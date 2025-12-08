package com.jackbradshaw.universal.frequency

import com.jackbradshaw.universal.frequency.Frequency.Bounded
import com.jackbradshaw.universal.frequency.Frequency.Bounded.ValueCase.HERTZ
import com.jackbradshaw.universal.frequency.Frequency.Bounded.ValueCase.RADIANS_PER_SECOND
import com.jackbradshaw.universal.frequency.Frequency.Bounded.ValueCase.VALUE_NOT_SET

/** Gets this bounded frequency in units of Hz. Fails if the value oneof has no value. */
fun Frequency.Bounded.toHertz(): Double =
    when (this.valueCase) {
      HERTZ -> this.hertz
      RADIANS_PER_SECOND -> this.radiansPerSecond / (2 * Math.PI)
      VALUE_NOT_SET -> throw IllegalStateException("value field not set: $this")
    }

/**
 * Returns a bounded frequency with the same value as this bounded frequency, using Hz for the
 * encoding. Fails if the value oneof has no value.
 */
fun Frequency.Bounded.usingHertz(): Frequency.Bounded =
    FrequencyKt.bounded { hertz = this@usingHertz.toHertz() }

/** Converts this bounded frequency to radians per second. Fails if the value oneof has no value. */
fun Frequency.Bounded.toRadiansPerSecond(): Double =
    when (this.valueCase) {
      HERTZ -> this.hertz * 2 * Math.PI
      RADIANS_PER_SECOND -> this.radiansPerSecond
      VALUE_NOT_SET -> throw IllegalStateException("value field not set: $this")
    }

/**
 * Returns a bounded frequency with the same value as this bounded frequency, using radians per
 * second for the encoding. Fails if the value oneof has no value.
 */
fun Frequency.Bounded.usingRadiansPerSecond(): Frequency.Bounded =
    FrequencyKt.bounded { radiansPerSecond = this@usingRadiansPerSecond.toRadiansPerSecond() }
