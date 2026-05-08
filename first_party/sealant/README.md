# Sealant

Infrastructure for leak-free Kotlin flows.

## Overview

Sealant allows you to use Kotlin flows with certainty the collector is connected to the source
before you allow data to begin flowing. It's useful in scenarios where you have a hot data source
that you can manually start, but you need to ensure no data is lost or dropped. It effectively
allows you to hold off starting the flow until everything downstream is definitely connected, then
begin the flow only when everything is connected, similar to connecting a hose before opening the
tap. It exists because this is not a well-defined or easily supported pattern with baseline Kotlin
flows but is necessary when building data-processing pipelines with zero-tolerance for lost or
repeated events.

## Purpose

Consider a scenario where you have:

1. A system that will start pushing values to you when you start it.
2. One or more downstream pipelines that must process every emission.
3. Absolutely no room for emissions being dropped by late subscribers or missed by downstream
   pipelines.

This scenario occurred during the creation of the [Backstab] KSP processor. Data is pushed into the
processor from the KSP framework, which it uses Kotlin flows to process the data in various ways. As
part of the build system it must be absolutely reliable, and there is no room for errors or
flakiness due to race conditions and other asynchronous behaviors. Ensuring the system works
perfectly required being able to wire together the downstream pipelines and ensure they were fully
connected _before_ data began flowing through them, but unfortunately, this is not straightforward.

The aforementioned setup is not impossible with Kotlin Flows, but it conflicts with the core design
of the system, and the base library does not provide primitives that are designed specifically for
the task. In Kotlin flows, a flow is actually just a series of connected operators, each collecting
from the one before it, and active flow collection is a recursive call up the operator chain. It is
a deeply asynchronous process with few guarantees about timing, and unlike RxJava, the pipeline
itself is not a well-defined rigid construct that can be modeled as "connected" and "disconnected".
The issue can best be described with code:

```kotlin
someScope.launch {
  someFlow().collect { println(it) }
}
println("collection started")
```

The above code is erroneous because `collect` is not guaranteed to have been called when the second
`println` is reached. The launched coroutine could crash due to an error in `someFlow()` or the
dispatcher could be slow/blocked. If `collect` returned an object that could be queried there would
be no issue, but that is not the case.

Despite the issue, Kotlin flows are the dominant system for reactive streams in Kotlin, and it is an
incredibly useful system for data processing, so sealant was created to allow its use in Backstab
while ensuring the pipe itself was perfectly sealed and no emissions were lost. It produces a
"sealed flow", which is designed to reliably produce an `isConnectedToHub` signal when the downstream
collector has actually started AND the pipe is connected to the source. It works by means of two
constructs: SealedHub and SealedFlow.

A sealed hub takes an existing flow and acts as the upstream for sealed flows. It produces one or
more `SealedFlow` instances, each representing a pipeline coming off the hub. The hub receives values from its
upstream and forwards them to each sealed flow for downstream processing. The critical factor that makes
this more than a shared flow is the observability: Every sealed flow exposes an `isConnectedToHub` state flow that
indicates whether its final downstream collector is actively listening AND whether its internal pipeline is securely 
connected to the hub.

Crucially, the `SealedFlow` encapsulates any flow transformations (e.g., map, filter) you wish to apply. 
Because these transformations occur *inside* the sealed pipeline, they are monitored, meaning you can 
guarantee `collect` is actually running from the very top to the very bottom.

Flow diagram:

```text
source flow -> sealed hub -> sealed flow (containing operators) -> collector
                     |
                     |------> sealed flow (containing operators) -> collector
                     |
                     |------> sealed flow (containing operators) -> collector
```

In Backstab this allowed the upstream KSP processor to be suspended until all downstream pipelines
were completely configured, which prevented dropped events, and allowed asynchronous processing with
zero risk of data being dropped by late subscribers. You can use sealant in any system that requires
this.

## Basic Usage

Assume a data source with the following interface:

```kotlin
interface DataSource {
  suspend fun flow(): Flow<String>
  suspend fun start()
}
```

The `flow` function can be used at any time but nothing will be emitted until `start()` is called.
Sealant can be used to ensure multiple downstream collectors are fully connected to the source
before calling start like so:

```kotlin
val source = getUpstreamDataSource()

val hubFactory = sealantComponent().sealedHubFactory()
val hub = hubFactory.create(source.flow())

val logSession = hub.createFlow()
val checkSession = hub.createFlow()
val processingSession = hub.createFlow()

val scope = CoroutineScope(Dispatchers.IO)
scope.launch {
  launch {
    logSession.flow.onEach {
      println(it)
    }.collect()
  }

  launch {
    checkSession.flow.onEach {
      if (it == "failure") scope.cancel()
    }.collect()
  }

  launch {
    processingSession.flow.onEach {
      println(someComplexProcessingFunction(it))
    }.collect()
  }
}

logSession.awaitConnectionToHub()
checkSession.awaitConnectionToHub()
processingSession.awaitConnectionToHub()

source.start()

// at some point later when processing is done

hub.close()
```

