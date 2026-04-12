package com.jackbradshaw.coroutines.testing.realistic.dispatcher

import com.jackbradshaw.chronosphere.idleable.Idleable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * A coroutine dispatcher that can report its idle status.
 *
 * Extends [Delay] to ensure coroutine delays and timeouts are considered in idle-checking.
 */
@OptIn(InternalCoroutinesApi::class)
abstract class IdleableDispatcher : CoroutineDispatcher(), Idleable, Delay
