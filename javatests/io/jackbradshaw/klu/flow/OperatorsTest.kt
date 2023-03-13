package io.jackbradshaw.klu.flow

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OperatorsTest {

  @Test
  fun collectToMap_emptyFlow_mapIsEmpty() = runBlocking {
    val source = flow<Pair<String, String>> {}

    val map = source.collectToMap()

    assertThat(map).isEmpty()
  }

  @Test
  fun collectToMap_flowWithOneItem_mapContainsItem() = runBlocking {
    val source = flow<Pair<String, String>> { emit("key" to "value") }

    val map = source.collectToMap()

    assertThat(map.size).isEqualTo(1)
    assertThat(map["key"]).isEqualTo("value")
  }

  @Test
  fun collectTomap_flowWithMultipleItemsAllUniqueKeys_mapContainsAllItems() = runBlocking {
    val source =
        flow<Pair<String, String>> {
          emit("key1" to "value1")
          emit("key2" to "value2")
        }

    val map = source.collectToMap()

    assertThat(map.size).isEqualTo(2)
    assertThat(map["key1"]).isEqualTo("value1")
    assertThat(map["key2"]).isEqualTo("value2")
  }

  @Test
  fun collectToMap_flowWithMultipleItemsSomeNonUniqueKeys_mapContainsLatestValuesForEachKey() =
      runBlocking {
        val source =
            flow<Pair<String, String>> {
              emit("key1" to "value1")
              emit("key1" to "value2")
              emit("key2" to "value2")
            }

        val map = source.collectToMap()

        assertThat(map.size).isEqualTo(2)
        assertThat(map["key1"]).isEqualTo("value2")
        assertThat(map["key2"]).isEqualTo("value2")
      }
}
