# Closet

Assorted tools for working with closable resources.

## Release

Not released to third party package managers.

## Overview

Closet provides various tools and interfaces for working with closable resources including:

- [SuspendableClosable](/first_party/closet/suspending/SuspendableClosable.kt): The foundational
  interface for closable resources that allow suspension during closure.
- [ObservableClosable](/first_party/closet/observable/ObservableClosable.kt): The foundational
  interface for closables that broadcast their closure state.
- [StandardObservableClosable](/first_party/closet/observable/standard/StandardObservableClosableComponent.kt):
  A standard implementation of `ObservableClosable` to eliminate boilerplate.
- [ObservableClosableHelpers](/first_party/closet/observable/helpers/ObservableClosableHelpers.kt):
  Convenience functions for `ObservableClosable`s.
- [ResourceMap](/first_party/closet/resourcemanager/map/ResourceMap.kt) and
  [ResourceSet](/first_party/closet/resourcemanager/set/ResourceSet.kt): Thread-safe registries for
  orchestrating multiple closables as one.
- [AutoCloseRule](/first_party/closet/rule/AutoCloseRule.kt): A JUnit test rule for automatically
  closing resources during test tear down.

Together these utilities allow you to reason about closable objects as suspendable, observable,
composable systems, with sensible and convenient test orchestration.

## SuspendableClosable

[SuspendableClosable] is equivalent to [AutoCloseable], except its close function is suspending
instead of synchronous. It is useful in scenarios where the internal logic of the closable requires
suspending operations during closure, for example:

```kotlin
class NetworkConnection : SuspendableClosable {

  private val closeLock = Mutex()

  private var isClosed = false

  private val connectionJob = coroutineScope.launch {
    // Keeps connection alive, does some work.
  }

  override suspend fun close() {
    closeLock.withLock {
      if (isClosed) return
      isClosed = true

      // Safely wait for the background job to finish its work and terminate
      connectionJob.cancelAndJoin()
    }
  }
}
```

Using `SuspendableClosable` avoids the need for `runBlocking` in `close` which aids overall
architecture by avoiding blocking calls deep within an asynchronous call stack.

## ObservableClosable

[ObservableClosable](/first_party/closet/observable/ObservableClosable.kt) is a
`SuspendableClosable` that broadcasts its closure state, for example:

```kotlin
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Foo : ObservableClosable {

  private val closeLock = Mutex()

  private val _closureStatus = MutableStateFlow(Status.OPEN)

  // Some generic network system, for example purposes.
  private val network = Network()

  override val closureStatus = _closureStatus.asStateFlow()

  fun connectToServer(): Connection {
    check(closureStatus.value == Status.OPEN) {
      "This Foo is closed, cannot provide a new connection."
    }
    return network.newSession()
  }

  override suspend fun close() {
    closeLock.withLock {
      if (_closureStatus.value != Status.CLOSED) {
        _closureStatus.value = Status.CLOSING
        network.closeAllSessions()
        _closureStatus.value = Status.CLOSED
      }
    }
  }
}
```

Observing closure:

```kotlin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import com.jackbradshaw.closet.observable.helpers.awaitClosed
import com.jackbradshaw.closet.observable.helpers.awaitClosing

suspend fun example(foo: Foo) = coroutineScope {
  launch {
    foo.awaitClosing()
    println("foo has started closing")
  }

  launch {
    foo.awaitClosed()
    println("background work terminated")
  }
}
```

## StandardObservableClosable

Implementing `ObservableClosable` directly requires careful management of `Mutex` locks and state
transitions to ensure compliance with the strict concurrency and queueing contract. You can
eliminate this boilerplate and reduce complexity by delegating to `StandardObservableClosable`, for
example:

```kotlin
import com.jackbradshaw.closet.observable.helpers.checkOpen
import com.jackbradshaw.closet.observable.standard.StandardObservableClosableFactory
import kotlinx.coroutines.runBlocking

class Foo(standardFactory: StandardObservableClosableFactory) : ObservableClosable {

  // Some generic network system, for example purposes.
  private val network = Network()

  private val standard = runBlocking {
    standardFactory.createStandardClosable {
      network.closeAllSessions()
    }
  }

  override val closureStatus = standard.closureStatus

  fun connectToServer(): Connection {
    checkOpen("This Foo is closed, cannot provide a new connection.")
    return network.newSession()
  }

  override suspend fun close() = standard.close()
}
```

