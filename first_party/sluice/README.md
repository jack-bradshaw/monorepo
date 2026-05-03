# Sluice

Sluice is a deterministic flow control primitive for routing and gating hot sources in environments with strict memory constraints.

## The Problem

When bridging purely asynchronous Hot Flows (like those driven by KSP round events) with downstream Kotlin Flow collectors, the Kotlin Coroutines library presents a fundamental timing vulnerability:

1. **The Micro-Race Condition**: Kotlin's `Flow` is just an interface for a generic suspending function. There is no built-in `onConnected` callback that reliably fires *after* the entire pipeline has successfully attached to the root source. Operators like `onSubscription` fire *before* the source is attached, leaving a micro-race where the tap can be turned on before the pipe is physically wired.
2. **The "Wait for Count" Hack**: While the publisher can check `MutableSharedFlow.subscriptionCount`, relying on a raw global integer breaks encapsulation when there are multiple independent downstream subscribers that don't know the "total" expected count.
3. **The Unsafe `UNDISPATCHED` Hack**: Using `launch(start = CoroutineStart.UNDISPATCHED)` ensures the flow attaches synchronously, but if *any* downstream operator suspends during the setup phase (e.g. an errant `delay()`), the thread yields prematurely and the race condition returns.
4. **The Buffering Trap**: The standard Kotlin answer to all of this is to simply buffer the hot source (`replay = 1` or `Channel`). But in strict environments like KSP, buffering references to closed compiler rounds creates massive memory leaks and fatal crashes when downstream collectors attempt to process stale AST nodes.

## The Solution

Sluice is a structural pattern that resolves these exact constraints by reinventing the `ConnectableObservable` concept for Kotlin Flows from first principles, ensuring 100% deterministic, zero-buffer Hot Flow routing.

When a downstream consumer requests a flow, Sluice does three things:

1. **Creates a Dedicated Pipe**: It spins up a brand new, dedicated intermediate `MutableSharedFlow(replay = 0)` exclusively for that specific consumer.
2. **Synchronous Upstream Wiring**: It launches a background coroutine using `UNDISPATCHED` that wires this intermediate pipe directly to the hot root source. Because the internal intermediate pipe has no suspending operators, the connection to the root is mathematically guaranteed to clamp on instantly and safely.
3. **Deterministic Downstream Gating**: It returns a `Sluice` object to the consumer. The consumer can now build their custom, complex flow chains off the intermediate pipe. Instead of relying on a global `subscriptionCount`, the consumer calls `sluice.awaitConnection()`. This specifically checks the `subscriptionCount` of *their dedicated intermediate pipe*.

This allows a central orchestrator to launch 20 independent consumers, explicitly wait for all 20 specific `Sluice` pipes to signal that they are fully wired up from end-to-end, and *then* safely turn on the hot source tap—knowing that not a single emission will be dropped, and not a single item had to be buffered.

## Usage

```kotlin
// 1. Give each component its own dedicated sluice
val sluices = listOf(dataRepo.createSluice(), dataRepo.createSluice())

// 2. Consumers build their pipes
launch { sluices[0].flow.collect { /* Component A */ } }
launch { sluices[1].flow.collect { /* Component B */ } }

// 3. The Orchestrator waits for the pipes to physically attach
sluices.forEach { it.awaitConnection() }

// 4. The Orchestrator turns on the tap!
controlRepo.allowStart()
```

## Closable Semantics

Sluice implements `ObservableClosable`. The intermediate pipe runs on a background coroutine until the root flow finishes. If a downstream consumer takes a Sluice but decides *not* to collect it (or crashes), they must call `sluice.close()` to cleanly terminate the background coroutine and prevent memory leaks.
