package io.jackbradshaw.otter.openxr.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class InputIdentifierExtTest {
  @Test
  fun inputIdentifier_passesValuesThroughIntoProto() {
    val id = "identifierId"

    val inputIdentifier = inputIdentifier(id)

    assertThat(inputIdentifier).isEqualTo(InputIdentifier.newBuilder().setId(id).build())
  }
}