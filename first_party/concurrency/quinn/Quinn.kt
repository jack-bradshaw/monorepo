
package com.jackbradshaw.concurrency.quinn

// note to future self, this branch has the old quinn, the new stuff is beign submitted separately
import com.jackbradshaw.closet.observable.ObservableClosable
import kotlinx.coroutines.flow.StateFlow








/**
 * A generic, reusable implementation of the multi-producer single-consumer actor-object design
 * pattern.
 * 
 * Quinn accepts tasks to run from one context and executes them in another. Tasks are modelled as
 * single-argument suspending tasks that are supplied concurrently in one execution context
 * (i.e. the submission side) and execute sequentially in another (i.e. the execution side). Unlike
 * the conventional actor-object design pattern, Quinn is a reusable component that fully encapsulates
 * the entire process of queueing, dequeueing, and executing tasks, such that the submission side can
 * simply pass in tasks, and the execution side simply can simply supply the value to use when running
 * tasks. Furthermore, Quinn conflates the act of passing in the value with execution itself, meaning
 * when the execution side calls [execute] and passes in a value, the function suspends indefinitely
 * and processes the task queue. This ensures execution occurs in the context that natively has access
 * to the value.
 * 
 * This single-arg task system is useful in situations where a thread-confined resource needs to be
 * used in other threads. Consider a main-threaded resource, perhaps a UI element, that
 * cannot be mutated by other threads. By constructing a Quinn and sharing it between the UI thread
 * and background worker threads, the workers can pass in tasks that operate on the object, and the
 * UI thread can execute them by calling [execute]. For example:
 * 
 * ```
 * // On the worker thread, suspends until the task is run.
 * quinn.queueAtFront { uiContext -> uiContext.setTextSize(10f) }
 * 
 * // On the UI thread, suspends indefinitely to run tasks as they are submitted
 * quinn.execute(uiContext)
 * ```
 * 
 * In the above example, Quinn iterates through submitted tasks in order on the main thread,
 * passing each the ui context so they can use it, but never moving the UI context off the main
 * thread. This effectively runs the tasks on the UI thread without breaking thread confinement.
 * 
 * Quinn follows a strict contract for coordinating submission, execution, and closure. When tasks are
 * submitted, the submitting call suspends until the task is complete, and when execute is called, it
 * suspends indefinitely. Multiple concurrent submissions and executions are permitted and quinn is
 * strictly thread-safe, however queue ordering can be non-deterministic in submission race conditions,
 * so external synchronisation can be useful, and only one execute call has effect (with concurrent
 * calls suspending). When Quinn is closed, it completes the present task if one is running, discards all
 * queued tasks, resumes all execute calls, and resumes all suspended submission calls. After closure,
 * no new tasks can be submitted, and attempting to do so throws an exception (unless the closure-safe
 * `try` variants are used); however, new execution calls do not fail and instead return immediately. 
 * 
 * Exceptions thrown within tasks are handled on the submission side or on the execution side, with the
 * deciding factor being the `errorHandling` argument passed in during task submission. Submission
 * side handling causes exceptions to be caught and rethrown by the submission function, and execution
 * side handling causes exceptions to be caught and rethrown by the execute function. In both cases,
 * Quinn remains operational and can continue processing tasks (immediately moving on to the
 * next in the queue without delay); however, whether the broader process can recover from such an
 * exception depends on the nature of the error, the effect on the process, and whether it created an
 * unrecoverable state. In any case, that is a detail of the broader program architecture, and cannot
 * be predicted or accounted for by Quinn.
 */
interface Quinn<T> : ObservableClosable {

  /** Whether execution is currently in progress. */
  val isExecuting: StateFlow<Boolean>

  /**
   * Schedules [task] for execution at the back of the queue (i.e. last) and suspends until it is run or this
   * [Quinn] is closed.
   * 
   * Throws an [IllegalStateException] if this [Quinn] is already closed when invoked.
   *
   * WARNING: Using the supplied resource (T) outside of [task] is unsupported and not recommended,
   * as the entire purpose of Quinn is accessing thread-bound resources safely. Using the resource
   * outside [task] is likely to cause errors.
   * 
   * WARNING: It is unsafe to make calls to this [Quinn] from [task] as implementations are free to
 *   use non-reentrant locks, and they likely will due to the multithreaded nature of Quinn.
   */
  suspend fun queueAtBack(
      errorHandling: ErrorHandling = ErrorHandling.DELIVER_TO_SUBMISSION_SIDE,
      task: (T) -> Unit
  )

