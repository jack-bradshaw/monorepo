# Closet

A toolkit for Java AutoClosable resources.

## Release

Not released to third party package managers.

## Overview

Closet provides various tools and interfaces for working with `AutoCloseable` including:

- [ObservableClosable](/first_party/closet/observable/ObservableClosable.kt), an interface for
  closables that can be observed (i.e. notify on close).
- [ResourceManager](/first_party/closet/resourcemanager/ResourceManager.kt), a thread-safe registry
  for orchestrating multiple closables as one entity.
- [ClosetRule](/first_party/closet/rule/ClosetRule.kt), a JUnit test rule for automatically closing
  resources during tear down.

Together they allow you to reason about `AutoCloseable` objects as observable and composable, while
simplifying test orchestration. Guides are provided below for each.

## ObservableClosable

When you have an `AutoCloseable` and you need to query or observe its closure, you can implement the
`ObservableClosable` interface. It represents a closable that broadcasts its closure status with two
flags:

1. `hasTerminalState`, which indicates the close signal has been received and recorded internally,
   such that any future call to any function on the object that requires a non-closed state will
   deterministically fail or exit without effect.
1. `hasTerminatedProcesses`, which indicates the termination of any background work the closable
   holds internally (e.g. coroutine jobs, async tasks, etc.).

Below are examples of using and implementing `ObservableClosable`.

### Usage

Observing an `ObservableClosable` is a standard Kotlin flow operation. For example:

```kotlin
coroutineScope.launch {
  someClosable.hasTerminalState.filter { it }.onEach {
    println("terminal state reached")
  }.collect()
}

coroutineScope.launch {
  someClosable.hasTerminatedProcesses.filter { it }.onEach {
    println("background work terminated")
  }.collect()
}
```

### Implementation

Below is an example of a custom `ObservableClosable` implementation:

```kotlin
import com.jackbradshaw.closet.observable.ObservableClosable
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkPipeline : ObservableClosable {

  private val coroutineScope = CoroutineScope(Dispatchers.Default)
  private val backgroundWork = ConcurrentHashMap.newKeySet<Job>()

  private val lock = Mutex()

  private val _hasTerminalState = MutableStateFlow(false)
  override val hasTerminalState: StateFlow<Boolean> = _hasTerminalState

  private val _hasTerminatedProcesses = MutableStateFlow(false)
  override val hasTerminatedProcesses: StateFlow<Boolean> = _hasTerminatedProcesses

  suspend fun sendRequest(request: Request) {
    lock.withLock {
      if (_hasTerminalState.value) return
        val job = coroutineScope.launch {
        // Not implemented, present only for example purposes.
      }
      backgroundWork.add(job)
    }
  }

  override fun close() {
    runBlocking {
      lock.withLock {
        // Immediately mark closed status to reject new work.
        _hasTerminalState.value = true
      }

      // Drain running processes
      coroutineScope.cancel()
      backgroundWork.forEach {
        it.join()
      }
    }
    _hasTerminatedProcesses.value = true
  }
}
```

This example highlights a critical part of the `ObservableClosable` contract: Every call to `close`
must block until both `hasTerminalState` and `hasTerminatedProcesses` are true. The example
implements this by cancelling background work and joining existing jobs while they cancel. A mutex
is used to ensure new work is not scheduled after the terminal state has been marked, which avoids a
variety of edge cases and race conditions that can cause resource leaks.

## ResourceManager

When you have multiple closables you can use the `ResourceManager` to track them, coordinate them,
and manage them as one entity. It acts as a thread-safe key-value store, and when the manager is
closed, all registered values are closed too. For example:

```kotlin
import com.jackbradshaw.closet.resourcemanager.ResourceManager
import com.jackbradshaw.closet.resourcemanager.ResourceManagerComponent
import com.jackbradshaw.coroutines.DaggerCoroutinesComponentImpl
import dagger.Component
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

class ConnectionCoordinator @Inject constructor(
  private val factory: ResourceManager.Factory
) {

  private val registry = factory.createResourceManager()

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

@Component(dependencies = [ResourceManagerComponent::class])
interface ApplicationComponent {
  fun inject(app: MyApplication)

  @Component.Builder
  interface Builder {
    fun resourceManagerComponent(component: ResourceManagerComponent): Builder
    fun build(): ApplicationComponent
  }
}

class MyApplication : Application() {

  @Inject lateinit var connectionCoordinator: ConnectionCoordinator

  override fun onCreate() {
    DaggerApplicationComponent.builder()
      .resourceManagerComponent(
        DaggerResourceManagerComponentImpl.builder()
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
`ResourceManager`.

## ClosetRule

When you have closable resources in a test and cannot rely on the test process ending to close them
safely, you will need to manually close them during tear down. The `ClosetRule` simplifies this by
ensuring `close` is called. For example:

```kotlin
import com.jackbradshaw.closet.rule.ClosetRuleFactory
import org.junit.Rule
import org.junit.Test

class NetworkTest {
  // Any resource registered to the ClosetRule will be automatically terminated when the test completes.
  @get:Rule
  val rule = ClosetRuleFactory.create(NetworkPipeline())

  @Test
  fun testRouting() {
    val pipeline = rule.get()
    assertThat(pipeline.route()).isTrue()
  }
}
```

## Modularity

Interface-based programming is used extensively throughout Closet such that all tools can be
completely reimplemented by third parties without compromising compatibility with the broader tool
system. For example, you could implement your own `ResourceManager` and it should work with all
`ObservableClosable`s and vice versa. For convenience, abstract tests are provided for all tools,
and you can check your implementations against them. Follow the example in
[ResourceManagerTest](/first_party/closet/resourcemanager/ResourceManagerTest.kt).

## Issues

Issues relating to this package and its subpackages are tagged with `closet`.

## Contributions

Open to contributions from third parties.