Each session object represents an exposure of the underlying source flow passed to the hub factory,
and the connection status of each can be individually checked. The connection flags are state flows,
so they can be observed as shown in the above example, or read directly with
`sealedFlow.isConnectedToHub.value`.

## Advanced Usage

Sealant can only prevent leaks within the constructed pipeline, and it cannot detect or prevent
leaks upstream of the root hub or downstream of the final `SealedFlow` output; therefore the hub should be defined as close
to the upstream source as possible, and the `SealedFlow` output should be collected as directly as possible by the downstream
consumer. While transformations can be safely passed into `createFlow`, complex architectures where flows pass through
many layers of abstraction and indirection might benefit from using multiple cascading hubs/flows across the pipeline to reduce the overall risk of leakage.
Below is an example of cascading hubs:

```kotlin
// Layer 1

class Layer1(private val sealedHubFactory: SealedHub.Factory) {

  private val coroutineScope = CoroutineScope(Dispatchers.IO)

  private val source = DataSource()

  private val topOfPipe = MutableSharedFlow<String>(replay = 0)

  private lateinit var topOfPipeHub: SealedHub<String>

  suspend fun initialize() {
    topOfPipeHub = sealedHubFactory.create(underlyingFlow = topOfPipe)
  }

  /** Emits data from the upstream. */
  suspend fun observeData(): SealedFlow<String> = topOfPipeHub.createFlow()

  suspend fun startEmissions() {
    coroutineScope.launch {
      source.onNewData {
        topOfPipe.emit(it)
      }
    }
  }

  fun close() {
    coroutineScope.cancel()
    if (::topOfPipeHub.isInitialized) topOfPipeHub.close()
  }
}

class Layer2(
  private val layer1: Layer1,
  private val sealedHubFactory: SealedHub.Factory
) {

  private lateinit var emissionCountHub: SealedHub<Int>
  private lateinit var helloWorldSeenHub: SealedHub<Boolean>

  suspend fun initialize() {
    emissionCountHub = sealedHubFactory.create(
      underlyingFlow = layer1.observeData().flow.scan(0) { count, _ -> count + 1 }
    )

    helloWorldSeenHub = sealedHubFactory.create(
      underlyingFlow = layer1.observeData().flow.map { it == "Hello, World!" }.scan(false) { prev, curr -> prev || curr }.distinctUntilChanged()
    )
  }

  /** Emits a running count of the number of emissions from the upstream. */
  suspend fun observeDataCount(): SealedFlow<Int> = emissionCountHub.createFlow()

  suspend fun observeHelloWorldSeen(): SealedFlow<Boolean> = helloWorldSeenHub.createFlow()

  fun close() {
    if (::emissionCountHub.isInitialized) emissionCountHub.close()
    if (::helloWorldSeenHub.isInitialized) helloWorldSeenHub.close()
  }
}

class Layer3(
  private val layer1: Layer1,
  private val layer2: Layer2
) {

  private val coroutineScope = CoroutineScope(Dispatchers.IO)

  suspend fun logDataCount() {
    val emissionCountFlow = layer2.observeDataCount()
    val helloWorldSeenFlow = layer2.observeHelloWorldSeen()

    coroutineScope.launch {
      emissionCountFlow.flow.onEach { totalCount ->
        println("Received another value, bringing total count to $totalCount")
      }.collect()
    }

    coroutineScope.launch {
      helloWorldSeenFlow.flow.filter { it }.onEach {
        println("hello world went down the pipe")
      }.collect()
    }

    emissionCountFlow.awaitConnectionToHub()
    helloWorldSeenFlow.awaitConnectionToHub()

    layer1.startEmissions()
  }

  fun close() {
    // Stops pushing data into the pipe and closes all downstream hubs/sessions
    coroutineScope.cancel()
    layer1.close()
    layer2.close()
  }
}
```

The pattern is simple:

1. `SealedHub` is usually a private implementation detail. It collects data from upstream and
   provides sealed flows when the class is used. Multiple hubs can be required when a class provides
   multiple distinct flows to keep them separate, though you can also use a single hub and provide different transformations via `createFlow { ... }`.
2. `SealedFlow` is usually a public API detail. It declares the leak-proof nature of the flow
   which allows consumers to check connection status and continue to create leak-proof flows
   downstream. A new `SealedFlow` is provisioned by the hub each time the flow is requested to avoid
   illegal double usage.

