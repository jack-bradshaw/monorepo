
package com.jackbradshaw.concurrency.quinn

import com.jackbradshaw.closet.observable.ObservableClosable
import kotlinx.coroutines.flow.StateFlow




/**
 * An active-object concurrency primitive that provides thread-safe access to a thread-confined
 * resource. It acts as a multi-producer, single-consumer task queue, decoupling the submission of
 * executable work from its execution context.
 *
 * Quinn works as follows:
 * 1. Submission Side: Threads call functions like [queueAtBack] to submit executable blocks that
 *    require access to the thread-confined resource. The submission function suspends until the
 *    block completes, so it appears to the submitter as a regular, synchronous function call, even
 *    though the block is actually passed to the execution context.
 * 2. Execution Side: The thread that owns the resource calls [execute], providing the resource as
 *    an argument. The function then acts as an event loop, waiting for and sequentially evaluating
 *    the submitted blocks against the provided resource.
 *
 * Quinn encapsulates all the complex multi-threading logic of this system. Submitters simply pass
 * in work, and the executor simply passes in the resource (and implicitly, the thread, by virtue of
 * calling the function).
 *
 * Quinn is closable and has well-defined closure mechanics:
 * 1. Submission Rejection: Following closure, [queueAtBack] and [queueAtFront] throws an
 *    [IllegalStateException]. The closure-safe variants ([tryQueueAtBack] and [tryQueueAtFront])
 *    return [InsertionResult.REJECTED_CLOSED] instead of throwing an exception.
 * 2. Graceful Completion: Any block being actively evaluated when closure occurs is finished
 *    gracefully before closure completes. The execution thread is never abruptly aborted.
 * 3. Queue Eviction: Pending blocks that were queued but have not yet been evaluated are evicted
 *    without evaluation. Coroutines suspended on [queueAtBack] for those evicted blocks resume
 *    completely normally.

 * 4. Execution Termination: Indefinite `execute` loops return gracefully without exception. Future
 *    calls to `execute` return immediately without throwing an exception.
 *
 * In summary, closure finishes up the present computation, discards the unprocessed ones, sets

 * [queueAtBack] to raise an error, and sets [execute] to return immediately without raising an
 * error.
 *
 * Thread safety details:
 * - Insertion is thread safe; however, concurrent submission can result in non-deterministic
 *   execution ordering, so external synchronisation is necessary if strict ordering is required.
 * - WARNING: It is unsafe to make calls to this [Quinn] from [block] as implementations are free to
 *   use non-reentrant locks, and they likely will due to the multithreaded nature of Quinn.
 *
 * Error behaviour details:
 * - If [block] throws an exception, the `errorBehaviour` value determines where the exception is
 *   routed.
 * - When [ErrorBehaviour.DELIVER_TO_EXECUTION_SIDE] is specified, the exception is raised by
 *   [execute] as if it occured during [execute] directly.
 * - When [ErrorBehaviour.DELIVER_TO_SUBMISSION_SIDE] is specified, the exception is raised by the
 *   submission function as if it occured within it directly.
 * - [Quinn] itself remains operational in both cases, meaning existing scheduled work is not lost
 *   and more work can be scheduled; however, there is no guarantee the error did not create an
 *   irrecoverable state in the border context of the application, so whether errors events are
 *   recoverable overall is dependent on the nature of the error and the context in which this
 *   [Quinn] is executing.
 */
interface Quinn<T> : ObservableClosable {

  /** Whether execution is currently in progress. */
  val isExecuting: StateFlow<Boolean>

  /**
   * Schedules [block] for execution and suspends until it has been run or this Quinn has closed.
   *
   * Work is scheduled for execution at the back of the queue (i.e. after all presently scheduled
   * work).
   *
   * WARNING: Using the supplied resource (T) outside of [block] is unsupported and not recommended,
   * as the entire purpose of Quinn is accessing thread-bound resources safely. Using the resource
   * outside [block] is likely to cause errors.
   */
  suspend fun queueAtBack(
      errorBehaviour: ErrorBehaviour = ErrorBehaviour.DELIVER_TO_SUBMISSION_SIDE,
      block: (T) -> Unit
  )

  /**
   * Schedules [block] for execution and suspends until it has been run or this Quinn has closed.
   *
   * Work is scheduled for execution at the back of the queue (i.e. after all presently scheduled
   * work).
   *
   * Returns [InsertionResult.REJECTED_CLOSED] if called after closure.
   *
   * WARNING: Using the supplied resource (T) outside of [block] is unsupported and not recommended,
   * as the entire purpose of Quinn is accessing thread-bound resources safely. Using the resource
   * outside [block] is likely to cause errors.
   */
  suspend fun tryQueueAtBack(
      errorBehaviour: ErrorBehaviour = ErrorBehaviour.DELIVER_TO_SUBMISSION_SIDE,
      block: (T) -> Unit
  ): InsertionResult

  /**
   * Identical to [queueAtBack], except it inserts [block] at the front of the queue instead of the
   * back.
   */
  suspend fun queueAtFront(
      errorBehaviour: ErrorBehaviour = ErrorBehaviour.DELIVER_TO_SUBMISSION_SIDE,
      block: (T) -> Unit
  )

  /**
   * Identical to [tryQueueAtBack], except it inserts [block] at the front of the queue instead of
   * the back.
   */
  suspend fun tryQueueAtFront(
      errorBehaviour: ErrorBehaviour = ErrorBehaviour.DELIVER_TO_SUBMISSION_SIDE,
      block: (T) -> Unit
  ): InsertionResult

  /**
   * Executes the work queued in this [Quinn] instance against the supplied [resource].
   *
   * When all previously-submitted work has been evaluated, this function continues to wait for new
   * work so future work can be processed. Execution blocks the thread, so to end execution, the
   * caller must terminate the execution context (e.g. cancel the coroutine/thread, kill the
   * process, etc.) or call [close]. Closure allows execution to terminate gracefully.
   *
   * Multiple concurrent invocations of `execute` are permitted and will not fail, but only the
   * first will actively evaluate blocks, and all subsequent calls will block until the active
   * execution is explicitly interrupted. If the active execution is cancelled without closure, the
   * next waiting call to [execute] will begin processing.
   */
  suspend fun execute(resource: T)

  /** The result of submitting a block to [Quinn]. */
  enum class InsertionResult {
    /** The block was not queued because the [Quinn] was closed at the time of submission. */
    REJECTED_CLOSED,

    /**
     * The block was queued but not run because the [Quinn] was closed between submission and
     * processing.
     */
    INSERTED_NOT_RUN,

    /** The block was queued and run without exception. */
    INSERTED_AND_RUN
  }

  /** The error behaviour to use when a block throws an exception during execution. */
  enum class ErrorBehaviour {
    /**
     * The error is thrown submission side by the submitting function (i.e. [queueAtBack],
     * [queueAtFront], [tryQueueAtBack], or [tryQueueAtFront]).
     */
    DELIVER_TO_SUBMISSION_SIDE,

    /** The error is thrown execution side by the [execute] function. */
    DELIVER_TO_EXECUTION_SIDE
  }


  /** Creates instances of [Quinn]. */
  interface Factory {
    /** Creates a new instance of [Quinn]. */
    fun <T> createQuinn(): Quinn<T>
  }
}


