# Testing

Test utilities for Kotlin coroutines.

## Components

The testing package provides utilities that work together to control test execution. All utilities operate on the same underlying `TestCoroutineScheduler`, ensuring coordinated behavior.

### TestCoroutines Component

The `TestCoroutinesComponent` extends the production `CoroutinesComponent` with test-specific bindings:

- `testScope()`: Returns the `TestScope` for running tests with virtual time.
- `launcher()`: Returns a `Launcher` for scheduling work on the test scope.
- `advancer()`: Returns an `Advancer` for controlling the virtual clock.

All three utilities are integrated such that `Launcher` schedules work in `testScope`, and `Advancer` controls the virtual time of `testScope`. This integration ensures predictable test execution.

### Launcher

The `Launcher` schedules coroutine work for execution. It provides:

- `launchEagerly(block)`: Immediately starts the block on the test scope.
- `launchLazily(block)`: Creates a lazy job that starts when explicitly called.

Launched work executes on the `TestScope` managed by `TestCoroutinesComponent`.

### Advancer

The `Advancer` provides high-level control over virtual time in tests, decoupling test code from specific dispatcher implementations.

#### Operations

**`advanceThroughTick()`**  
Advances through a single "tick" of execution:
- Runs all tasks scheduled for the current virtual time
- Recursively executes any new tasks scheduled for the current time (e.g., immediate `launch`)
- Does not advance the virtual clock
- Equivalent to `runCurrent()` but with a clearer name

Use this to verify intermediate states or check behavior of unconfined/immediate coroutines.

**`advanceUntilIdle()`**  
Advances through all scheduled work until the scheduler is idle:
- Executes all tasks, including those with delays
- Advances the virtual clock as needed to reach the next scheduled tasks
- Blocks until no more work remains

Use this to ensure all asynchronous operations have completed before making final assertions.

## Usage

Inject `TestCoroutinesComponent` into test infrastructure:

```kotlin
class MyTest {
  @Inject lateinit var advancer: Advancer
  @Inject lateinit var launcher: Launcher
  @Inject lateinit var testScope: TestScope
  
  @Before
  fun setup() {
    DaggerTestComponent.builder().build().inject(this)
  }
  
  @Test
  fun testSomething() = runTest {
    // Launch work
    launcher.launchEagerly { subject.doSomething() }
    
    // Check immediate side effects
    advancer.advanceThroughTick()
    assertThat(subject.state).isEqualTo(INTERMEDIATE)
    
    // Finish all work
    advancer.advanceUntilIdle()
    assertThat(subject.state).isEqualTo(DONE)
  }
}
```

## Architecture

All test utilities share the same `TestCoroutineScheduler` instance, ensuring:

- Consistent virtual time across all components
- Predictable execution order
- Reliable test repeatability

The `TestScope` provides the scheduler, `Launcher` schedules work on it, and `Advancer` controls its clock. This architectural integration eliminates race conditions and timing-dependent test failures.