This pattern can be used recursively to ensure the flow is fully connected before the tap at the top
of the flow is opened.

By passing transformations directly into `createFlow { flow -> ... }`, users can safely apply operators (even "leaky" ones like `buffer`) knowing they are fully monitored by the internal pipeline.

## Closure

SealedHub and SealedFlow are active systems that operate and coordinate coroutine jobs
internally. To prevent resource leaks, ensure the hub and individual sessions are closed with
`close` when no longer needed. If you construct hubs using `SealedHub.Factory.create()` you will
need to close each hub/session individually, but if you use
`SealedHub.Factory.createWithAutomaticClosure` then closure propagates automatically down the
pipeline. For example:

```kotlin
class CascadingPipeline(private val sealedHubFactory: SealedHub.Factory) {

  private val coroutineScope = CoroutineScope(Dispatchers.IO)

  private val topOfPipe = MutableSharedFlow<String>()

  // Hubs defined at the class level so they can be referenced in close()
  private lateinit var topOfPipeHub: SealedHub<String>
  private lateinit var hub1: SealedHub<String>
  private lateinit var hub2: SealedHub<String>

  suspend fun startDownstream() {
    topOfPipeHub = sealedHubFactory.create(underlyingFlow = topOfPipe)

    // Lifecycle is inherently linked to topOfPipeHub because all sessions are linked to their hub.
    val topOfPipeFlow = topOfPipeHub.createFlow()

    // Lifecycle is intentionally linked to topOfPipeFlow since with-automatic-closure is used.
    hub1 = sealedHubFactory.createWithAutomaticClosure(
      underlyingFlow = topOfPipeFlow
    )

    // Lifecycle is NOT linked to topOfPipeFlow since with-automatic-closure was not used.
    hub2 = sealedHubFactory.create(
      underlyingFlow = topOfPipeFlow.flow
    )

    // Lifecycle is inherently linked to hub1 because all sessions are linked to their hub.
    val hub1Flow = hub1.createFlow()
    coroutineScope.launch {
      hub1Flow.flow.collect { println(it) }
    }

    // Lifecycle is inherently linked to hub2 because all sessions are linked to their hub.
    val hub2Flow = hub2.createFlow()
    coroutineScope.launch {
      hub2Flow.flow.collect { println(it) }
    }
  }

  suspend fun close() {
    coroutineScope.cancelAndJoin()

    // Closes topOfPipeHub -> closes topOfPipeFlow -> closes hub1 -> closes hub1Flow
    // Does NOT close hub2 or hub2Flow because they were created without automatic closure.
    if (::topOfPipeHub.isInitialized) topOfPipeHub.close()

    // Closes hub2 -> closes hub2Flow
    if (::hub2.isInitialized) hub2.close()
  }
}
```

## Preventing Leaks

Sealant can only prevent leaks when transformations occur within the pipeline itself, meaning all
transformations are contained in the `createFlow` function of a hub, and each hub is linked together
without further transformations. For example, there are three leaks in the following code:

```kotlin
class LeakySystem(
  private val upstream: DataSource,
  private val sealedHubFactory: SealedHub.Factory
) {

  private val coroutineScope = CoroutineScope(Dispatchers.IO)
  
  private lateinit var flowHub1: SealedHub<Int>
  private lateinit var flowHub2: SealedHub<Int>

  suspend fun initialize() {
    // LEAK 1: Applying a buffer BEFORE the hub.
    // The buffer breaks the connection upstream of the hub, so Sealant cannot verify whether
    // 'upstream.rawData' has actually connected.
    val leakyUpstream = upstream.rawData.buffer(capacity = 3)
    flowHub1 = sealedHubFactory.create(leakyUpstream)

    // LEAK 2: Applying a buffer BETWEEN hubs.
    // The connection between flowHub2 to flowHub1 is broken by the buffer, similar to leak1.
    val sealedFlow1 = flowHub1.createFlow()
    val leakyIntermediate = sealedFlow1.flow.buffer(capacity = 3)
    flowHub2 = sealedHubFactory.create(leakyIntermediate)

    coroutineScope.launch {
      // Not passing the transformation into createFlow()!
      val sealedFlow2 = flowHub2.createFlow()
      
      launch {
        // LEAK 3: Applying a buffer AFTER the sealed flow.
        // The connection after sealedFlow2 is broken by the buffer, similar to leak1.
        sealedFlow2.flow
            .map { it.length }
            .buffer(capacity = 3)
            .filter { it > 2 }
            .onEach { println("Observed string of length over 2") }
            .collect()
      }
      
      sealedFlow2.awaitConnectionToHub()
      upstream.start() // UNRELIABLE: Race condition because of the leaks!
    }
  }
}
```

