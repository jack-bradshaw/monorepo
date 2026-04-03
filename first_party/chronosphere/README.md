# Chronosphere

A framework and toolkit for testing concurrent systems.

## Release

Not released to third party package managers.

## Purpose

Chronosphere solves two problems that are inherent to testing concurrent systems:

1. The test needs to drive the system forward in time by a fixed duration.
2. The test needs to wait for the system to finish its work.

The former is called advancement, and the latter is called idling.

Advancement is usually required when the test cannot make an assertion or move on to the next step
until the system has been artificially driven through a particular time frame. A common example in
UI programming is animations: For pixel-perfection, a screendiff test might need to drive the UI
through 50 milliseconds and screenshot the UI to ensure the animation looks right at the 50ms mark. In
these cases, the issue is not waiting for a complex multithreaded environment to complete, it's just
about driving the underlying system forward in time with granular control. Advancement generally
relates to single-threaded systems since synchronizing virtual time across threads is difficult and
not always meaningful, but this is not an absolute limit, and multi-threaded systems can also be
advanced depending on how they are architected.

Idling is usually required when the test cannot make an assertion or move on to the next step until
the system has naturally finished its work. A common example in front-end programming is disk
read/write events: For performance, such operations usually happen on a background thread, with
callbacks or other asynchronous mechanisms used to notify the main thread when the task is complete.
In these cases the issue is not driving the system through a fixed amount of time, but waiting in
the test until all the background and foreground work has finished. Idling generally relates to
multi-threaded systems, but this is not an absolute limit, and single-threaded systems can be idled.

## Approach

There are many ways to approach advancement and idling depending on your architectural flexibility
and priorities. You could simply put `isFinished` or `advanceBy` functions on your production APIs
and mark them as test-only; however, this pollutes your production API with test code and can lead
to test-only code inadvertently being used in production (causing all sorts of errors).
Alternatively, you could wait for fixed periods in tests using `Thread.sleep()` or coroutine
`delay`, but this is unreliable and error-prone, as millisecond precision is not guaranteed, and the
tests will break if the host processor is slower than you expect. Chronosphere supports a third
option that avoids both these issues.

A solution that avoids production API pollution and hardcoded waits is to replace the underlying
execution engine with a fake in tests using dependency injection. If you structure your system so
that the execution system can be completely replaced, then you can inject a fake in tests and the
real system in production. If you set up the fake so that it can be advanced/idled, then everything
that uses it can be advanced/idled indirectly. For example, if you're using Kotlin coroutines, you
could inject the real IO dispatcher into production and a fake into tests, and design the fake so it
provides an `isFinished` boolean you can check to see if there is any work. In the end this approach
keeps the high-level API clean and allows you to check a deterministic value to determine if the
system has idled. The details of idling and advancement depend on the exact system being used (i.e.
advancing a coroutine is different to advancing a scheduled thread pool), but the core idea is
common: Instead of advancing/idling the high-level API, advance/idle the underlying execution
engine.

Although the exact process of advancing/idling depends on the system being advanced/idled, there are
aspects of the logic that are common to all systems and do not need duplication and reimplementation
everywhere. This is where Chronosphere comes in. It provides tools that handle the common operations
of checking systems are idle and advancing systems, along with interfaces that any system can
implement to plug in. This approach allows you to define a system that can advance/idle, register it
with Chronosphere, and let Chronosphere handle the orchestration. This way, when you have multiple
systems that need to advance/idle together, you can focus on making each advancable/idleable while
ignoring the integration complexity.

### Advancing

Chronosphere provides the following tools for advancement:

- [Advancable](/first_party/chronosphere/advancable/Advancable.kt) (interface), which represents
  something that can advance.
- [TestingTaskDriver](/first_party/chronosphere/testingtaskdriver/TestingTaskDriver.kt) (tool),
  which drives one or more `Advancable` implementations forward in time.

If you have a system that advances through steps, you can use the `TestingTaskDriver` to advance
your test in millisecond increments. You simply need to construct an `Advancable`, use that
`Advancable` in your higher-level components, inject them into a test, then coordinate them with the
`TestingTaskDriver`. Below is a complete example that demonstrates tool usage with instantiation handled by
Dagger.

