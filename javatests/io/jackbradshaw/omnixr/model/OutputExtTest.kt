package io.jackbradshaw.omnixr.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class OutputExtTest {
  @Test
  fun output_noLocation_createsProto() {
    val user = user("userId")
    val identifier = outputIdentifier("identifierId")

    val output = output(user, identifier)

    assertThat(output).isEqualTo(Output.newBuilder().setUser(user).setIdentifier(identifier).build())
  }

  @Test
  fun output_withLocation_createsProto() {
    val user = user("userId")
    val identifier = outputIdentifier("identifierId")
    val location = outputLocation("locationId")

    val output = output(user, identifier, location)

    assertThat(output).isEqualTo(Output.newBuilder().setUser(user).setIdentifier(identifier).setLocation(location).build())
  }
}