package com.jackbradshaw.quinn.core

import com.jackbradshaw.closet.observable.ObservableClosable

/**
 * The submission-side of a [Quinn] instance.
 *
 * [Quinn] is divided into two interfaces for API segregation:
 * - [SubmittableQuinn], this interface, which contains the APIs for submitting work.
 * - [ExecutableQuinn], which contains the APIs for executing work.
 *
 * The segregation allows different functions to be exposed to different contexts to avoid API
 * leakage and accidental evaluation of the wrong logic (i.e. the submission layer of the consuming
 * application cannot accidentally invoke execution).
 *
 * View the docs of [Quinn] for full details.
 */
interface SubmittableQuinn<T> : ObservableClosable {
  /**
   * Schedules [block] for execution in the execution context and suspends until [block] has been
   * run.
   *
   * Strict FIFO ordering is used, and implementations must ensure this function is thread-safe;
   * however, submission race conditions may lead to non-deterministic queue order, so consumers
   * must use external synchronisation if strict execution ordering is required.
   *
   * Closure details:
   * - If invoked after [close], throws an [IllegalStateException] immediately.
   * - If [close] is called after this function is invoked but before [block] begins evaluation, no
   *   exception is thrown, [block] is discarded without evaluation, and the suspended coroutine
   *   resumes (i.e. this function exits).
   * - If [close] is called while [block] is being evaluated, [block] will complete before closure
   *   proceeds, and no further blocks will be evaluated.
   *
   * WARNING: Using the supplied lambda argument (T) outside of [block] is unsupported and not
   * recommended, as the entire purpose of Quinn is accessing thread-bound resources safely, and
   * using them outside the lambdas is likely to cause errors.
   *
   * WARNING: It is unsafe to make calls to this [SubmittableQuinn] from the [block] lambda as
   * implementations are free to use non-reentrant locks, and they likely will due to the
   * multithreaded nature of Quinn.
   */
  suspend fun run(block: (T) -> Unit)

  /**
   * Identical to [run], except it returns `false` instead of throwing an [IllegalStateException] if
   * this [SubmittableQuinn] is closed when called, and otherwise returns `true` after resuming.
   * Implementations should avoid using try/catch where possible for performance optimization.
   *
   * Note: The return type does not indicate whether [block] was actually executed, only whether it
   * was successfully scheduled without error, since this Quinn could be closed after submission but
   * before execution.
   */
  suspend fun tryRun(block: (T) -> Unit): Boolean
}

/**
 * The execution-side of a [Quinn] instance.
 *
 * [Quinn] is divided into two interfaces for API segregation:
 * - [ExecutableQuinn], this interface, which contains the APIs for executing work.
 * - [SubmittableQuinn], which contains the APIs for submitting work.
 *
 * The segregation allows different functions to be exposed to different contexts to avoid API
 * leakage and accidental execution logic evaluation (i.e. the execution layer of the consuming
 * application cannot accidentally schedule infinite recursive logic).
 *
 * View the docs of [Quinn] for full details.
 */
interface ExecutableQuinn<T> : ObservableClosable {
  /**
   * Executes the work submitted to the associated [SubmittableQuinn] with [resource] as the lambda
   * argument.
   *
   * When all previously-submitted work has been evaluated, this function continues to wait for new
   * work (without blocking the thread) so future work can be processed. To end execution, the
   * caller must terminate the execution context (e.g. cancel the coroutine, kill the process, etc.)
   * or call [close]. Multiple concurrent invocations of `execute` are permitted and will not fail,
   * but only the first will actively evaluate blocks, and all subsequent calls will block until the
   * active execution is explicitly terminated (via the aforementioned execution context
   * termination) or [close] is called. If the active execution is cancelled, the next waiting
   * execution call will safely take over loop processing.
   *
   * Closure details:
   * - If invoked after [close], returns immediately without throwing an exception.
   * - If [close] is called after this function is invoked, all remaining blocks in the queue are
   *   discarded and this function returns normally.
   * - If [close] is called while a [block] is being evaluated, the [block] will be completed before
   *   this function returns and closure finishes.
   */
  suspend fun execute(resource: T)
}

/**
 * Queues executable work in one execution context and executes it in another.
 *
 * Quinn solves two problems that commonly occur in systems with single-threaded event loops:
 * 1. Resource Confinement: The resource of the main thread often cannot be used from other threads.
 * 2. Automatic Termination: The main thread event loop cannot exit without terminating the process.
 *
 * Quinn allows other threads to define lambdas that take in the resource constrained to the main
 * thread, queues them, and encapsulates all the execution mechanics, so the single-threaded context
 * can simply supply the resource and block until quinn finishes executing scheduled work. It works
 * as follows:
 * 1. The main thread calls [ExecutableQuinn.execute]. The function accepts a resource to use and
 *    blocks indefinitely while it wait for and executes work, although it never actually blocks the
 *    thread so the underlying main thread does not report non-responsive.
 * 2. Other threads call [SubmittableQuinn.run]. The function accepts a lambda that takes in the
 *    resource from the main thread and works on it. It suspends until work completes so that it
 *    appears to be a regular function call to the user, but the work is actually passed to the main
 *    thread for execution.
 *
 * Quinn encapsulates all the complex multi-threading logic of this system so the submission-side
 * users can just pass in work and the execution-side users can just pass in the resources to use
 * (and implicitly, the thread, by virtue of calling the function).
 *
 * Quinn is closable and has well-defined closure mechanics:
 * 1. Submission Rejection: Following closure, `run` throws an `IllegalStateException` while
 *    `tryRun` safely returns `false`.
 * 2. Graceful Completion: Any block being actively evaluated when closure occurs is finished
 *    gracefully before closure completes. The execution thread is never abruptly aborted.
 * 3. Queue Eviction: Pending blocks that were queued but have not yet been evaluated are evicted
 *    without evaluation. Coroutines suspended on `run` for those evicted blocks resume completely
 *    normally.
 * 4. Execution Termination: Indefinite `execute` loops return gracefully without exception. Future
 *    calls to `execute` return immediately without throwing an exception.
 *
 * In summary, closure finishes up the present computation, discards the unprocessed ones, sets
 * `run` to raise an error, and sets `execute` to return immediately without raising an error.
 *
 * To ensure type abstraction and limit architectural exposure, components should typically depend
 * specifically on either [SubmittableQuinn] or [ExecutableQuinn].
 */
interface Quinn<T> : SubmittableQuinn<T>, ExecutableQuinn<T> {
  /** Creates instances of [Quinn]. */
  interface Factory {
    /** Creates a new instance of [Quinn]. */
    fun <T> createQuinn(): Quinn<T>
  }
}
