package io.jackbradshaw.otter.openxr.standard

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class StandardOutputIdentifierTest {
  @Test
  fun toIdentifierAndBack_forEachStandardOutputIdentifier_returnsOriginalValue() {
    for (standardOutputIdentifier in StandardOutputIdentifier.values()) {
      val identifier = standardOutputIdentifier.identifier
      val reverse = StandardOutputIdentifier.fromOutputIdentifer(identifier)
      assertThat(reverse).isEqualTo(standardOutputIdentifier)
    }
  }
}