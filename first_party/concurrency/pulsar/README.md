# Pulsar

A Kotlin-flow-based looper for repeated/indefinite work execution.

## Overview

Whenever you need to perform work repeatedly, you can easily use a `while(true)` loop, but this is
difficult to control in tests and is not necessarily the most ergonomic API. Pulsar is a Kotlin
Flow-based looper that can be injected with Dagger for ergonomic use and improved testability.

## Usage

Below is an example of a class that uses `Pulsar` to drive a loop:

```kotlin
import com.jackbradshaw.concurrency.pulsar.Pulsar
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

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
```

The Dagger [PulsarComponent](/first_party/concurrency/pulsar/PulsarComponent.kt) can be used to
supply Pulsar objects, for example:

```kotlin
import com.jackbradshaw.concurrency.pulsar.PulsarComponent
import dagger.Component

@Component(dependencies = [PulsarComponent::class])
interface FooComponent {

  fun provideFoo(): Foo

  @Component.Builder
  interface Builder {
    fun consuming(pulsar: PulsarComponent): Builder

    fun build(): FooComponent
  }
}

val fooComponent = DaggerFooComponent.builder().consuming(pulsarComponent()).build()
```

Alternatively, pulsars can be directly obtained from the component, for example:

```kotlin
import com.jackbradshaw.concurrency.pulsar.pulsarComponent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

pulsarComponent().pulsar().pulses().onEach { println("doing work") }.collect()
```

## Testing

The Pulsar test double does not emit pulses on its own, instead it exposes an `emit` function that
can be called to manually trigger a pulse. This is useful in tests because it makes pulsar directly
controllable and allows an idle-state to be reached when using
[Chronosphere](/first_party/chronosphere). For example:

```kotlin
import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.concurrency.pulsar.testing.TestPulsar
import com.jackbradshaw.concurrency.pulsar.testing.testPulsarComponent
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrierComponent
import com.jackbradshaw.chronosphere.testingtaskbarrier.testingTaskBarrierComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FooTest {

  @Inject lateinit var testScope: TestScope

  @Inject lateinit var taskBarrier: TestingTaskBarrier

  @Inject lateinit var testPulsar: TestPulsar

  @Inject lateinit var foo: Foo

  @Before
  fun setup() {
    val taskBarrierComponent = testingTaskBarrierComponent()
    val coroutinesComponent = realisticCoroutinesTestingComponent(taskBarrierComponent)

    DaggerFooTestComponent.builder()
        .consuming(
            DaggerFooComponent.builder()
                .consuming(testPulsarComponent())
                .build()
        )
        .consuming(coroutinesComponent)
        .consuming(taskBarrierComponent)
        .build()
        .inject(this)
  }

  @Test
  fun eachLoopIncrementsCounter(): Unit = testScope.runTest {
    launch { foo.doWork() }
    taskBarrier.waitUntilIdle()

    repeat(3) {
      testPulsar.emit()
      taskBarrier.waitUntilIdle()
    }

    assertThat(foo.loopCount).isEqualTo(3)
  }
}

@Component(dependencies = [
    FooComponent::class,
    CoroutinesComponent::class,
    TestingTaskBarrierComponent::class
])
interface FooTestComponent {
  fun inject(target: FooTest)

  @Component.Builder
  interface Builder {
    fun consuming(fooComponent: FooComponent): Builder
    fun consuming(coroutines: CoroutinesComponent): Builder
    fun consuming(taskBarrier: TestingTaskBarrierComponent): Builder
    fun build(): FooTestComponent
  }
}
```

The test double can be obtained from the
[test component](/first_party/concurrency/pulsar/testing/TestPulsarComponent.kt) (similar to the
production component).