  /**
   * Schedules [task] for execution at the back of the queue (i.e. last), suspends until it is
   * run or this [Quinn] is closed, and returns a result indicating whether the task was run. If
   * this [Quinn] is already closed when invoked, [InsertionResult.REJECTED_CLOSED] is returned.
   *
   * WARNING: Using the supplied resource (T) outside of [task] is unsupported and not recommended,
   * as the entire purpose of Quinn is accessing thread-bound resources safely. Using the resource
   * outside [task] is likely to cause errors.
   * 
   * WARNING: It is unsafe to make calls to this [Quinn] from [task] as implementations are free to
 *   use non-reentrant locks, and they likely will due to the multithreaded nature of Quinn.
   */
  suspend fun tryQueueAtBack(
      errorHandling: ErrorHandling = ErrorHandling.DELIVER_TO_SUBMISSION_SIDE,
      task: (T) -> Unit
  ): InsertionResult

   /**
   * Schedules [task] for execution at the front of the queue (i.e. first) and suspends until it is run or this
   * [Quinn] is closed.
   * 
   * Throws an [IllegalStateException] if this [Quinn] is already closed when invoked.
   *
   * WARNING: Using the supplied resource (T) outside of [task] is unsupported and not recommended,
   * as the entire purpose of Quinn is accessing thread-bound resources safely. Using the resource
   * outside [task] is likely to cause errors.
   * 
   * WARNING: It is unsafe to make calls to this [Quinn] from [task] as implementations are free to
 *   use non-reentrant locks, and they likely will due to the multithreaded nature of Quinn.
   */
  suspend fun queueAtFront(
      errorHandling: ErrorHandling = ErrorHandling.DELIVER_TO_SUBMISSION_SIDE,
      task: (T) -> Unit
  )

  /**
   * Schedules [task] for execution at the front of the queue (i.e. first), suspends until it is
   * run or this [Quinn] is closed, and returns a result indicating whether the task was run. If
   * this [Quinn] is already closed when invoked, [InsertionResult.REJECTED_CLOSED] is returned.
   *
   * WARNING: Using the supplied resource (T) outside of [task] is unsupported and not recommended,
   * as the entire purpose of Quinn is accessing thread-bound resources safely. Using the resource
   * outside [task] is likely to cause errors.
   * 
   * WARNING: It is unsafe to make calls to this [Quinn] from [task] as implementations are free to
 *   use non-reentrant locks, and they likely will due to the multithreaded nature of Quinn.
   */
  suspend fun tryQueueAtFront(
      errorHandling: ErrorHandling = ErrorHandling.DELIVER_TO_SUBMISSION_SIDE,
      task: (T) -> Unit
  ): InsertionResult

  /**
   * Executes task in the queue with the supplied [resource] used as the task argument.
   *
   * This function suspends indefinitely and continues to wait for new work when the task queue has
   * been emptied. To end execution, the calling coroutine must be cancelled or this Quinn must be
   * closed. Closure allows execution to terminate gracefully and the presently processing task
   * will be completed before tear down.
   *
   * Multiple concurrent invocations of `execute` are permitted and will not fail, but only the
   * first will actively evaluate tasks, and all subsequent calls will suspend. If the active
   * call is cancelled without closure (i.e. coroutine cancellation) then the next waiting call
   * will begin processing (in order of arrival).
   */
  suspend fun execute(resource: T)

  /** The result of submitting a task to [Quinn] to one of the closure-safe `try` functions. */
  enum class InsertionResult {
    /** The task was not queued because the [Quinn] was closed at the time of submission. */
    REJECTED_CLOSED,

    /**
     * The task was queued but not run because the [Quinn] was closed between submission and
     * processing.
     */
    INSERTED_NOT_RUN,

    /** The block was queued and run without exception. */
    INSERTED_AND_RUN
  }

  /** The behaviour to use when a task throws an exception during execution. */
  enum class ErrorHandling {
    /**
     * Catch the error and rethrow it from the suspended submission function that submitted it.
     */
    DELIVER_TO_SUBMISSION_SIDE,

    /**
     * Catch the error and rethrow it from the active [execute] call.
     */
    DELIVER_TO_EXECUTION_SIDE
  }


  /** Creates instances of [Quinn]. */
  interface Factory {
    /** Creates a new instance of [Quinn]. */
    suspend fun <T> createQuinn(): Quinn<T>
  }
}


