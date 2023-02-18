package io.jackbradshaw.klu.collections

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NiceDoubleListBufferTest {

  private val buffer = NiceDoubleListBuffer<String>()

  @Test
  fun switch_inactiveListBecomesActive() = runBlocking {
    val firstList = buffer.getActive()

    buffer.switch()

    assertThat(buffer.getActive() === firstList).isFalse()
  }

  @Test
  fun switchTwice_originalActiveListBecomesActiveAgain() = runBlocking {
    val firstList = buffer.getActive()

    buffer.switch()
    buffer.switch()

    assertThat(buffer.getActive() === firstList).isTrue()
  }

  @Test
  fun switchThreeTimes_originalInactiveBecomesActiveAgain() = runBlocking {
    buffer.switch()
    val firstList = buffer.getActive()

    buffer.switch()
    buffer.switch()

    assertThat(buffer.getActive() === firstList).isTrue()
  }
}
