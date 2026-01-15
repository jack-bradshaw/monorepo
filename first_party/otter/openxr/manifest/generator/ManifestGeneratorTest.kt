package com.jackbradshaw.otter.openxr.manifest.generator

import com.google.common.truth.Truth.assertThat
import com.google.devtools.build.runfiles.Runfiles
import com.jackbradshaw.klu.flow.collectToMap
import com.jackbradshaw.otter.OtterComponent
import com.jackbradshaw.otter.openxr.manifest.goldens.goldenPrimaryManifest
import com.jackbradshaw.otter.openxr.manifest.goldens.goldenSecondaryManifests
import com.jackbradshaw.otter.openxr.model.InteractionProfile
import com.jackbradshaw.otter.openxr.standard.StandardInteractionProfile
import com.jackbradshaw.otter.otterComponent
import dagger.Component
import java.io.File
import javax.inject.Inject
import javax.inject.Scope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.skyscreamer.jsonassert.JSONAssert

@RunWith(JUnit4::class)
class ManifestGeneratorTest {

  @Inject lateinit var generator: ManifestGenerator

  @Before
  fun setUp() {
    DaggerTestComponent.builder().consuming(otterComponent()).build().inject(this)
  }

  @Test
  fun generateManifest_primaryManifest_isGolden() = runBlocking {
    val manifests = generator.generateManifests()

    JSONAssert.assertEquals(manifests.primaryManifest, goldenPrimaryManifest, /* strict= */ true)
  }

  @Test
  fun generateManifest_secondaryManifests_oneForEachStandardInteractionProfile() = runBlocking {
    val manifests = generator.generateManifests()

    assertThat(manifests.secondaryManifests.size)
        .isEqualTo(StandardInteractionProfile.values().size)
  }

  @Test
  fun generateManifest_secondaryManifestForEachStandardInteractionProfile_isGolden() = runBlocking {
    val manifests = generator.generateManifests()

    val manifestsByProfile: Map<InteractionProfile, SecondaryManifest> =
        manifests.secondaryManifests.asFlow().map { it.profile to it }.collectToMap()

    for (profile in StandardInteractionProfile.values()) {
      JSONAssert.assertEquals(
          manifestsByProfile[profile.profile]!!.content,
          goldenSecondaryManifests[profile]!!,
          /* strict= */ true)
    }
  }

  private fun readGolden(relativeFilename: String) =
      File(runfiles.rlocation("$BASE_GOLDEN_PATH/$relativeFilename")).readText()

  companion object {
    private val runfiles = Runfiles.preload().unmapped()
    private const val BASE_GOLDEN_PATH = "_main/first_party/otter/openxr/manifest/testgoldens"
  }
}

@Scope @Retention(AnnotationRetention.RUNTIME) annotation class TestScope

@TestScope
@Component(dependencies = [OtterComponent::class])
interface TestComponent {
  fun inject(test: ManifestGeneratorTest)

  @Component.Builder
  interface Builder {
    fun consuming(otter: OtterComponent): Builder

    fun build(): TestComponent
  }
}
