package com.jackbradshaw.closet.suspending

import java.lang.AutoCloseable

/**
 * An equivalent to [AutoCloseable] where the [close] function is a suspending function instead of a
 * synchronous function.
 *
 * Closure is monotonic but not atomic. When [close] is called, the closable begins moving to the
 * closed state from the open state. This process may take time, and while it occurs the closable is
 * in an intermediate closing state. Closure can only progress from open to closing to closed,
 * without deviation or regression. If the coroutine running [close] is cancelled before completion,
 * the closable remains in the closing state until another call to [close] continues closure.
 *
 * Implementations are free to implement [close] in any way that ensures the above conditions are
 * met. When recovering from a previously cancelled closure, options include (but are not limited
 * to) restarting the processing from the beginning, continuing the process from the cancellation
 * point, or implementing a separate path. In any case, only one call to [close] can be operating at
 * any given time, and concurrent calls must suspend in a queue until their turn.
 *
 * Beyond the above contract, what constitutes the closed state is not strictly defined, and
 * implementations may use their own definition; however, generally a system is closed when it has
 * ceased all internally driven work (e.g., coroutines, futures, loopers) and will either reject or
 * ignore externally driven interactions (e.g., function calls, property setters).
 */
fun interface SuspendableClosable {

  /**
   * Closes this system.
   *
   * This function must be idempotent and safe to call concurrently. The first concurrent call takes
   * precedence and executes the internal closure logic. Any subsequent calls must suspend and wait
   * their turn in a queue. When the active call completes or is cancelled, the next in the queue
   * must resume, even if a closed state hasbeen reached.
   */
  suspend fun close()
}
