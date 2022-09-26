package io.jackbradshaw.otter.openxr.manifest.encoder

import io.jackbradshaw.otter.openxr.model.InteractionProfile
import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.model.input
import io.jackbradshaw.otter.openxr.model.Output
import io.jackbradshaw.otter.openxr.model.output
import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.otter.Otter
import javax.inject.Scope
import io.jackbradshaw.otter.openxr.standard.StandardInputComponent
import io.jackbradshaw.otter.openxr.standard.StandardInputIdentifier
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile
import io.jackbradshaw.otter.openxr.standard.StandardOutputIdentifier
import io.jackbradshaw.otter.openxr.standard.StandardUser
import io.jackbradshaw.otter.otter
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import kotlin.test.fail
import org.junit.runners.JUnit4
import dagger.Component
import javax.inject.Inject

@RunWith(JUnit4::class)
class ManifestEncoderTest {

  @Inject lateinit var encoder: ManifestEncoder

  @Before
  fun setUp() {
    DaggerTestComponent.builder().setOtter(otter()).build().inject(this)
  }

  @Test
  fun encodeInput_eachStandardProfileInputCombination_generatesUniqueEncodingForEach() {
    val encodingToEncoded = mutableMapOf<String, MutableList<Pair<InteractionProfile, Input>>>()

    for (profile in StandardInteractionProfile.values()) {
      for (input in profile.profile.inputList) {
        val encoding = encoder.encodeInput(profile.profile, input)!!
        encodingToEncoded[encoding] = encodingToEncoded.getOrPut(encoding) { mutableListOf() }.also {
          it.add(Pair(profile.profile, input))
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
        StandardInteractionProfile.GOOGLE_DAYDREAM_CONTROLLER.profile,
        input(StandardUser.TREADMILL.user, StandardInputIdentifier.THUMBSTICK.identifier, StandardInputComponent.CLICK.component)
    )

    assertThat(encoding).isNull()
  }

  @Test
  fun decodeInput_eachStandardProfileInputCombination_restoresOriginalInputForEach() {
    for (profile in StandardInteractionProfile.values()) {
      for (input in profile.profile.inputList) {
        val encoding = encoder.encodeInput(profile.profile, input)!!
        val decoded = encoder.decodeInput(encoding)!!
        assertThat(decoded.first).isEqualTo(profile.profile)
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
      for (output in profile.profile.outputList) {
        val encoding = encoder.encodeOutput(profile.profile, output)!!
        encodingToEncoded[encoding] = encodingToEncoded.getOrPut(encoding) { mutableListOf() }.also {
          it.add(Pair(profile.profile, output))
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
        StandardInteractionProfile.GOOGLE_DAYDREAM_CONTROLLER.profile,
        output(StandardUser.TREADMILL.user, StandardOutputIdentifier.HAPTIC.identifier)
    )

    assertThat(encoding).isNull()
  }

  @Test
  fun decodeOutput_eachStandardProfileOutputCombination_restoresOriginalOutputForEach() {
    for (profile in StandardInteractionProfile.values()) {
      for (output in profile.profile.outputList) {
        val encoding = encoder.encodeOutput(profile.profile, output)!!
        val decoded = encoder.decodeOutput(encoding)!!
        assertThat(decoded.first).isEqualTo(profile.profile)
        assertThat(decoded.second).isEqualTo(output)
      }
    }
  }

  @Test
  fun decodeOutput_nonStandardEncoding_returnsNull() {
    assertThat(encoder.decodeOutput("not_an_encoding")).isNull()
  }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TestScope

@TestScope
@Component(dependencies = [Otter::class])
interface TestComponent {
  fun inject(test: ManifestEncoderTest)

  @Component.Builder
  interface Builder {
    fun setOtter(otter: Otter): Builder
    fun build(): TestComponent
  }
}