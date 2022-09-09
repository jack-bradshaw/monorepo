package io.jackbradshaw.omnixr.standard

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class StandardInputComponentTest {
  @Test
  fun toInputComponentAndBack_forEachStandardInputComponent_returnsOriginalValue() {
    for (standardInputComponent in StandardInputComponent.values()) {
      val inputComponent = standardInputComponent.component
      val reverse = StandardInputComponent.fromInputComponent(inputComponent)
      assertThat(reverse).isEqualTo(standardInputComponent)
    }
  }
}