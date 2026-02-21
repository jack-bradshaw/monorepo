# Concurrency

Kotlin concurrency infrastructure.

## Release

Not released to third party package managers.

## Utilities

This package provides the following utilities:

- [Pulsar](/first_party/concurrency/pulsar): A Kotlin-flow-based looper for repeated/indefinite work
  execution. A [test double](/first_party/concurrency/pulsar/testing) is provided for controlling
  loop execution in tests.

The utilities are integrated/exposed using [Dagger](https://dagger.dev), and two components are
provided:

- The [production component](/first_party/concurrency/Concurrency.kt), which provides production
  versions.
- The [test component](/first_party/concurrency/testing/TestConcurrency.kt), which provides test
  doubles.

The test component extends the production component; therefore, the test component can be
substituted anywhere the production component is required. Furthermore, an equivalent Kotlin factory
function is provided for each, specifically `com.jackbradshaw.concurrency.concurrency` for the
former, and `com.jackbradshaw.concurrency.testing.testConcurrency` for the latter.

## Example

The following example demonstrates a production class `Foo` which uses `Pulsar` to drive a loop, and
a test setup that replaces it with the `TestPulsar` testing double in a test. The example uses the
[test coroutines infrastructure](/first_party/coroutines/testing), but the concurrency
infrastructure does not actually depend on it.

```kotlin
import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.concurrency.Concurrency
import com.jackbradshaw.concurrency.concurrency
import com.jackbradshaw.concurrency.pulsar.Pulsar
import com.jackbradshaw.concurrency.testing.pulsar.TestPulsar
import com.jackbradshaw.concurrency.testing.testConcurrency
import com.jackbradshaw.coroutines.Coroutines
import com.jackbradshaw.coroutines.testing.launcher.Launcher
import com.jackbradshaw.coroutines.testing.testCoroutines
import dagger.Component
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class Foo @Inject constructor(private val pulsar: Pulsar) {

  var loopCount = 0

  suspend fun doWork() {
    pulsar
        .pulses()
        .onEach {
          println("doing work...")
          loopCount++
        }
        .collect()
  }
}

@Component(dependencies = [Concurrency::class])
interface FooComponent {

  fun provideFoo(): Foo

  @Component.Builder
  interface Builder {
    fun setConcurrency(concurrency: Concurrency): Builder

    fun build(): FooComponent
  }
}

class Main {
  fun main(args: Array<String>) {
    runBlocking {
      val fooComponent = DaggerFooComponent.builder().setConcurrency(concurrency()).build()
      val foo = fooComponent.provideFoo()
      foo.doWork()
    }
  }
}

@RunWith(JUnit4::class)
class Test {

  @Inject lateinit var testScope: TestScope
  @Inject lateinit var launcher: Launcher
  @Inject lateinit var testPulsar: TestPulsar
  @Inject lateinit var foo: Foo

  @Before
  fun setup() {
    DaggerTestComponent.builder()
        .setFooComponent(DaggerFooComponent.builder().setConcurrency(testConcurrency()).build())
        .setCoroutines(testCoroutines())
        .build()
        .inject(this)
  }

  @Test
  fun eachLoopIncrementsCounter(): Unit = runBlocking {
    launcher.launchEagerly { foo.doWork() }

    repeat(3) {
      testPulsar.emit()
      testScope.testScheduler.advanceUntilIdle()
    }
    assertThat(foo.loopCount).isEqualTo(3)
  }
}

@Component(dependencies = [FooComponent::class, Coroutines::class])
interface TestComponent {
  fun inject(target: Test)

  @Component.Builder
  interface Builder {
    fun setFooComponent(fooComponent: FooComponent): Builder

    fun setCoroutines(coroutines: Coroutines): Builder

    fun build(): TestComponent
  }
}
```

## Issues

Issues relating to this package and its subpackages are tagged with `concurrency`.

## Contributions

Third-party contributions are accepted.