## ObservableClosableHelpers

Helpers are available to reduce boilerplate when using `ObservableClosable`, for example:

```kotlin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import com.jackbradshaw.closet.observable.helpers.awaitClosed
import com.jackbradshaw.closet.observable.helpers.awaitClosing

suspend fun example(foo: Foo) = coroutineScope {
  launch {
    foo.awaitClosing()
    println("foo has started closing")
  }

  launch {
    foo.awaitClosed()
    println("background work terminated")
  }
}
```

## ResourceMap

When you have multiple closables you can use the `ResourceMap` to track them, coordinate them, and
manage them as one entity. It acts as a thread-safe key-value store, and when the manager is closed,
all registered values are closed too. For example:

```kotlin
import com.jackbradshaw.closet.resourcemanager.map.ResourceMap
import com.jackbradshaw.closet.resourcemanager.map.ResourceMapComponent
import com.jackbradshaw.coroutines.DaggerCoroutinesComponentImpl
import dagger.Component
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

class ConnectionCoordinator @Inject constructor(
  private val factory: ResourceMap.Factory
) {

  private val registry = runBlocking { factory.createResourceMap() }

  suspend fun openConnection(destination: String) {
    val connection = Connection(destination)
    registry.put(destination, connection)
    // Other work not implemented, present only for example purposes.
  }

  suspend fun shutdownSystem() {
    registry.close()
  }

  class Connection(private val destination: String) : ObservableClosable {
    // Not implemented, present only for example purposes.
  }
}

@Component(dependencies = [ResourceMapComponent::class])
interface ApplicationComponent {
  fun inject(app: MyApplication)

  @Component.Builder
  interface Builder {
    fun resourceMapComponent(component: ResourceMapComponent): Builder
    fun build(): ApplicationComponent
  }
}

class MyApplication : Application() {

  @Inject lateinit var connectionCoordinator: ConnectionCoordinator

  override fun onCreate() {
    DaggerApplicationComponent.builder()
      .resourceMapComponent(
        DaggerResourceMapComponentImpl.builder()
          .coroutines(DaggerCoroutinesComponentImpl.create())
          .build()
      )
      .build()
      .inject(this)

    runBlocking {
      ENDPOINTS.forEach {
        connectionCoordinator.openConnection(it)
      }
    }
  }

  override fun onDestroy() {
    runBlocking {
      connectionCoordinator.shutdownSystem()
    }
  }

  companion object {
    private val ENDPOINTS = listOf("https://foo.com", "https://bar.com") // etc.
  }
}
```

The dagger setup in the above example is real and can be followed in your code to get instances of
`ResourceMap`. For cases where keys are not necessary, the `ResourceSet` offers the same
functionality without key-value associations.

## AutoCloseRule

When you need to open resources in a test and close them during teardown, the `AutoCloseRule` can
simplify test boilerplate. It works as a registry and closes all registered values during tear-down,
for example:

```kotlin
import com.jackbradshaw.closet.rule.autoCloseRuleComponent
import org.junit.Rule
import org.junit.Test

class NetworkTest {
  // Auto closes registered resources.
  @get:Rule
  val rule = autoCloseRuleComponent().autoCloseRule()

  @Test
  fun testRouting() {
    val pipeline = rule.register(NetworkPipeline())
    assertThat(pipeline.route()).isTrue()
  }
}
```

## Modularity

Interface-based programming is used extensively throughout Closet such that all tools can be
completely reimplemented by third parties without compromising compatibility with the broader tool
system. For example, you could implement your own `ResourceMap` and it should work with all
`ObservableClosable`s and vice versa. For convenience, abstract tests are provided for all tools,
and you can check your implementations against them. Follow the example in
[ResourceMapTest](/first_party/closet/resourcemanager/map/ResourceMapTest.kt).

## Issues

Issues relating to this package and its subpackages are tagged with `closet`.

## Contributions

Open to contributions from third parties.
