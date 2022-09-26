package io.jackbradshaw.otter.openxr.manifest.generator

import io.jackbradshaw.otter.openxr.manifest.goldens.goldenPrimaryManifest
import io.jackbradshaw.otter.openxr.manifest.goldens.goldenSecondaryManifests
import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.klu.flow.toMap
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile
import io.jackbradshaw.otter.openxr.model.InteractionProfile
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.devtools.build.runfiles.Runfiles
import io.jackbradshaw.otter.Otter
import io.jackbradshaw.otter.otter
import kotlinx.coroutines.runBlocking
import java.io.File
import dagger.Component
import javax.inject.Scope
import javax.inject.Inject

@RunWith(JUnit4::class)
class ManifestGeneratorTest {

  @Inject lateinit var generator: ManifestGenerator

  @Before
  fun setUp() {
    DaggerTestComponent.builder().setOtter(otter()).build().inject(this)
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
      assertThat(manifestsByProfile[profile.profile]!!.content)
          .isEqualTo(goldenSecondaryManifests[profile]!!)
    }
  }

  private fun readGolden(relativeFilename: String) = File(Runfiles.create().rlocation("$BASE_GOLDEN_PATH/$relativeFilename")).readText()

  companion object {
    private const val BASE_GOLDEN_PATH = "io_jackbradshaw/javatests/io/jackbradshaw/otter/openxr/manifest/goldens"
  }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TestScope

@TestScope
@Component(dependencies = [Otter::class])
interface TestComponent {
  fun inject(test: ManifestGeneratorTest)

  @Component.Builder
  interface Builder {
    fun setOtter(otter: Otter): Builder
    fun build(): TestComponent
  }
}