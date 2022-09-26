package io.jackbradshaw.otter.openxr.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class InputExtTest {
  @Test
  fun input_noLocation_passesValuesThroughIntoProto() {
    val user = user("userId")
    val identifier = inputIdentifier("identifierId")
    val component = inputComponent("componentId")

    val input = input(user, identifier, component)

    assertThat(input).isEqualTo(Input.newBuilder().setUser(user).setIdentifier(identifier).setComponent(component).build())
  }

  @Test
  fun input_withLocation_passesValuesThroughIntoProto() {
    val user = user("userId")
    val identifier = inputIdentifier("identifierId")
    val component = inputComponent("componentId")
    val location = inputLocation("locationId")

    val input = input(user, identifier, component, location)

    assertThat(input).isEqualTo(Input.newBuilder().setUser(user).setIdentifier(identifier).setComponent(component).setLocation(location).build())
  }
}