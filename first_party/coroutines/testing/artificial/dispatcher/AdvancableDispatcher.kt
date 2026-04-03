package com.jackbradshaw.coroutines.testing.artificial.dispatcher

import com.jackbradshaw.chronosphere.advancable.Advancable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay
import kotlinx.coroutines.InternalCoroutinesApi

/** A coroutine dispatcher that can have its time manually advanced. */
@OptIn(InternalCoroutinesApi::class)
abstract class AdvancableDispatcher : CoroutineDispatcher(), Advancable, Delay
