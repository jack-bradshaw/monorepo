package io.jackbradshaw.omnixr.manifest.encoder

import io.jackbradshaw.omnixr.omniXr
import io.jackbradshaw.omnixr.model.InteractionProfile
import io.jackbradshaw.omnixr.model.Input
import io.jackbradshaw.omnixr.model.input
import io.jackbradshaw.omnixr.model.Output
import io.jackbradshaw.omnixr.model.output
import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.omnixr.standard.StandardInputComponent
import io.jackbradshaw.omnixr.standard.StandardInputIdentifier
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile
import io.jackbradshaw.omnixr.standard.StandardOutputIdentifier
import io.jackbradshaw.omnixr.standard.StandardUser
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import kotlin.test.fail
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ManifestEncoderTest {

  private lateinit var encoder: ManifestEncoder

  @Before
  fun setUp() {
    encoder = omniXr().manifestEncoder()
  }

  @Test
  fun encodeInput_eachStandardProfileInputCombination_generatesUniqueEncodingForEach() {
    val encodingToEncoded = mutableMapOf<String, MutableList<Pair<InteractionProfile, Input>>>()

    for (profile in StandardInteractionProfile.values()) {
      for (input in profile.interactionProfile.inputList) {
        val encoding = encoder.encodeInput(profile.interactionProfile, input)!!
        encodingToEncoded[encoding] = encodingToEncoded.getOrPut(encoding) { mutableListOf() }.also {
          it.add(Pair(profile.interactionProfile, input))
        }
      }
    }

    for ((encoding, encoded) in encodingToEncoded) {
      if (encoded.size > 1) {
        fail("Expected unique encoding for all profile/input pairs, but found duplicates for $encoding ($encoded).")
      }
    }
  }

  @Test
  fun encodeInput_nonStandardProfileInputCombination_returnsNull() {
    val encoding = encoder.encodeInput(
        StandardInteractionProfile.GOOGLE_DAYDREAM_CONTROLLER.interactionProfile,
        input(StandardUser.TREADMILL.user, StandardInputIdentifier.THUMBSTICK.identifier, StandardInputComponent.CLICK.component)
    )

    assertThat(encoding).isNull()
  }

  @Test
  fun decodeInput_eachStandardProfileInputCombination_restoresOriginalInputForEach() {
    for (profile in StandardInteractionProfile.values()) {
      for (input in profile.interactionProfile.inputList) {
        val encoding = encoder.encodeInput(profile.interactionProfile, input)!!
        val decoded = encoder.decodeInput(encoding)!!
        assertThat(decoded.first).isEqualTo(profile.interactionProfile)
        assertThat(decoded.second).isEqualTo(input)
      }
    }
  }

  @Test
  fun decodeInput_nonStandardEncoding_returnsNull() {
    assertThat(encoder.decodeInput("not_an_encoding")).isNull()
  }

  @Test
  fun encodeOutput_eachStandardProfileOutputCombination_generatesUniqueEncodingForEach() {
    val encodingToEncoded = mutableMapOf<String, MutableList<Pair<InteractionProfile, Output>>>()

    for (profile in StandardInteractionProfile.values()) {
      for (output in profile.interactionProfile.outputList) {
        val encoding = encoder.encodeOutput(profile.interactionProfile, output)!!
        encodingToEncoded[encoding] = encodingToEncoded.getOrPut(encoding) { mutableListOf() }.also {
          it.add(Pair(profile.interactionProfile, output))
        }
      }
    }

    for ((encoding, encoded) in encodingToEncoded) {
      if (encoded.size > 1) {
        fail("Expected unique encoding for all profile/output pairs, but found duplicates for $encoding ($encoded).")
      }
    }
  }

  @Test
  fun encodeOutput_nonStandardProfileOutputCombination_returnsNull() {
    val encoding = encoder.encodeOutput(
        StandardInteractionProfile.GOOGLE_DAYDREAM_CONTROLLER.interactionProfile,
        output(StandardUser.TREADMILL.user, StandardOutputIdentifier.HAPTIC.identifier)
    )

    assertThat(encoding).isNull()
  }

  @Test
  fun decodeOutput_eachStandardProfileOutputCombination_restoresOriginalOutputForEach() {
    for (profile in StandardInteractionProfile.values()) {
      for (output in profile.interactionProfile.outputList) {
        val encoding = encoder.encodeOutput(profile.interactionProfile, output)!!
        val decoded = encoder.decodeOutput(encoding)!!
        assertThat(decoded.first).isEqualTo(profile.interactionProfile)
        assertThat(decoded.second).isEqualTo(output)
      }
    }
  }

  @Test
  fun decodeOutput_nonStandardEncoding_returnsNull() {
    assertThat(encoder.decodeOutput("not_an_encoding")).isNull()
  }

  companion object {
    //private val GOLDENS =
  }
}