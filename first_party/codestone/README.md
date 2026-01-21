# Codestone

**Framework-agnostic lifecycle and work orchestration for Android applications.**

Codestone provides a type-safe, composable architecture for managing application state, navigation, and asynchronous work across different execution frameworks (Coroutines, Futures, custom lifecycles).

---

## Why Codestone?

### The Problem

Modern Android applications face several architectural challenges:

1. **Framework Lock-in**: Tightly coupling to Coroutines, RxJava, or Futures makes migration difficult
2. **Lifecycle Complexity**: Managing lifecycles across Activities, Fragments, ViewModels, and background work
3. **Type Erasure**: Losing type information when converting between async primitives
4. **Navigation Coupling**: UI navigation logic mixed with business logic

### The Solution

Codestone solves these problems through:

- **Work Abstraction**: Universal `Work<T>` wrapper that preserves type information across frameworks
- **Lifecycle Primitives**: `StartStop` interface for consistent lifecycle management
- **Automatic Conversion**: Convert between Coroutines, Futures, and custom work types transparently
- **Navigation Framework**: Type-safe `Destination` and `Navigator` abstractions

---

## Core Concepts

### Work

`Work<T>` is the fundamental abstraction representing executable work with a handle and type:

```kotlin
interface Work<out T> {
  val handle: T           // The actual work (Job, Future, StartStop, etc.)
  val workType: KType     // Runtime type information
}
```

**Create Work instances**:

```kotlin
// Coroutine work
val coroutineWork: Work<Job> = ktCoroutineWork(job)

// Future work
val futureWork: Work<ListenableFuture<Unit>> = listenableFutureWork(future)

// StartStop work
val startStopWork: Work<StartStop<*, *>> = startStopWork(startStop)
```

### StartStop

`StartStop<R, T>` is a lifecycle interface for work that can be started, stopped, and produces results or errors:

```kotlin
interface StartStop<R, T : Throwable> {
  fun start()
  fun abort()
  fun complete(result: R)
  fun fail(error: T)
  
  val state: StateFlow<ExecutionState>
}
```

**Usage**:

```kotlin
class MyOperation : StartStopImpl<String, Throwable>() {
  override suspend fun onStart() {
    // Perform work
    complete("Success!")
  }
}

val operation = MyOperation()
operation.start()
```

### Sustainable

`Sustainable<W>` represents something that can be sustained (kept alive) by providing work:

```kotlin
interface Sustainable<out W : Work<*>> {
  val work: W
}
```

**Create Sustainables**:

```kotlin
// From coroutine block
val sustainable = ktCoroutineSustainable(scope) {
  // Coroutine work
}

// From StartStop
val sustainable = startStopSustainable(operation)
```

### WorkOrchestrator

`WorkOrchestrator<O>` manages the lifecycle of multiple `Sustainable` instances, converting between work types automatically:

```kotlin
interface WorkOrchestrator<O : Work<*>> : Sustainable<O> {
  fun sustain(sustainable: Sustainable<*>)
  fun release(sustainable: Sustainable<*>)
  fun releaseAll()
}
```

**Usage**:

```kotlin
// Create orchestrator for Job work
val orchestrator = WorkOrchestrators.create<Work<Job>>(
  target = typeOf<Work<Job>>(),
  integrationScope = coroutineScope
)

// Start the orchestrator
orchestrator.work.handle.start()

// Sustain various work types - they're automatically converted
orchestrator.sustain(ktCoroutineSustainable(scope) { /* work */ })
orchestrator.sustain(startStopSustainable(operation))
orchestrator.sustain(listenableFutureSustainable(future))

// Stop everything
orchestrator.releaseAll()
```

---

## Navigation Framework

### Destination

`Destination<U, W>` represents a UI destination with associated work:

```kotlin
interface Destination<U : Ui, W : Work<*>> : Usable<U>, Sustainable<W>
```

### Navigator

