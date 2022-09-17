package io.jackbradshaw.clearxr.manifest.generator

import io.jackbradshaw.clearxr.manifest.goldens.goldenPrimaryManifest
import io.jackbradshaw.clearxr.manifest.goldens.goldenSecondaryManifests
import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.klu.flow.toMap
import io.jackbradshaw.clearxr.clearxr
import io.jackbradshaw.clearxr.standard.StandardInteractionProfile
import io.jackbradshaw.clearxr.model.InteractionProfile
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.devtools.build.runfiles.Runfiles
import kotlinx.coroutines.runBlocking
import java.io.File

@RunWith(JUnit4::class)
class ManifestGeneratorTest {

  private lateinit var generator: ManifestGenerator

  @Before
  fun setUp() {
    generator = clearxr().manifestGenerator()
  }

  @Test
  fun generateManifest_primaryManifest_isGolden() = runBlocking {
    val manifests = generator.generateManifests()

    assertThat(manifests.primaryManifest).isEqualTo(goldenPrimaryManifest)
  }

  @Test
  fun generateManifest_secondaryManifests_oneForEachStandardInteractionProfile() = runBlocking {
    val manifests = generator.generateManifests()

    assertThat(manifests.secondaryManifests.size).isEqualTo(StandardInteractionProfile.values().size)
  }

  @Test
  fun generateManifest_secondaryManifestForEachStandardInteractionProfile_isGolden() = runBlocking {
    val manifests = generator.generateManifests()

    val manifestsByProfile: Map<InteractionProfile, SecondaryManifest> = manifests
        .secondaryManifests
        .asFlow()
        .map { it.profile to it }
        .toMap()

    for (profile in StandardInteractionProfile.values()) {
      assertThat(manifestsByProfile[profile.interactionProfile]!!.content)
          .isEqualTo(goldenSecondaryManifests[profile]!!)
    }
  }

  private fun readGolden(relativeFilename: String) = File(Runfiles.create().rlocation("$BASE_GOLDEN_PATH/$relativeFilename")).readText()

  companion object {
    private const val BASE_GOLDEN_PATH = "io_jackbradshaw/javatests/io/jackbradshaw/clearxr/manifest/goldens"
  }
}