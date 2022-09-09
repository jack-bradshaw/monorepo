package io.jackbradshaw.omnixr.standard

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class StandardInputIdentifierTest {
  @Test
  fun toIdentifierAndBack_forEachStandardInputIdentifier_returnsOriginalValue() {
    for (standardIdentifier in StandardInputIdentifier.values()) {
      val identifier = standardIdentifier.identifier
      val reverse = StandardInputIdentifier.fromInputIdentifier(identifier)
      assertThat(reverse).isEqualTo(standardIdentifier)
    }
  }
}