package io.jackbradshaw.clearxr.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class InputComponentExtTest {
  @Test
  fun inputComponent_passesValuesThroughIntoProto() {
    val id = "testId"

    val component = inputComponent(id)

    assertThat(component).isEqualTo(InputComponent.newBuilder().setId(id).build())
  }
}