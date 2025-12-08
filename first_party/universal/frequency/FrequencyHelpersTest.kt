package com.jackbradshaw.universal.frequency

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.universal.frequency.FrequencyKt.bounded
import kotlin.test.assertFailsWith
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FrequencyHelpersTest {

  @Test
  fun toHertz_fromHz_passesThrough() {
    val original = bounded { hertz = HERTZ }

    val converted = original.toHertz()

    assertThat(converted).isWithin(FLOAT_ACCURACY).of(HERTZ)
  }

  @Test
  fun toHertz_fromRadiansPerSecond_converts() {
    val original = bounded { radiansPerSecond = RADIANS_PER_SECOND }

    val converted = original.toHertz()

    assertThat(converted).isWithin(FLOAT_ACCURACY).of(HERTZ)
  }

  @Test
  fun toHertz_missingValue_throwsError() {
    val original = bounded {}

    assertFailsWith<IllegalStateException>("value field not set: $original") { original.toHertz() }
  }

  @Test
  fun usingHertz_fromHz_passesThrough() {
    val original = bounded { hertz = HERTZ }

    val converted = original.usingHertz()

    assertThat(converted).isEqualTo(original)
  }

  @Test
  fun usingHertz_fromRadiansPerSecond_converts() {
    val original = bounded { radiansPerSecond = RADIANS_PER_SECOND }

    val converted = original.usingHertz()

    assertThat(converted.hertz).isWithin(FLOAT_ACCURACY).of(HERTZ)
  }

  @Test
  fun usingHertz_missingValue_throwsError() {
    val original = bounded {}

    assertFailsWith<IllegalStateException>("value field not set: $original") {
      original.usingHertz()
    }
  }

  @Test
  fun toRadiansPerSecond_fromHz_converts() {
    val original = bounded { hertz = HERTZ }

    val converted = original.toRadiansPerSecond()

    assertThat(converted).isWithin(FLOAT_ACCURACY).of(RADIANS_PER_SECOND)
  }

  @Test
  fun toRadiansPerSecond_fromRadiansPerSecond_passesThrough() {
    val original = bounded { radiansPerSecond = RADIANS_PER_SECOND }

    val converted = original.toRadiansPerSecond()

    assertThat(converted).isWithin(FLOAT_ACCURACY).of(RADIANS_PER_SECOND)
  }

  @Test
  fun toRadiansPerSecond_missingValue_throwsError() {
    val original = bounded {}

    assertFailsWith<IllegalStateException>("value field not set: $original") {
      original.toRadiansPerSecond()
    }
  }

  @Test
  fun usingRadiansPerSecond_fromHz_converts() {
    val original = bounded { hertz = HERTZ }

    val converted = original.usingRadiansPerSecond()

    assertThat(converted.radiansPerSecond).isWithin(FLOAT_ACCURACY).of(RADIANS_PER_SECOND)
  }

  @Test
  fun usingRadiansPerSecond_fromRadiansPerSecond_passesThrough() {
    val original = bounded { radiansPerSecond = RADIANS_PER_SECOND }

    val converted = original.usingRadiansPerSecond()

    assertThat(converted).isEqualTo(original)
  }

  @Test
  fun usingRadiansPerSecond_missingValue_throwsError() {
    val original = bounded {}

    assertFailsWith<IllegalStateException>("value field not set: $original") {
      original.usingRadiansPerSecond()
    }
  }

  companion object {
    /** An arbitrary frequency value measured in hertz. */
    private const val HERTZ: Double = 10.0

    /**
     * The [HERTZ] value converted to radians per second using the formula `w (rad/s) = 2pi * f
     * (hz)`, rounded to 3 decimal places.
     */
    private const val RADIANS_PER_SECOND = 62.831

    /**
     * The FLOAT_ACCURACY to use in floating point comparisons. Chosen ot match the 3 decimal place
     * rounding of [RADIANS_PER_SECOND].
     */
    private const val FLOAT_ACCURACY = 0.001
  }
}