`Navigator` translates signals (user actions, deep links) into destinations:

```kotlin
interface Navigator<in A, in E, U : Ui, out D : Destination<U, *>, W : Work<*>> 
  : Sustainable<W> {
  
  fun translateSignalFromApplication(signal: A): D?
  fun translateSignalFromEnvironment(signal: E): D?
}
```

### Coordinator

`Coordinator` orchestrates navigation by managing navigators and destinations:

```kotlin
interface Coordinator<in A, in E, U : Ui, D : Destination<U, *>, 
  N : Navigator<A, E, U, D, *>, W : Work<*>> : Sustainable<W> {
  
  val navigator: MutableStateFlow<N?>
  val destination: StateFlow<D?>
  
  fun onSignalFromApplication(signal: A)
  fun onSignalFromEnvironment(signal: E)
}
```

**Example**:

```kotlin
// Define destinations
class HomeDestination : AndroidDestination<Work<StartStop<*, *>>> {
  override val ui = HomeScreen()
  override val work = startStopWork(HomeOperation())
}

// Define navigator
class AppNavigator : AndroidNavigator<String, Intent, Work<StartStop<*, *>>> {
  override fun translateSignalFromApplication(signal: String) = when(signal) {
    "home" -> HomeDestination()
    "profile" -> ProfileDestination()
    else -> null
  }
  
  override val work = startStopWork(NavigatorOperation())
}

// Use coordinator
val coordinator = CoordinatorImpl<String, Intent, AndroidUi, 
  AndroidDestination<*>, AppNavigator>()

coordinator.navigator.value = AppNavigator()
coordinator.onSignalFromApplication("home") // Navigate to home
```

---

## Platform Support

Codestone supports multiple async frameworks through automatic conversion:

### Coroutines

```kotlin
// Create coroutine work
val work = ktCoroutineWork(job)

// Create sustainable from coroutine block
val sustainable = ktCoroutineSustainable(scope) {
  delay(1000)
  println("Done!")
}
```

### ListenableFuture

```kotlin
// Create future work
val work = listenableFutureWork(future)

// Futures are automatically converted when sustained
orchestrator.sustain(futureSustainable)
```

### StartStop (Custom Lifecycle)

```kotlin
// Create StartStop work
val work = startStopWork(operation)

// Create sustainable
val sustainable = startStopSustainable(operation)
```

### Automatic Conversion

The `MultiConverter` automatically routes work through `StartStop` as an intermediate:

```
Work<Job> → Work<StartStop<*, *>> → Work<ListenableFuture<Unit>>
```

This enables framework-agnostic orchestration without manual conversion.

---

## Android Integration

### AndroidRoot

`AndroidRoot` is the application-level entry point:

```kotlin
class MyApp : AndroidRoot<MyApp, MyNavigator>() {
  override val coordinator = CoordinatorImpl<...>()
  
  override fun onCreate() {
    super.onCreate()
    // App initialization
  }
}
```

### AndroidDestination

```kotlin
class MyDestination : AndroidDestination<Work<StartStop<*, *>>> {
  override val ui: AndroidUi = MyComposeUi()
  override val work = startStopWork(MyOperation())
}
```

### AndroidNavigator

```kotlin
class MyNavigator : AndroidNavigator<UserAction, Intent, Work<StartStop<*, *>>> {
  override fun translateSignalFromApplication(signal: UserAction) = 
    when(signal) {
      is UserAction.OpenProfile -> ProfileDestination()
      else -> null
    }
  
  override fun translateSignalFromEnvironment(signal: Intent) =
    when(signal.action) {
      Intent.ACTION_VIEW -> DeepLinkDestination(signal.data)
      else -> null
    }
  
  override val work = startStopWork(NavigatorLifecycle())
}
```

---

## Architecture Benefits

### Type Safety

Runtime type information is preserved through `KType`, enabling type-safe conversions:

