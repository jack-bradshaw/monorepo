package io.jackbradshaw.otter.openxr.standard

import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.model.InputComponent
import io.jackbradshaw.otter.openxr.model.InputIdentifier
import io.jackbradshaw.otter.openxr.model.InputLocation
import io.jackbradshaw.otter.openxr.model.User
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class InputExtTest {
  @Test
  fun input_noLocationSupplied_createsInputWithoutLocation() {
    val user = StandardUser.HEAD
    val identifier = StandardInputIdentifier.AIM
    val component = StandardInputComponent.TOUCH

    val input = input(user, identifier, component)

    assertThat(input)
        .isEqualTo(
            Input.newBuilder()
                .setUser(StandardUser.HEAD.user)
                .setIdentifier(StandardInputIdentifier.AIM.identifier)
                .setComponent(StandardInputComponent.TOUCH.component)
                .build())
  }

  @Test
  fun input_locationSupplied_createsInputWithLocation() {
    val user = StandardUser.HEAD
    val identifier = StandardInputIdentifier.AIM
    val component = StandardInputComponent.TOUCH
    val location = StandardInputLocation.LEFT

    val input = input(user, identifier, component, location)

    assertThat(input)
        .isEqualTo(
            Input.newBuilder()
                .setUser(User.newBuilder().setId(user.user.id).build())
                .setIdentifier(InputIdentifier.newBuilder().setId(identifier.identifier.id).build())
                .setComponent(InputComponent.newBuilder().setId(component.component.id).build())
                .setLocation(InputLocation.newBuilder().setId(location.location.id).build())
                .build())
  }
}
