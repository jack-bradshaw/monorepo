package com.jackbradshaw.codestone.lifecycle.conversion.multiconverter

import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.conversion.uniconverter.UniConverter

interface MultiConverter<O> : UniConverter<Any, O>
