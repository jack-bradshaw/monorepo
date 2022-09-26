package io.jackbradshaw.otter.openxr.standard


import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StandardOutputLocationTest {
  @Test
  fun toLocationAndBack_forEachStandardOutputLocation_returnsOriginalValue() {
    for (standardOutputLocation in StandardOutputLocation.values()) {
      val location = standardOutputLocation.location
      val reverse = StandardOutputLocation.fromOutputLocation(location)
      assertThat(reverse).isEqualTo(standardOutputLocation)
    }
  }
}