```kotlin
class GameTest {

  @Inject lateinit var taskDriver: TestingTaskDriver
  @Inject lateinit var gameEngine: GameEngine

  @Before
  fun setup() {
    DaggerTestComponent.builder()
      .testingTaskDriverComponent(DaggerTestingTaskDriverComponentImpl.create())
      .build()
      .inject(this)
  }

  @Test
  fun checkFirst50ms() = runBlocking {
    taskDriver.advanceAllBy(50)

    assertThat(gameEngine.physics.barrelCount).isEqualTo(50)
  }
}

// The core game loop.
interface Looper {
  fun onTick(listener: () -> Unit)
}

// A game loop double for use in tests.
class AdvancableLooper @Inject constructor() : Looper, Advancable {

  private val listeners = mutableListOf<() -> Unit>()

  override fun onTick(listener: () -> Unit) {
    listeners.add(listener)
  }

  override fun advanceBy(millis: Int) {
    for (i in 0 until millis) {
      listeners.forEach { it.invoke() }
    }
  }
}

class PhysicsEngine(val loop: Looper) {
  var barrelCount = 0

  init {
    loop.onTick {
      barrelCount++
      spawnExplosiveBarrel() // Not implemented, present only for example purposes.
    }
  }
}

class GameEngine @Inject constructor(
  val loop: Looper
) {
  val physics = PhysicsEngine(loop)
}

@Module
interface TestingModule {
  @Binds
  fun bindLooper(impl: AdvancableLooper): Looper

  companion object {
    @Provides
    fun provideDriver(
      looper: AdvancableLooper,
      factory: TestingTaskDriver.Factory
    ) = factory.create(setOf(looper))
  }
}

@Component(
  dependencies = [TestingTaskDriverComponent::class],
  modules = [TestingModule::class]
)
interface TestComponent {
  fun inject(test: GameTest)

  @Component.Builder
  interface Builder {
    fun testingTaskDriverComponent(component: TestingTaskDriverComponent): Builder
    fun build(): TestComponent
  }
}
```

This test demonstrates the full end to end process. The various components are wired together by
dagger, and when the test advances time, it advances the underlying looper. This ensures the real
game engine is being exercised, and only the deeper concurrency system needs to be switched for a
test fake.

### Idling

Chronosphere provides the following tools for idling:

- [Idleable](/first_party/chronosphere/idleable/Idleable.kt) (interface), which represents something
  that can idle.
- [TestingTaskBarrier](/first_party/chronosphere/testingtaskbarrier/TestingTaskBarrier.kt) (tool),
  which blocks until one or more `Idleable` implementations have finished work.

If you have a system that performs background work, you can use the `TestingTaskBarrier` to pause
your test until all asynchronous work has completed. You simply need to construct an `Idleable`, use
that `Idleable` in your higher-level components, inject them into a test, then coordinate them with
the `TestingTaskBarrier`. Below is a complete example that demonstrates tool usage with instantiation
handled by Dagger.

```kotlin
class NetworkTest {

  @Inject lateinit var taskBarrier: TestingTaskBarrier
  @Inject lateinit var networkManager: NetworkManager

  @Before
  fun setup() {
    DaggerTestComponent.builder()
      .testingTaskBarrierComponent(DaggerTestingTaskBarrierComponentImpl.create())
      .build()
      .inject(this)
  }

  @Test
  fun networkCallReturnsResponse() = runBlocking {
    var response: Response? = null
    networkManager.sendRequest(Request()) {
      response = it
    }

    taskBarrier.awaitAllIdle()

    assertThat(response).isNotNull()
  }
}

// The core async engine.
interface BackgroundProcessor {
  fun execute(runnable: Runnable)
}

// An async engine double for use in tests.
class IdleableBackgroundProcessor @Inject constructor() : BackgroundProcessor, Idleable {

  private val _isIdle = AtomicBoolean(true)

  override fun isIdle() = _isIdle.get()

  override fun execute(runnable: Runnable) {
    _isIdle.set(false)
    // Not implemented, present only for example purposes.
    _isIdle.set(true)
  }
}

class NetworkManager @Inject constructor(
  val processor: BackgroundProcessor
) {
  fun sendRequest(req: Request, onResponse: (Response) -> Unit) {
    processor.execute {
      // Not implemented, present only for example purposes.
    }
  }
}

@Module
interface TestingModule {
  @Binds
  fun bindProcessor(impl: IdleableBackgroundProcessor): BackgroundProcessor

  companion object {
    @Provides
    fun provideBarrier(
      processor: IdleableBackgroundProcessor,
      factory: TestingTaskBarrier.Factory
    ) = factory.create(setOf(processor))
  }
}

@Component(
  dependencies = [TestingTaskBarrierComponent::class],
  modules = [TestingModule::class]
)
interface TestComponent {
  fun inject(test: NetworkTest)

  @Component.Builder
  interface Builder {
    fun testingTaskBarrierComponent(component: TestingTaskBarrierComponent): Builder
    fun build(): TestComponent
  }
}
```

