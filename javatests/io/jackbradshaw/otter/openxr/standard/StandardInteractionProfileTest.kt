package io.jackbradshaw.otter.openxr.standard

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StandardInteractionProfileTest {
  @Test
  fun toInteractionProfileAndBack_forEachStandardInteractionProfile_returnsOriginalValue() {
    for (standardInteractionProfile in StandardInteractionProfile.values()) {
      val profile = standardInteractionProfile.profile
      val reverse = StandardInteractionProfile.fromInteractionProfile(profile)
      assertThat(reverse).isEqualTo(standardInteractionProfile)
    }
  }
}