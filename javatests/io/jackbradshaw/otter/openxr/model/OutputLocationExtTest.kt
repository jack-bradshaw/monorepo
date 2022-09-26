package io.jackbradshaw.otter.openxr.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class OutputLocationExtTest {
  @Test
  fun outputLocation_passesValuesThroughIntoProto() {
    val id = "locationId"

    val outputLocation = outputLocation(id)

    assertThat(outputLocation).isEqualTo(OutputLocation.newBuilder().setId(id).build())
  }
}