
package com.jackbradshaw.concurrency.quinn

import com.jackbradshaw.closet.observable.ObservableClosable
import kotlinx.coroutines.flow.StateFlow


A generic, reusable actor-object concurrency primitive.

Quinn is a variatnion of the actor-object design pattern. It accepts tasks to run from one context
and executes them in another; however, it deviates from the conventional actor-object pattern, where
the tasks are zero-argument runnables that execute in the context of the executor as it works as a generic and
reusable task queue that operates in isolation. It accepts tasks from one context as single-argument
executables and requires the executor to supply the argument to pass into the tasl. It encapsulates
both the queueing, dequeueing, and execution of tasks, so that the submission side simply has to
pass in tasks, and the execution side simply has to pass in the value to supply. When the value is
passed in, Quinn blocks the call to effectively hijack the calling thread for its execution.

This is useful in situations where a thread-confined resource needs to be used in other threads
because it effectively decouples task definition from task execution. Consider a main-threaded
resource, perhaps a UI element, that cannot be mutated by other threads. Quinn allows any thread
to pass in tasks that operate on the object (via [queueAtFront], [queueAtBack], etc), and the main
thread simply has to call [execute] and pass in the object. 

For exmaple, on the submission side:

`quinn.queueAtFront { uiContext -> uiContext.setTextSize(10f) }`

With an associated execution side:

`quinn.execute(uiContext)`

Quinn will iterate through the submitted tasks on the main thread (passing each the ui context
so they can use it), thereby effectively running the tasks on the ui thread without ever moving the
object off the main thread.

Quinn implements strict suspension and closable contracts for coordination. When tasks are submitted
the submitting call suspends until the task is complete or the quinn instance is closed. When the
quinn instance is closed, it completes the present task (or the task that is about to run, if it has
already been dequeued), before discarding all existing tasks and exiting execute. After closure,
no new tasks can be submitted, and attempting to do so throws an exception.

Exceptions thrown by tasks are handled in one of two places: On the submission side, or on the
execution side, with the deciding factor being the `errorBehaviour` argument on the submission
function. Handling on the submission side causes any exceptions thrown by the task to be caught and
rethrown by the submission function, and handling on th eexeuction side causes any exceptions thrown by the
task to be caught and rethrown by the execute function. In both cases quinn remains operational and
can continue processing tasks (immediately moving on to the next in the queue without delay);
however, whether the process at large can record from such an exception depends on the nature of the
error, and whether it created an unrecoverable state. In any case, that is a detail of the broader
program architecture, and cannot be predicted or accounted for by quinn.




todo tomorrow: only a few missing details to incorporate. check gemini for details.












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