To fix the above example, the transformations need to be moved into the sealed pipeline so sealant
can ensure they are connected even when they contain leaky operators. For example:

```kotlin
class ResilientSystem(
  private val upstream: DataSource,
  private val sealedHubFactory: SealedHub.Factory
) {

  private val coroutineScope = CoroutineScope(Dispatchers.IO)

  private lateinit var flowHub1: SealedHub<String>
  private lateinit var flowHub2: SealedHub<String>

  suspend fun initialize() {
    // FIX 1: Pass the raw data directly to the hub so Sealant can verify 
    // the root connection.
    flowHub1 = sealedHubFactory.create(upstream.rawData)

    // FIX 2: Apply the intermediate buffer inside the first pipeline so its 
    // connection is structurally verified.
    val sealedFlow1 = flowHub1.createFlow { it.buffer(capacity = 3) }
    flowHub2 = sealedHubFactory.create(sealedFlow1.flow)

    coroutineScope.launch {
      // FIX 3: Encapsulate all downstream transformations inside createFlow!
      val sealedFlow2 = flowHub2.createFlow { flow -> 
          flow.map { it.length }.buffer(capacity = 3).filter { it > 2 } 
      }
      
      launch {
        sealedFlow2.flow.onEach {
          println("Observed string of length over 2")
        }.collect()
      }
      
      // Wait for both pipelines to be securely connected from bottom to top!
      sealedFlow1.awaitConnectionToHub()
      sealedFlow2.awaitConnectionToHub()
      
      upstream.start() // SAFE: Data will flow reliably!
    }
  }
}
```

By ensuring your code follows this approach by keeping your transformations occur inside
`createFlow`, Sealant can guarantees the entire pipeline is connected from the first source to
the final collector before `awaitConnectionToHub()` resumes, thus eliminating race conditions even
across asynchronous boundaries and complex intermediate operators.

## Dagger

Dagger is used to manage dependencies and provide instances of `SealedHub.Factory`. You can get
instances from `SealantComponent` (and its implementation, `SealantComponentImpl`) directly, or
depend on it to inject them through your code. The `sealantComponent` helper function makes it
easy to instantiate with sensible defaults.. For example:

```kotlin
@MyScope
@Component(dependencies = [SealantComponent::class])
interface MyApplicationComponent {
  fun inject(application: Application)
}

@MyScope
class SomeClass @Inject constructor(private val sealedHubFactory: SealedHub.Factory) {
  fun doSealantThings() {
    // Impl omitted for example purposes
  }
}

class Application : SomeFrameworkApplication {

  @Inject lateinit var someClass: SomeClass

  override fun onCreate() {
    DaggerMyApplicationComponent.builder()
        .sealantComponent(sealantComponent())
        .build()
        .inject(this)

    someClass.doSealantThings()
  }
}
```

`SealantComponent` depends on `CoroutinesComponent` from the [coroutines package](/first_party/coroutines). By
default it uses coroutines that are well suited for flow-based work, but if you need granular
control over coroutines, an alternative can be passed in like so:

```kotlin
@Module
class CustomCoroutinesModule {
  @Provides @Io fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
  @Provides @Io fun provideIoContext(@Io d: CoroutineDispatcher): CoroutineContext = d
  @Provides @Cpu fun provideCpuDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
  @Provides @Cpu fun provideCpuContext(@Cpu d: CoroutineDispatcher): CoroutineContext = d
}

@Component(modules = [CustomCoroutinesModule::class])
interface CustomCoroutinesComponent : CoroutinesComponent

val customCoroutines = DaggerCustomCoroutinesComponent.create()
val sealant = sealantComponent(customCoroutines)
```

Furthermore, the coroutines package supports various testing configurations that allow deterministic
testing of asynchronous code, which is particularly useful when dealing with flows. View the package for more details.

## Performance

While the performance of Sealant has not been empirically tested, it adds various layers of
complexity over baseline flows, and performs intermediate collection/processing of flows which add
both time and space overhead. This is the cost of leak-proof flows, and using sealant is a
tradeoff between performance and correctness. If you require a pipeline where data does not begin
to flow until the pipeline is definitively setup, use Sealant. If lost emissions are recoverable
or unimportant, Sealant is unnecessary.

## Modularity

Interface-based programming is used extensively throughout this package such that most tools can be
completely reimplemented by third parties without compromising compatibility with the broader tool
system. For example, you could implement your own `SealedHub` and it should work seamlessly. For
convenience, abstract tests are provided for all tools, and you can check your implementations
against them. Follow the example in [SealedHubTest](/first_party/sealant/hub/SealedHubTest.kt).

## Issues

Issues relating to this package and its subpackages are tagged with `sealant`.

## Contributions

Third-party contributions are accepted.
