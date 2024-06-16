package io.jackbradshaw.queen.sustainment.omnisustainer.factoring

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PlatformsSimplexEmptyClasspathTest {

  private val platforms = PlatformsSimplex()

  @Test
  fun componentsIsEmpty() {
    assertThat(platforms.getAll()).isEmpty()
  }
}
