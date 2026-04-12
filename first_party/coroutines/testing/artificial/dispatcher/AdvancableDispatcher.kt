package com.jackbradshaw.coroutines.testing.artificial.dispatcher

import com.jackbradshaw.chronosphere.advancable.Advancable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * A coroutine dispatcher that can be advanced in tests.
 *
 * Extends [Delay] to ensure coroutine delays and timeouts advance as expected.
 */
@OptIn(InternalCoroutinesApi::class)
abstract class AdvancableDispatcher : CoroutineDispatcher(), Advancable, Delay
