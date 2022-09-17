package io.jackbradshaw.clearxr.standard

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StandardUserTest {
  @Test
  fun toUserAndBack_forEachStandardUser_returnsOriginalValue() {
    for (standardUser in StandardUser.values()) {
      val user = standardUser.user
      val reverse = StandardUser.fromUser(user)
      assertThat(reverse).isEqualTo(standardUser)
    }
  }
}
