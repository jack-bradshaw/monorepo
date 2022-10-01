package io.jackbradshaw.otter.openxr.standard

import io.jackbradshaw.otter.openxr.model.Output
import io.jackbradshaw.otter.openxr.model.User
import io.jackbradshaw.otter.openxr.model.OutputIdentifier
import io.jackbradshaw.otter.openxr.model.OutputLocation
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class OutputExtTest {
  @Test
  fun output_noLocation_createsProto() {
    val user = StandardUser.HEAD
    val identifier = StandardOutputIdentifier.HAPTIC

    val output = output(user, identifier)

    assertThat(output).isEqualTo(Output.newBuilder().setUser(User.newBuilder().setId(user.user.id).build()).setIdentifier(OutputIdentifier.newBuilder().setId(identifier.identifier.id).build()).build())
  }

  @Test
  fun output_withLocation_createsProto() {
    val user = StandardUser.HEAD
    val identifier = StandardOutputIdentifier.HAPTIC
    val location = StandardOutputLocation.LEFT

    val output = output(user, identifier, location)

    assertThat(output).isEqualTo(Output.newBuilder().setUser(User.newBuilder().setId(user.user.id).build()).setIdentifier(OutputIdentifier.newBuilder().setId(identifier.identifier.id).build()).setLocation(OutputLocation.newBuilder().setId(location.location.id).build()).build())
  }
}