```kotlin
val work: Work<Job> = ktCoroutineWork(job)
work.workType // typeOf<Job>()
```

### Framework Agnostic

Switch between Coroutines, Futures, or custom frameworks without changing business logic:

```kotlin
// Today: Coroutines
orchestrator.sustain(ktCoroutineSustainable(scope) { /* work */ })

// Tomorrow: Futures (same orchestrator!)
orchestrator.sustain(futureSustainable(future))
```

### Composable

Small, focused abstractions compose into complex behaviors:

```kotlin
Work + Sustainable + WorkOrchestrator = Lifecycle Management
Destination + Navigator + Coordinator = Navigation Framework
```

### Testable

Pure interfaces enable easy mocking and testing:

```kotlin
class FakeNavigator : Navigator<String, Intent, Ui, Destination<*, *>, Work<*>> {
  override fun translateSignalFromApplication(signal: String) = 
    FakeDestination()
  override val work = startStopWork(FakeOperation())
}
```

---

## Project Structure

```
codestone/
├── sustainment/
│   ├── primitives/           # Core abstractions (Work, Sustainable)
│   ├── operation/            # StartStop lifecycle
│   ├── conversion/           # Work type converters
│   │   ├── coroutines/       # Coroutine support
│   │   ├── listenablefutures/# Future support
│   │   ├── operation/        # StartStop helpers
│   │   └── multiconverter/   # Automatic routing
│   └── orchestration/        # WorkOrchestrator
├── interaction/
│   └── foundation/           # Navigation framework
│       ├── Destination.kt
│       ├── Navigator.kt
│       ├── Coordinator.kt
│       ├── android/          # Android implementations
│       └── omniform/         # Generic implementations
└── ui/
    └── platforms/
        └── android/          # Android UI primitives
```

---

## Getting Started

### 1. Add Dependency

```kotlin
dependencies {
  implementation("//first_party/codestone")
}
```

### 2. Create Application

```kotlin
class MyApp : AndroidRoot<MyApp, MyNavigator>() {
  override val coordinator = CoordinatorImpl<...>()
}
```

### 3. Define Destinations

```kotlin
class HomeDestination : AndroidDestination<Work<StartStop<*, *>>> {
  override val ui = HomeScreen()
  override val work = startStopWork(HomeOperation())
}
```

### 4. Implement Navigator

```kotlin
class MyNavigator : AndroidNavigator<...> {
  override fun translateSignalFromApplication(signal: String) = 
    when(signal) {
      "home" -> HomeDestination()
      else -> null
    }
  override val work = startStopWork(NavigatorOperation())
}
```

### 5. Navigate

```kotlin
coordinator.navigator.value = MyNavigator()
coordinator.onSignalFromApplication("home")
```

---

## Advanced Usage

### Custom Work Types

Implement `Work<T>` for custom async primitives:

```kotlin
fun customWork(handle: MyAsyncType) = object : Work<MyAsyncType> {
  override val handle = handle
  override val workType = typeOf<MyAsyncType>()
}
```

### Custom Platforms

Implement `Platform<Work<T>>` to add converter support:

```kotlin
class MyPlatform : Platform<Work<MyAsyncType>> {
  override fun forwardsUniConverter() = 
    Pair(..., MyForwardsConverter())
  override fun backwardsUniConverter() = 
    Pair(..., MyBackwardsConverter())
  override fun passThroughUniConverter() = 
    Pair(..., MyPassThroughConverter())
}
```

---

## Design Philosophy

Codestone follows these principles:

1. **Separation of Concerns**: Work, lifecycle, and navigation are distinct
2. **Type Safety**: Preserve type information at runtime
3. **Composability**: Small pieces combine into larger systems
4. **Framework Agnostic**: No lock-in to specific async frameworks
5. **Testability**: Pure interfaces enable easy testing

---

## License

[Your License Here]

---

## Contributing

[Contribution guidelines]
