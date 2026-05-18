package com.jackbradshaw.concurrency.quinn

import com.jackbradshaw.closet.observable.ObservableClosable
import kotlinx.coroutines.flow.StateFlow

/**
 * A generic, reusable implementation of the multi-producer single-consumer actor-object design
 * pattern.
 *
 * Quinn accepts tasks to run from one context and executes them in another. Tasks are modelled as
 * single-argument non-suspending computations that are supplied concurrently in one execution
 * context (i.e. the submission side) and executed sequentially in another (i.e. the execution
 * side). Unlike the conventional actor-object design pattern, which is an implementation detail of
 * an object, Quinn is a reusable component that fully encapsulates the entire process of queueing,
 * dequeueing, and executing tasks. The submission side can simply pass in tasks, and the execution
 * side can simply supply the value to pass to them. Quinn conflates the act of passing in the value
 * with execution itself, meaning when the execution side calls [execute], the function suspends
 * indefinitely while processes the task queuee. This ensures execution occurs in the context that
 * natively has access to the value.
 *
 * This single-arg model double-sided model is useful in situations where a thread-confined resource
 * needs to be used in other threads, for example, a main-threaded resource (such as a UI element)
 * that cannot be mutated by other threads. By sharing a Quinn between the UI thread and background
 * worker threads, the workers can submit tasks that operate on the object and the UI thread can
 * execute the tasks by calling [execute]. Simplistically:
 * ```
 * // On the worker thread, suspends until the task is run.
 * quinn.queueAtFront { uiContext -> uiContext.setTextSize(10f) }
 *
 * // On the UI thread, suspends indefinitely to run tasks as they are submitted
 * quinn.execute(uiContext)
 * ```
 *
 * In the above example, Quinn iterates through submitted tasks in order on the main thread, passing
 * each the ui context so each can use it, but never moving the UI context off the main thread. This
 * effectively runs each task on the UI thread without breaking thread confinement.
 *
 * Quinn follows a strict contract for coordinating submission, execution, and closure. When tasks
 * are submitted, the submitting call suspends until the task is complete, and when execute is
 * called, it suspends indefinitely. Multiple concurrent submissions are permitted and quinn is
 * strictly thread-safe, however queue ordering can be non-deterministic in submission race
 * conditions, so external synchronisation can be useful when determinstic ordering is required.
 * Only one execution call can have effect at any time, but all call suspend, and when one is
 * cancelled (by cancelling the host coroutine) the next takes effect (in order or arrival). When
 * Quinn is closed, it completes the present task if one is running, discards all queued tasks,
 * resumes all suspended submission calls, and resumes all suspended execute calls. No new tasks can
 * be submitted after closure, and attempting to do so throws an exception (unless the closure-safe
 * `try` variants are used); however, execution calls never fail after closure and instead return
 * immediately.
 *
 * Exceptions thrown within tasks are handled on the submission side or on the execution side, with
 * the deciding factor being the `errorHandling` argument passed to the submission function.
 * Submission side handling causes exceptions to be caught and rethrown by the submission function,
 * and execution side handling causes exceptions to be caught and rethrown by the execute function.
 * In both cases, Quinn remains operational and can continue processing tasks (immediately moving on
 * to the next in the queue without delay); however, whether the broader process can recover from
 * such an exception depends on the nature of the error, the effect on the process, and whether it
 * created an unrecoverable state. In any case, that is a detail of the broader program
 * architecture, and cannot be predicted or accounted for by Quinn.
 *
 * Quinn tasks are not suspending functions because Quinn operates structly as a processing queue
 * without cooperative multitasking. Tasks are exectued strictly according to the queue order, one
 * at a time, any blocking calls within a task block the task queue. This ensures Quinn is
 * deterministic and fulfills its core function (moving execution between contexts) without becoming
 * a generic coroutine dispatcher.
 */
interface Quinn<T> : ObservableClosable {

  /**
   * Whether a call to [execute] is presently active (i.e. suspended).
   *
   * Does not become false between execute calls if one call exits while another is already waiting.
   */
  val isExecuting: StateFlow<Boolean>

