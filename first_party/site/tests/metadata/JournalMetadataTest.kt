package com.jackbradshaw.site.tests.metadata

import com.google.common.truth.Truth.assertThat
import com.google.devtools.build.runfiles.Runfiles
import com.google.gson.JsonParser
import java.io.File
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class JournalMetadataTest {

  private val runfiles = Runfiles.preload().unmapped()

  @Test
  fun itemsRegistry_everyItem_followsSchema() {
    for (item in loadItemsAsJson()) {
      assertThat(item.has("key")).isTrue()
      assertThat(item.get("key").isJsonPrimitive).isTrue()
      assertThat(item.get("key").asJsonPrimitive.isString).isTrue()

      assertThat(item.has("file")).isTrue()
      assertThat(item.get("file").isJsonPrimitive).isTrue()
      assertThat(item.get("file").asJsonPrimitive.isString).isTrue()

      assertThat(item.has("title")).isTrue()
      assertThat(item.get("title").isJsonPrimitive).isTrue()
      assertThat(item.get("title").asJsonPrimitive.isString).isTrue()

      assertThat(item.has("description")).isTrue()
      assertThat(item.get("description").isJsonPrimitive).isTrue()
      assertThat(item.get("description").asJsonPrimitive.isString).isTrue()

      assertThat(item.has("date")).isTrue()
      assertThat(item.get("date").isJsonObject).isTrue()
      val dateObj = item.getAsJsonObject("date")
      assertThat(dateObj.has("year")).isTrue()
      assertThat(dateObj.get("year").asJsonPrimitive.isNumber).isTrue()
      assertThat(dateObj.has("month")).isTrue()
      assertThat(dateObj.get("month").asJsonPrimitive.isNumber).isTrue()
      assertThat(dateObj.has("day")).isTrue()
      assertThat(dateObj.get("day").asJsonPrimitive.isNumber).isTrue()

      if (item.has("series")) {
        assertThat(item.get("series").isJsonPrimitive).isTrue()
        assertThat(item.get("series").asJsonPrimitive.isString).isTrue()
      }

      if (item.has("original_publication")) {
        assertThat(item.get("original_publication").isJsonObject).isTrue()

        val pub = item.getAsJsonObject("original_publication")
        assertThat(pub.has("publisher") && pub.get("publisher").asJsonPrimitive.isString).isTrue()
        assertThat(pub.has("link") && pub.get("link").asJsonPrimitive.isString).isTrue()
        assertThat(pub.has("date") && pub.get("date").isJsonObject).isTrue()
      }

      if (item.has("alternative_publications")) {
        assertThat(item.get("alternative_publications").isJsonArray).isTrue()

        for (pub in item.getAsJsonArray("alternative_publications")) {
          assertThat(pub.isJsonObject).isTrue()

          val pubObj = pub.asJsonObject
          assertThat(pubObj.has("publisher") && pubObj.get("publisher").asJsonPrimitive.isString)
              .isTrue()
          assertThat(pubObj.has("link") && pubObj.get("link").asJsonPrimitive.isString).isTrue()
        }
      }
    }
  }

  @Test
  fun itemsRegistry_everyItem_existsAsMarkdown() {
    for (item in loadItemsAsJson()) {
      val linkedFile = item.get("file").asString
      val resolvedFile =
          File(runfiles.rlocation("_main/first_party/site/${linkedFile.removePrefix("/")}"))
      assertThat(resolvedFile.exists()).isTrue()
    }
  }

  @Test
  fun genresRegistry_everyGenre_followsSchema() {
    requireCommonRegistrySchema("genres.json")
  }

  @Test
  fun genresRegistry_everyGenre_hasValidKeys() {
    requireKeysToExist(loadAllLinkedItemKeys("genres.json"))
  }

  @Test
  fun seriesRegistry_everySeries_followsSchema() {
    requireCommonRegistrySchema("series.json")
  }

  @Test
  fun seriesRegistry_everySeries_hasValidKeys() {
    requireKeysToExist(loadAllLinkedItemKeys("series.json"))
  }

  @Test
  fun topicsRegistry_everyTopic_followsSchema() {
    requireCommonRegistrySchema("topics.json")
  }

  @Test
  fun topicsRegistry_everyTopic_hasValidKeys() {
    requireKeysToExist(loadAllLinkedItemKeys("topics.json"))
  }

  @Test
  fun highlightsRegistry_everyEntry_isValidKey() {
    val highlights = loadAsJson("highlights.json").map { it.asString }
    requireKeysToExist(highlights)
  }

  /**
   * Returns a JSON array containing the contents of [filename] (relative to the journal data
   * directory).
   */
  private fun loadAsJson(filename: String) =
      JsonParser.parseString(
              File(runfiles.rlocation("_main/first_party/site/data/journal/$filename")).readText())
          .asJsonArray

  /**
   * Returns a JSON array containing the contents of `items.json` (relative to the journal data
   * directory).
   */
  private fun loadItemsAsJson() = loadAsJson("items.json").map { it.asJsonObject }

  /**
   * Extracts all entries within the "items" array across all objects in a given registry (relative
   * to the journal data directory).
   */
  private fun loadAllLinkedItemKeys(registryName: String): List<String> =
      loadAsJson(registryName)
          .map { it.asJsonObject }
          .filter { it.has("items") }
          .flatMap { it.getAsJsonArray("items").map { item -> item.asString } }

  /**
   * Verifies that the schema of the registry in [registryFile] (relative to the journal data
   * directory) follows the common registry schema of key (string), title (string), description
   * (string), and items (list of keys). The validity of the keys is not checked.
   */
  private fun requireCommonRegistrySchema(registryFile: String) {
    for (entry in loadAsJson(registryFile).map { it.asJsonObject }) {
      assertThat(entry.has("key")).isTrue()
      assertThat(entry.get("key").isJsonPrimitive).isTrue()
      assertThat(entry.get("key").asJsonPrimitive.isString).isTrue()

      assertThat(entry.has("title")).isTrue()
      assertThat(entry.get("title").isJsonPrimitive).isTrue()
      assertThat(entry.get("title").asJsonPrimitive.isString).isTrue()

      assertThat(entry.has("description")).isTrue()
      assertThat(entry.get("description").isJsonPrimitive).isTrue()
      assertThat(entry.get("description").asJsonPrimitive.isString).isTrue()

      assertThat(entry.has("items")).isTrue()
      assertThat(entry.get("items").isJsonArray).isTrue()

      for (item in entry.getAsJsonArray("items")) {
        assertThat(item.isJsonPrimitive).isTrue()
        assertThat(item.asJsonPrimitive.isString).isTrue()
      }
    }
  }

  /** Verifies that all provided [keys] exist in items.json. */
  private fun requireKeysToExist(keys: Iterable<String>) {
    val keysInItems = loadItemsAsJson().map { it.get("key").asString }.toSet()

    // There is no direct 'subject is subset of expected' operator, so use subtraction as a proxy.
    assertThat(keys.toSet() - keysInItems).isEmpty()
  }
}
