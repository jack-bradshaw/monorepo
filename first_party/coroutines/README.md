# Coroutines

Dependency injectable Kotlin coroutine infrastructure with [Chronosphere](/first_party/chronosphere) integration.
## Release

Not released to third party package managers.

## Overview

This package makes coroutine infrastructure available via dependency injection. It provides two
dispatchers, one for CPU bound work, and one for IO bound work, with all the necessary framing to
inject them into other classes. It also provides a variety of test doubles for different purposes,
also with dependency injection framing, so they can be substituted into applications without
disturbing high-level code. This system provides all the benefits of concurrent programming with
coroutines while keeping testability high and allowing tests to target real infrastructure where
possible, thus ensuring tests exercise real code and provide meaningful results.

## Guide

Anywhere you need a coroutine dispatcher simply inject an [@Io](/first_party/coroutines/io/Io.kt) or [@Cpu](/first_party/coroutines/cpu/Cpu.kt) annotated dispatcher. For example:

```
class Foo @Inject constructor(
  @Cpu private val cpuDispatcher: CoroutineDispatcher,
  @Io private val ioDispatcher: CoroutineDispatcher
) {
  suspend fun sendRequest(request: Request) {
    withContext(ioDispatcher) {
      // Not implemented, present only for example purposes.
    }
  }

  suspend fun calculateFibonacci(n: Int): Int {
    check (n > 0) {
      "n must be greater than zero"
    }
    if (n == 1) return 1
      
    return withContext(cpuDispatcher) {
      var a = 0
      var b = 1
      for (i in 2..n) {
        val next = a + b
        a = b
        b = next
      }

      b
    }
  }

  suspend fun printWithDelay(text: String, delayMs: Int) {
    delay(delayMs)
    println(text)
  }
}
```

The [CoroutinesComponent](/first_party/coroutines/CoroutinesComponent.kt) provides the dispatchers to Dagger and should be included as a component
dependency. It comes in three varieties:

- Production.
- Artificial testing.
- Realistic testing.

In general you should use the production version in production and testing unless you need to
control concurrency in your test, in which case you should choose the appropriate testing dispatcher.
Details for all three are provided below.

### Production

The production variant ultimately delegates to the standard Kotlin dispatchers (`Dispatchers.Io` and
`Dispatchers.Default`) for performance and safety. It is suitable for most production environments
and should be treated as singleton to avoid duplicating your concurrency systems. For example:

```kotlin
class MyApplication : Application() {

  @Inject lateinit var foo: Foo

  override fun onCreate() {
    super.onCreate()
    DaggerApplicationComponent.builder()
        .consuming(DaggerCoroutinesComponentImpl.create())
        .build()
        .inject(this)

    runBlocking {
      val fib10 = foo.calculateFibonacci(10)
      foo.sendRequest(Request("fib(10) is $fib10"))
    }
  }
}

@Component(dependencies = [CoroutinesComponent::class])
interface ApplicationComponent {
  fun inject(application: Application)

  @Component.Builder
  interface Builder {
    fun consuming(coroutines: CoroutinesComponent): Builder
    fun build(): ApplicationComponent
  }
}
```

### Artificial Testing

The artificial testing variant delegates to the standard Kotlin test dispatcher for both CPU and
IO bound work. This means all asynchronous work operates on the test thread and is fundamentally single-threaded, which is useful when testing systems that require a coroutine context but are not
deeply concurrent (i.e. they use suspending functions but are fairly simple). The component exposes a Chronosphere [TestingTaskDriver](/first_party/chronosphere/testingtaskdriver/TestingTaskDriver.kt) that can be used to advance virtual time in millisecond increments. For example:

```kotlin
class MyApplicationTest {
  @Inject lateinit var foo: Foo
  @Inject lateinit var driver: TestingTaskDriver

  private val outContent = java.io.ByteArrayOutputStream()

  @Before fun setUp() {
    DaggerApplicationTestComponent.builder()
        .consuming(
            DaggerArtificialCoroutinesTestingComponentImpl.builder()
                .consuming(DaggerTestingTaskDriverComponentImpl.create())
                .build()
        )
        .build()
        .inject(this)

    System.setOut(java.io.PrintStream(outContent))
  }

  @Test
  fun printWithDelay_delayNotPassed_doesNotPrint() {
    foo.printWithDelay("Hello, world!", 10)

    driver.advanceBy(9)

    assertThat(outContent.toString().trim()).isEmpty()
  }
  
  @Test
  fun printWithDelay_delayPassed_prints() {
    foo.printWithDelay("Hello, world!", 10)

    driver.advanceBy(10)

    assertThat(outContent.toString().trim()).isEqualTo("Hello, world!")
  }
}

@Component(dependencies = [ArtificialCoroutinesTestingComponent::class])
interface ApplicationTestComponent {
  fun inject(test: MyApplicationTest)

  @Component.Builder
  interface Builder {
    fun consuming(coroutines: ArtificialCoroutinesTestingComponent): Builder
    fun build(): ApplicationTestComponent
  }
}
```

Overall this variant allows you to reason about your tests as single-threaded but coroutine-compatible environments, but it does not exercise your system in a realistic environment,
and thus does not provide the same guarantees as the realistic testing variant.

### Realistic Testing

The realistic testing variant ultimately delegates to custom executors backed by a JVM
thread pool. This means all asynchronous work operates off the test thread and is fundamentally a
realistic reflection of production concurrency, which is useful when testing systems that require a
multi-threaded environment to avoid thread exhaustion and other such issues. The component exposes a Chronosphere [TestingTaskBarrier](/first_party/chronosphere/testingtaskbarrier/TestingTaskBarrier.kt) that can be used to block tests until work
completes. For example:

```kotlin
@Component(
  // Pretend this module provides the endpoint Foo actually writes to.
  modules = [FooEndpointModule::class],
  dependencies = [RealisticCoroutinesTestingComponent::class])
interface ApplicationTestComponent {
  fun inject(test: MyApplicationTest)

  @Component.Builder
  interface Builder {
    fun consuming(coroutines: RealisticCoroutinesTestingComponent): Builder
    fun build(): ApplicationTestComponent
  }
}

class MyApplicationTest {
  @Inject lateinit var foo: Foo
  @Inject lateinit var barrier: TestingTaskBarrier

  // Pretend this is where Foo actually sends network requests
  @Inject lateinit var fooEndpoint: FooEndpoint

  @Before fun setUp() {
    DaggerApplicationTestComponent.builder()
        .consuming(
            DaggerRealisticCoroutinesTestingComponentImpl.builder()
                .consuming(DaggerTestingTaskBarrierComponentImpl.create())
                .build()
        )
        .build()
        .inject(this)
  }
  
  @Test fun test_manyNetworkCalls_allComplete() = runBlocking {
    for (i in 0..100) {
      foo.sendRequest(Request("request $i"))
    }

    barrier.awaitAllIdle()

    assertThat(fooEndpoint.successCount).isEqualTo(100)
  }
}
```

Overall this variant allows
you to reason about tests as single-thread environments that interact with realistic multi-threaded
environments, thereby ensuring your system under test is being exercised as it would in production.

## Issues

Issues relating to this package and its subpackages are tagged with `coroutines`.

## Contributions

Third-party contributions are accepted.