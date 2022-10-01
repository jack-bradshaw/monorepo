package io.jackbradshaw.otter.openxr.standard


import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StandardInputLocationTest {
  @Test
  fun toInputLocationAndBack_forEachStandardInputLocation_returnsOriginalValue() {
    for (standardLocation in StandardInputLocation.values()) {
      val location = standardLocation.location
      val reverse = StandardInputLocation.fromInputLocation(location)
      assertThat(reverse).isEqualTo(standardLocation)
    }
  }
}