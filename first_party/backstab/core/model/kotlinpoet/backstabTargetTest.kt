package com.jackbradshaw.backstab.core.model.kotlinpoet

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.backstab.core.model.BackstabTarget
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BackstabTargetTest {

  /**
   * Checks whether a qualifier with the following format is converted correctly:
   * ```
   * @Named("some-name")
   * ```
   */
  @Test
  fun qualifier_named_toAnnotationSpec() {
    val qualifier = BackstabTarget.Qualifier.Named("some-name")

    val spec = qualifier.toAnnotationSpec()

    assertThat(spec.className.canonicalName).isEqualTo("javax.inject.Named")
    assertThat(spec.members).hasSize(1)
    assertThat(spec.members.map { it.toString() }).containsExactly("\"some-name\"")
  }

  /**
   * Checks whether a qualifier with the following format is converted correctly:
   * ```
   * @com.example.MyQualifier
   * ```
   */
  @Test
  fun qualifier_custom_shortNameChain_toAnnotationSpec() {
    val qualifier = BackstabTarget.Qualifier.Custom("com.example", listOf("MyQualifier"))

    val spec = qualifier.toAnnotationSpec()

    assertThat(spec.className.canonicalName).isEqualTo("com.example.MyQualifier")
    assertThat(spec.members).isEmpty()
  }

  /**
   * Checks whether a qualifier with the following format is converted correctly:
   * ```
   * @com.example.Outer.Inner
   * ```
   */
  @Test
  fun qualifier_custom_longNameChain_toAnnotationSpec() {
    val qualifier = BackstabTarget.Qualifier.Custom("com.example", listOf("Outer", "Inner"))
    val spec = qualifier.toAnnotationSpec()

    assertThat(spec.className.canonicalName).isEqualTo("com.example.Outer.Inner")
  }

  /**
   * Checks whether a qualifier with the following format is converted correctly:
   * ```
   * @com.example.Level1.Level2.Level3.Level4.Level5.Level6.Level7.Level8.Level9.Level10
   * ```
   */
  @Test
  fun qualifier_custom_veryLongNameChain_toAnnotationSpec() {
    val nameChain =
        listOf(
            "Level1",
            "Level2",
            "Level3",
            "Level4",
            "Level5",
            "Level6",
            "Level7",
            "Level8",
            "Level9",
            "Level10")
    val qualifier = BackstabTarget.Qualifier.Custom("com.example", nameChain)
    val spec = qualifier.toAnnotationSpec()

    assertThat(spec.className.canonicalName)
        .isEqualTo(
            "com.example.Level1.Level2.Level3.Level4.Level5.Level6.Level7.Level8.Level9.Level10")
  }
}
