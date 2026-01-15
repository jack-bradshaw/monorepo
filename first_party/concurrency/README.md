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

- The [production component](/first_party/concurrency/ConcurrencyComponent.kt), which provides
  production versions.
- The [test component](/first_party/concurrency/testing/TestConcurrencyComponent.kt), which provides
  test doubles.

The test component extends the production component; therefore, the test component can be
substituted anywhere the production component is required. Furthermore, an equivalent Kotlin factory
function is provided for each, specifically `com.jackbradshaw.concurrency.concurrencyComponent` for
the former, and `com.jackbradshaw.concurrency.testing.testConcurrencyComponent` for the latter.

## Example

The following example demonstrates a production class `Foo` which uses `Pulsar` to drive a loop, and
a test setup that replaces it with the `TestPulsar` testing double in a test. The example uses the
[test coroutines infrastructure](/first_party/coroutines/testing), but the concurrency
infrastructure does not actually depend on it.

```kotlin
import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.concurrency.ConcurrencyComponent
import com.jackbradshaw.concurrency.concurrencyComponent
import com.jackbradshaw.concurrency.pulsar.Pulsar
import com.jackbradshaw.concurrency.pulsar.testing.TestPulsar
import com.jackbradshaw.concurrency.testing.testConcurrencyComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.testing.launcher.Launcher
import com.jackbradshaw.coroutines.testing.testCoroutinesComponent
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

@Component(dependencies = [ConcurrencyComponent::class])
interface FooComponent {

  fun provideFoo(): Foo

  @Component.Builder
  interface Builder {
    fun consuming(concurrency: ConcurrencyComponent): Builder

    fun build(): FooComponent
  }
}

fun fooComponent(concurrency: ConcurrencyComponent = concurrencyComponent()) =
    DaggerFooComponent.builder().consuming(concurrency).build()

class Main {
  fun main(args: Array<String>) {
    runBlocking {
      val foo = fooComponent().provideFoo()
      foo.doWork()
    }
  }
}

@RunWith(JUnit4::class)
class FooTest {

  @Inject lateinit var testScope: TestScope
  @Inject lateinit var launcher: Launcher
  @Inject lateinit var testPulsar: TestPulsar
  @Inject lateinit var foo: Foo

  @Before
  fun setup() {
    DaggerFooTestComponent.builder()
        .consuming(fooComponent(concurrency = testConcurrencyComponent()))
        .consuming(testCoroutinesComponent())
        .build()
        .inject(this)
  }

  @Test
  fun eachLoopIncrementsCounter(): Unit = testScope.runTest {
    launcher.launchEagerly { foo.doWork() }

    repeat(3) {
      testPulsar.emit()
      testScope.testScheduler.advanceUntilIdle()
    }
    assertThat(foo.loopCount).isEqualTo(3)
  }
}

@Component(dependencies = [FooComponent::class, CoroutinesComponent::class])
interface FooTestComponent {
  fun inject(target: FooTest)

  @Component.Builder
  interface Builder {
    fun consuming(fooComponent: FooComponent): Builder

    fun consuming(coroutines: CoroutinesComponent): Builder

    fun build(): FooTestComponent
  }
}
```

## Issues

Issues relating to this package and its subpackages are tagged with `concurrency`.

## Contributions

Third-party contributions are accepted.