This test demonstrates the full end to end process. The various components are wired together by
dagger, and when the test waits for idle, it waits until the underlying processor idles. This
ensures the real network system is being exercised, and only the deeper concurrency system needs to
be switched for a test fake.

### Composition

`TestingTaskBarrier` and `TestingTaskDriver` are themselves `Idleable` and `Advancable`
respectively, which allows composition. For example:

```kotlin
val masterBarrier = barrierFactory.create(setOf(systemABarrier, systemBBarrier))
val masterDriver = driverFactory.create(setOf(systemADriver, systemBDriver))

masterDriver.advanceAllBy(100)
masterBarrier.awaitAllIdle()
```

This is useful if multiple unrelated libraries expose `TestingTaskBarrier`/`TestingTaskDriver` objects. If you could not
combine them together, you would need to manually loop over them, which adds boilerplate to your
tests. Composing them saves work and is less error-prone.

## Concurrency

The functions of the `TestingTaskBarrier` and `TestingTaskDriver` are not suspending functions because they
inherently operate on concurrent systems and are meant as the final boundary between a synchronous
test and an asynchronous system. If they exposed suspending functions, the caller would need to be a
coroutine context, which defeats the purpose.

## Best Practices

Implementing `Idleable` and `Advancable` can impose performance overheads which are undesirable in
production; therefore, it can be useful to only implement them on your test doubles. Keeping the
behaviour of your doubles as close as possible to production is a general best practice though;
therefore, the interceptor pattern can be useful. Example:

```kotlin
class TestFoo(
  val realFoo: Foo,
) : Idleable, Foo {

  private val _isIdle = AtomicBoolean()

  override fun executeWork(job: Runnable) {
    _isIdle.set(false)
    job.run()
    _isIdle.set(true)
  }

  override fun isIdle() = _isIdle.get()
}
```

Using the real foo ensures the system under test performs similarly to production. Using this in
production is undesirable though because the atomic boolean would bottleneck concurrency.

## Beyond Testing

While Chronosphere was designed and built for testing asynchronous systems, nothing about it
strictly requires a testing environment, and it could be used in production if desired. 

## Modularity

Interface-based programming is used extensively throughout Chronosphere such that all tools can be
completely reimplemented by third parties without compromising compatibility with the broader tool
system. For example, you could implement your own `TestingTaskBarrier` and it should work with all
`Idleable` implementations and be composable with any other `TestingTaskBarrier`. For convenience, the `TestingTaskBarrier` and
`TestingTaskDriver` tests are abstract contract tests, and you can check your implementations against them.
Follow the example in
[TestingTaskDriverImplTest](/first_party/chronosphere/testingtaskdriver/TestingTaskDriverImplTest.kt).

## Other Packages

The [CoroutinesComponent](/first_party/coroutines/CoroutinesComponent.kt) is integrated with Chronosphere. It provides standard dispatchers that can
be advanced/idled in tests. It can be used and provides helpful examples for extending Chronosphere
to other systems.

## Issues

Issues relating to this package and its subpackages are tagged with `chronosphere`.

## Contributions

Third-party contributions are accepted.
