package io.jackbradshaw.omnixr.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class InteractionProfileExtTest {
  @Test
  fun interactionProfile_noInputsOrOutputs_passesValuesThroughIntoProto() {
    val vendorId = "vendorId"
    val controllerId = "controllerId"

    val interactionProfile = interactionProfile(vendorId, controllerId)

    assertThat(interactionProfile).isEqualTo(
        InteractionProfile.newBuilder().setVendor(Vendor.newBuilder().setId(vendorId)
        .build()).setController(Controller.newBuilder().setId(controllerId).build()).build()
    )
  }

  @Test
  fun interactionProfile_withInputsAndOutputs_passesValuesThroughIntoProto() {
    val vendorId = "vendorId"
    val controllerId = "controllerId"
    val inputs = setOf(input(user("userId"), inputIdentifier("inputIdentiferId"), inputComponent("componentId")))
    val outputs = setOf(output(user("userId"), outputIdentifier("outputIdentiferId")))

    val interactionProfile = interactionProfile(vendorId, controllerId,inputs, outputs)

    assertThat(interactionProfile).isEqualTo(
        InteractionProfile.newBuilder().setVendor(Vendor.newBuilder().setId(vendorId)
            .build()).setController(Controller.newBuilder().setId(controllerId).build())
            .addAllInput(inputs).addAllOutput(outputs).build()
    )
  }
}