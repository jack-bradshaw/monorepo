# Quinn ("Queue In")

A bridge between single-threaded environments and complex multi-threaded ones.

## The Dual Problem: Resource Confinement and Execution Extension

When bridging a complex asynchronous environment (like Kotlin coroutines) with a rigid,
single-threaded framework, two distinct architectural friction points emerge:

1. Resource Confinement: Frameworks often strictly isolate their resources to a single thread. For
   example, UI elements safely cannot be altered by non-main threads. Often, if you try to affect
   them from another thread, an exception will be raised.
1. Execution Extension: Synchronous frameworks usually assume that once their main entry point
   returns, all processing has finished and the process can be terminated. If you launch background
   asynchronous workers and return control to the framework, those workers will be abruptly stopped.

A prime example is the Kotlin Symbol Processing (KSP) framework. KSP uses a strictly single-threaded
event loop as its central entry point and supplies a `Resolver` object for accessing the underlying
abstract syntax tree. The API is approximately:

```kotlin
class MySymbolProcessor() : SymbolProcessor {

  override fun process(resolver: Resolver): List<KSAnnotated> = runBlocking {
    // Do work here with resolver. Process may be killed after this function returns.
  }
}
```

The `Resolver` object cannot be safely used from other threads without risking deadlocks or native
crashes; furthermore, when the KSP `process()` method natively returns, the `Resolver` is discarded
and KSP may end the process if there is no more work. This risks the immediate cancellation of any
background work that was launched using coroutines or other frameworks, with no way for more complex
architectures to use the `Resolver`. These constraints rigidly bind the architecture of the broader
program by limiting the concurrency model and restricting the general design to a single-threaded,
single-callback API.

## The Solution: Quinn

[Quinn](/first_party/quinn/core/Quinn.kt) solves both problems simultaneously by completely
decoupling where reusable logic is defined from where that logic is natively executed. It inverts
the standard processing model, so instead of background workers attempting to fetch restricted
objects (pull), they dynamically construct logic blocks (lambdas) and `run` them with `Quinn`.
Concurrently, the restricted framework thread calls `execute()` on `Quinn`, which indefinitely
repeatedly polls and executes received work on the framework thread.

`Quinn` encapsulates all the complex multi-threading and cross-thread orchestration: Higher level
components simply pass in lambdas and the underlying event loop simply passes in the resources those
lambdas need. It explicitly keeps the framework process alive, preventing teardown while
asynchronous background workers are active, and avoiding sleeping the main thread. While suspended
indefinitely, the framework thread continuously evaluates the queue, natively evaluating the logic
blocks, and eagerly supplying its highly restricted resources directly into them.

Once the higher-level code has finished, it simply invokes `close()` on the `Quinn` instance. This
causes execution to gracefully complete so the framework loop can complete and pass control back to
the framework.

## Usage

Using `Quinn` is straightforward:

1. Create an instance and share it between the submission side and execution side.
1. Feed in blocks on the submission side with `run`.
1. Supply resources and trigger execution on the execution side with `execute`.

For example consider a KSP processor that uses `Quinn` to expose its `Resolver` and keep its
`process` callback alive:

```kotlin
// In MySymbolProcessor.kt

/** The restricted, single-threaded entrypoint provided natively by KSP. */
class MySymbolProcessor(private val quinnFactory: Quinn.Factory) : SymbolProcessor {

  override fun process(resolver: Resolver): List<KSAnnotated> = runBlocking {
    val quinn = quinnFactory.createQuinn<Resolver>()

    launchBackgroundWork(quinn)

    // Keeps executing until `quinn.close()` is invoked to prevent `process` from returning.
    quinn.execute(resolver)

    return@runBlocking emptyList()
  }

  /**
   * Background worker that prints the file it discovers.
   *
   * Uses [quinn] to submit logic to the KSP thread.
   */
  private fun launchBackgroundWork(quinn: Quinn<Resolver>) {
    GlobalScope.launch {
      // Work is defined here but executed by the `execute` call in `process`.
      quinn.run { resolver ->
        val filePaths = resolver.getAllFiles().map { it.filePath }
        println("Processing files: $filePaths")
      }

      // Only reaches here after the `run` block has been processed.
      quinn.close()
    }
  }
}
```

The example launches background coroutines that require the single-threaded `Resolver`, but rather
than saving the resolver in a member variable and accessing it from the coroutines (which would
fail), the workers package their logic into lambdas and pass it to `Quinn` so it can be executed on
the KSP thread. The KSP thread runs the `execute` loop to safely evaluate each block with the
restricted `Resolver` resources on the main thread. When all asynchronous tasks are complete, a
coroutine calls `close` on the `Quinn` instance which ends the loop on the KSP thread so that
`process` can return.

## Interface Segregation

`Quinn` is composed of two interfaces: [SubmittableQuinn](/first_party/quinn/core/Quinn.kt) which
can only receive work, and [ExecutableQuinn](/first_party/quinn/core/Quinn.kt) which can only
execute work. This ensures interface segregation so that different parts of the application only
have access to the elements they need. The above example is intentionally simplistic, with the
`Quinn` being used in the same class, but in a more realistic scenario where the submitter is a
separate class, that class should only receive the `SubmittableQuinn`, and the executor should only
receive the `ExecutableQuinn`. Using dependency injection makes this straightforward and is
recommended.

## Other Frameworks

The Kotlin coroutine dispatcher system is similar to `Quinn` but has a major difference: A coroutine
dispatcher only queues work for execution; it does not inject restricted resources from the
underlying system into that queued work, and it fundamentally models all work as basic Runnables.
Furthermore, a dispatcher-based system does not inherently provide a native way for higher-level
asynchronous systems to signal "done" to the underlying loop. `Quinn` provides both of these
capabilities, making it the superior structural fit for single-thread resource bridging.

## Issues

Issues relating to this package and its subpackages are tagged with `quinn`.

## Contributions

Contributions from third parties are accepted.
