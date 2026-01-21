# Wheatley

Test infrastructure for contract-based testing.

## ContractTest

The `ContractTest` interface defines the standard contract for abstract test classes that verify implementations against behavioral contracts.

### Architecture

The abstract test pattern separates test logic from test infrastructure through interface-based inheritance:

- **Abstract test classes** define behavioral expectations by implementing `ContractTest<T, C>` and providing test methods with complete assertion logic
- **Concrete test classes** provide dependency injection and infrastructure by extending abstract tests and implementing setup/teardown/subject methods

This separation ensures test logic is written once in the abstract class and  applied to all implementations, while each implementation provides its own infrastructure.

### Components

**Abstract Test Classes**

Implement `ContractTest<T, C>` where `T` is the interface under test and `C` is the configuration type.

Requirements:
- Define test methods annotated with `@Test` containing complete assertion logic
- Declare abstract helper methods for test-specific operations
- Call `setupSubject(config)` before accessing `subject()` in each test
- Call `teardownSubject()` after test completion

**Concrete Test Classes**

Extend abstract test classes and provide infrastructure.

Requirements:
- Inject dependencies using a Dagger component
- Implement `setupSubject(config)` to create and invoke the component with provided configuration
- Implement `subject()` to return the injected subject (same instance across calls)
- Implement `teardownSubject()` to release resources and clean up test state
- Implement abstract helper methods with infrastructure-specific logic

### Example

Abstract test class defining behavioral contract:

```kotlin
abstract class LauncherTest : ContractTest<Launcher, LauncherConfig> {
  
  @Test
  fun launchEagerly_runsWithoutFurtherPrompting(): Unit = runBlocking {
    setupSubject(LauncherConfig.DEFAULT)
    
    var didStart = false
    val job = subject().launchEagerly { didStart = true }
    
    assertThat(didStart).isTrue()
    
    teardownSubject()
  }
  
  abstract fun runScheduledWork()
}
```

Concrete test class providing infrastructure:

```kotlin
class LauncherImplTest : LauncherTest() {
  
  @Inject lateinit var launcher: Launcher
  @Inject lateinit var testScope: TestScope
  
  override fun setupSubject(config: LauncherConfig) {
    DaggerTestComponent.builder()
      .binding(config)
      .build()
      .inject(this)
  }
  
  override fun subject() = launcher
  
  override fun teardownSubject() {
    testScope.cleanupTestCoroutines()
  }
  
  override fun runScheduledWork() {
    testScope.testScheduler.advanceUntilIdle()
  }
}
```

### Benefits

- **Contract enforcement:** All implementations tested against the same behavioral contract
- **Code reuse:** Test logic written once in abstract class, applied to all implementations
- **Consistency:** Uniform testing ensures all implementations behave identically
- **Maintainability:** Changes to test logic occur in a single location
- **Separation of concerns:** Test logic isolated from test infrastructure
- **Resource management:** Teardown lifecycle ensures proper cleanup
