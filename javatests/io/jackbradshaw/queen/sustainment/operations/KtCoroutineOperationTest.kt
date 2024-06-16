package io.jackbradshaw.queen.sustainment.operations

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class KtCoroutineOperationTest {

  @Test
  fun keepAliveType_alwaysSameInstance() {
    val operation1 =
        object : KtCoroutineOperation() {
          override fun work() = GlobalScope.launch {}
        }
    val operation2 =
        object : KtCoroutineOperation() {
          override fun work() = GlobalScope.launch {}
        }
    assertThat(operation1.workType == operation2.workType).isTrue()
  }
}
