package io.jackbradshaw.queen.sustainment.omnisustainer.factoring

import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.queen.sustainment.omnisustainer.factoring.PlatformsSimplex
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PlatformsSimplexNonEmptyClasspathTest {

  private val platforms = PlatformsSimplex()

  @Test
  fun componentsContainsComponentsAvailableInClasspath() {
    assertThat(platforms.getAll()).hasSize(1)
    assertThat(platforms.getAll().toList()[0] is KtCoroutinePlatform).isTrue()
  }
}
