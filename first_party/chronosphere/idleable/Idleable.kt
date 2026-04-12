package com.jackbradshaw.chronosphere.idleable

/**
 * A functional interface for any system that can report whether it is currently idle.
 *
 * Idle is defined as the state where the state will not change unless acted upon by an external
 * system, even given infinite time. The infinite time constraint means that even systems which are
 * waiting for a duration of time have not reached idle, which commonly occurs in execution systems
 * (such as coroutines), where computations can pause for a fixed duration then resume. This
 * definition of idle requires all pending work to be either cancelled or completed before idle can
 * be reached.
 *
 * The system may transition from an idle state back to an active state independent of calls to
 * [isIdle], and implementing this interface provides no guarantee the system will remain in the
 * state reported by [isIdle] after it returns. This is a practical concern in multithreaded systems
 * because one thread may awaken resources that were previously idle on another thread.
 */
fun interface Idleable {

  /** Returns whether this system is presently idle. */
  fun isIdle(): Boolean
}