  /**
   * Schedules [task] for execution at the back of the queue (i.e. last) and suspends until it is
   * run or this [Quinn] is closed.
   *
   * Throws an [IllegalStateException] if this [Quinn] is already closed when invoked.
   *
   * WARNING: Using the supplied resource (T) outside of [task] is unsupported and will likely fail
   * if the resource is thread confined.
   *
   * WARNING: It is unsafe to make calls to this [Quinn] from [task] as implementations are free to
   * use non-reentrant locks (and they likely will due to the multithreaded nature of Quinn).
   */
  suspend fun queueAtBack(
      errorHandling: ErrorHandling = ErrorHandling.DELIVER_TO_SUBMISSION_SIDE,
      task: (T) -> Unit
  )

  /**
   * Schedules [task] for execution at the back of the queue (i.e. last), suspends until it is run
   * or this [Quinn] is closed, and returns a result indicating whether the task was run. If this
   * [Quinn] is already closed when invoked, then it does not suspend and
   * [InsertionResult.REJECTED_CLOSED] is returned immedidiately.
   *
   * WARNING: Using the supplied resource (T) outside of [task] is unsupported and will likely fail
   * if the resource is thread confined.
   *
   * WARNING: It is unsafe to make calls to this [Quinn] from [task] as implementations are free to
   * use non-reentrant locks (and they likely will due to the multithreaded nature of Quinn).
   */
  suspend fun tryQueueAtBack(
      errorHandling: ErrorHandling = ErrorHandling.DELIVER_TO_SUBMISSION_SIDE,
      task: (T) -> Unit
  ): InsertionResult

  /**
   * Schedules [task] for execution at the front of the queue (i.e. first) and suspends until it is
   * run or this [Quinn] is closed.
   *
   * Throws an [IllegalStateException] if this [Quinn] is already closed when invoked.
   *
   * WARNING: Using the supplied resource (T) outside of [task] is unsupported and will likely fail
   * if the resource is thread confined.
   *
   * WARNING: It is unsafe to make calls to this [Quinn] from [task] as implementations are free to
   * use non-reentrant locks (and they likely will due to the multithreaded nature of Quinn).
   */
  suspend fun queueAtFront(
      errorHandling: ErrorHandling = ErrorHandling.DELIVER_TO_SUBMISSION_SIDE,
      task: (T) -> Unit
  )

  /**
   * Schedules [task] for execution at the front of the queue (i.e. first), suspends until it is run
   * or this [Quinn] is closed, and returns a result indicating whether the task was run. If this
   * [Quinn] is already closed when invoked, then it does not suspend and
   * [InsertionResult.REJECTED_CLOSED] is returned immedidiately.
   *
   * WARNING: Using the supplied resource (T) outside of [task] is unsupported and will likely fail
   * if the resource is thread confined.
   *
   * WARNING: It is unsafe to make calls to this [Quinn] from [task] as implementations are free to
   * use non-reentrant locks (and they likely will due to the multithreaded nature of Quinn).
   */
  suspend fun tryQueueAtFront(
      errorHandling: ErrorHandling = ErrorHandling.DELIVER_TO_SUBMISSION_SIDE,
      task: (T) -> Unit
  ): InsertionResult

  /**
   * Executes the task queue in order with the supplied [resource] as the task argument.
   *
   * This function suspends indefinitely and continues to wait for new work once the task queue has
   * been deplated. Multiple concurrent invocations of `execute` are permitted without error, and
   * all will suspend, but only one can be active at any given time. Execution can be terminated by
   * closing the Quinn or cancelling the coroutine hosting execution. When one call to execute
   * exits, the next in line begins processing in order of arrival.
   */
  suspend fun execute(resource: T)

  /** The result of task submission. */
  enum class InsertionResult {
    /** The task was not queued because the [Quinn] was closed at the time of submission. */
    REJECTED_CLOSED,

    /**
     * The task was queued but not run because the [Quinn] was closed between submission and
     * processing.
     */
    INSERTED_NOT_RUN,

    /** The task was queued and ran without exception. */
    INSERTED_AND_RUN
  }

  /** How to handle the error when a task throws an exception during execution. */
  enum class ErrorHandling {
    /** Catch the error and rethrow it from the associated submission function. */
    DELIVER_TO_SUBMISSION_SIDE,

    /** Catch the error and rethrow it from the active [execute] call. */
    DELIVER_TO_EXECUTION_SIDE
  }

  /** Creates instances of [Quinn]. */
  interface Factory {
    /** Creates a new instance of [Quinn]. */
    suspend fun <T> createQuinn(): Quinn<T>
  }
}
