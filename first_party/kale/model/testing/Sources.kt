package com.jackbradshaw.kale.testing

import com.jackbradshaw.kale.model.Source

/**
 * Sources for use in tests.
 *
 * No malformed Java source is defined because no tests require one.
 */
object TestSources {
  /** A syntactically-correct Kotlin source. */
  val VALID_KOTLIN_SOURCE =
      Source(fileName = "ValidKotlin", extension = "kt", contents = "class ValidKotlin")

  /** A syntactically-correct Java source. */
  val VALID_JAVA_SOURCE =
      Source(fileName = "ValidJava", extension = "java", contents = "class ValidJava {}")

  /** A malformed Kotlin source. */
  val BROKEN_KOTLIN_SOURCE =
      Source(
          fileName = "BrokenKotlin",
          extension = "kt",
          contents = "class BrokenKotlin { broken syntax")
}
