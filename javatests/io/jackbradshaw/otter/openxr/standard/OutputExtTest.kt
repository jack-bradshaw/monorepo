package io.jackbradshaw.otter.openxr.standard

import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.otter.openxr.model.Output
import io.jackbradshaw.otter.openxr.model.OutputIdentifier
import io.jackbradshaw.otter.openxr.model.User
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OutputExtTest {
  @Test
  fun output_noLocation_createsProto() {
    val user = StandardUser.HEAD
    val identifier = StandardOutputIdentifier.HAPTIC

    val output = output(user, identifier)

    assertThat(output)
        .isEqualTo(
            Output.newBuilder()
                .setUser(StandardUser.HEAD.user)
                .setIdentifier(StandardOutputIdentifier.HAPTIC.identifier)
                .build())
  }

  @Test
  fun output_withLocation_createsProto() {
    val user = StandardUser.HEAD
    val identifier = StandardOutputIdentifier.HAPTIC
    val location = StandardOutputLocation.LEFT

    val output = output(user, identifier, location)

    assertThat(output)
        .isEqualTo(
            Output.newBuilder()
                .setUser(StandardUser.HEAD.user)
                .setIdentifier(StandardOutputIdentifier.HAPTIC.identifier)
                .setLocation(StandardOutputLocation.LEFT.location)
                .build())
  }
}
