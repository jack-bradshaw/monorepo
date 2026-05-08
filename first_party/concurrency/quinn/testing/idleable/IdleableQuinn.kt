package com.jackbradshaw.concurrency.quinn.testing.idleable

import com.jackbradshaw.chronosphere.idleable.Idleable
import com.jackbradshaw.concurrency.quinn.Quinn

/**
 * A [Quinn] that can report its idle-state.
 *
 * Idle occurs when all submitted tasks have been executed or removed from the queue (i.e. on
 * closure). Since the [Quinn] contract expects calls to [executor] to suspend when there are no
 * tasks to complete, idle state is defined purely in terms of the queue size, and an active call to
 * [execute] does not preclude idle state being reached.
 */
interface IdleableQuinn<T> : Quinn<T>, Idleable
