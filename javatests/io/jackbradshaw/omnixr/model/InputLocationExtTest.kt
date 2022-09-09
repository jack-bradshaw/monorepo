package io.jackbradshaw.omnixr.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class InputLocationExtTest {
  @Test
  fun inputLocation_passesValuesThroughIntoProto() {
    val id = "locationId"

    val inputLocation = inputLocation(id)

    assertThat(inputLocation).isEqualTo(InputLocation.newBuilder().setId(id).build())
  }
}