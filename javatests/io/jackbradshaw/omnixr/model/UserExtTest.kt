package io.jackbradshaw.omnixr.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class UserExtTest {
  @Test
  fun user_passesValuesThroughIntoProto() {
    val id = "userId"

    val user = user(id)

    assertThat(user).isEqualTo(User.newBuilder().setId(id).build())
  }
}