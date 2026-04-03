package com.jackbradshaw.chronosphere.idleable

/**
 * A functional interface for any system that can report whether it is currently idle.
 *
 * The system may transition between idle and active states independently of calls to [isIdle], and
 * implementing this interface provides no guarantee that the system will remain idle after [isIdle]
 * returns true. In pure computer science theory, an Idleable is guaranteed to remain idle if and
 * only if all processors capable of affecting state have reached idle state (e.g. all coroutine
 * dispatchers are idle, all executors have finished work and drained their queues, all
 * hardware-interrupt mechanisms are disabled, etc.), which is effectively impossible since the host
 * machine is always coupled to other systems (including the network and the power grid); however,
 * in practice it is sufficient to idle the executors/processors/dispatchers which directly affect
 * the system under test, and assume all others are irrelevant.
 */
interface Idleable {
  /**
   * Returns whether this system is presently idle.
   * 
   * The returned value is not guaranteed to be stable (view class KDoc for details).
   */
  fun isIdle(): Boolean
}
