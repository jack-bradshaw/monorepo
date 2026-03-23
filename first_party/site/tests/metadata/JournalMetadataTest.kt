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
    for (item in readItems()) {
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
    for (item in readItems()) {
      val linkedFile = item.get("file").asString
      val resolvedFile =
          File(runfiles.rlocation("_main/first_party/site/${linkedFile.removePrefix("/")}"))
      assertThat(resolvedFile.exists()).isTrue()
    }
  }

  @Test
  fun charactersRegistry_everyCharacter_followsSchema() {
    verifyCommonRegistrySchema("characters.json")
  }

  @Test
  fun charactersRegistry_everyCharacter_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("characters.json"))
  }

  @Test
  fun settingsRegistry_everySetting_followsSchema() {
    verifyCommonRegistrySchema("settings.json")
  }

  @Test
  fun settingsRegistry_everySetting_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("settings.json"))
  }

  @Test
  fun veracityRegistry_everyVeracity_followsSchema() {
    verifyCommonRegistrySchema("veracity.json")
  }

  @Test
  fun veracityRegistry_everyVeracity_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("veracity.json"))
  }

  @Test
  fun tropesRegistry_everyTrope_followsSchema() {
    verifyCommonRegistrySchema("tropes.json")
  }

  @Test
  fun tropesRegistry_everyTrope_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("tropes.json"))
  }

  @Test
  fun compositionRegistry_everyComposition_followsSchema() {
    verifyCommonRegistrySchema("composition.json")
  }

  @Test
  fun compositionRegistry_everyComposition_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("composition.json"))
  }

  @Test
  fun styleRegistry_everyStyle_followsSchema() {
    verifyCommonRegistrySchema("style.json")
  }

  @Test
  fun styleRegistry_everyStyle_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("style.json"))
  }

  @Test
  fun genresRegistry_everyGenre_followsSchema() {
    verifyCommonRegistrySchema("genres.json")
  }

  @Test
  fun genresRegistry_everyGenre_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("genres.json"))
  }

  @Test
  fun seriesRegistry_everySeries_followsSchema() {
    verifyCommonRegistrySchema("series.json")
  }

  @Test
  fun seriesRegistry_everySeries_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("series.json"))
  }

  @Test
  fun subjectsRegistry_everySubject_followsSchema() {
    verifyCommonRegistrySchema("subjects.json")
  }

  @Test
  fun subjectsRegistry_everySubject_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("subjects.json"))
  }

  @Test
  fun themesRegistry_everyTheme_followsSchema() {
    verifyCommonRegistrySchema("themes.json")
  }

  @Test
  fun themesRegistry_everyTheme_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("themes.json"))
  }

  @Test
  fun topicsRegistry_everyTopic_followsSchema() {
    verifyCommonRegistrySchema("topics.json")
  }

  @Test
  fun topicsRegistry_everyTopic_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("topics.json"))
  }

  @Test
  fun movementsRegistry_everyMovement_followsSchema() {
    verifyCommonRegistrySchema("movements.json")
  }

  @Test
  fun movementsRegistry_everyMovement_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("movements.json"))
  }

  @Test
  fun toneRegistry_everyTone_followsSchema() {
    verifyCommonRegistrySchema("tone.json")
  }

  @Test
  fun toneRegistry_everyTone_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("tone.json"))
  }

  @Test
  fun audienceRegistry_everyAudience_followsSchema() {
    verifyCommonRegistrySchema("audience.json")
  }

  @Test
  fun audienceRegistry_everyAudience_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("audience.json"))
  }

  @Test
  fun ratingsRegistry_everyRating_followsSchema() {
    verifyCommonRegistrySchema("ratings.json")
  }

  @Test
  fun ratingsRegistry_everyRating_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("ratings.json"))
  }

  @Test
  fun languageRegistry_everyLanguage_followsSchema() {
    verifyCommonRegistrySchema("language.json")
  }

  @Test
  fun languageRegistry_everyLanguage_hasValidKeys() {
    verifyKeysExist(extractAllItemKeys("language.json"))
  }

  @Test
  fun behaviourRegistry_everyEntry_followsSchema() {
    val fileContent =
        File(runfiles.rlocation("_main/first_party/site/data/journal/behaviour.json")).readText()
    val behaviour = JsonParser.parseString(fileContent).asJsonObject
    assertThat(behaviour.keySet()).isNotEmpty()
    assertThat(behaviour.has("subjects")).isTrue()
  }

  @Test
  fun highlightsRegistry_everyEntry_isValidKey() {
    val highlights = parseJson("highlights.json").map { it.asString }
    verifyKeysExist(highlights)
  }

  /**
   * Returns a JSON array containing the contents of [filename] (relative to the journal data
   * directory).
   */
  private fun parseJson(filename: String) =
      JsonParser.parseString(
              File(runfiles.rlocation("_main/first_party/site/data/journal/$filename")).readText())
          .asJsonArray

  /**
   * Returns a JSON array containing the contents of `items.json` (relative to the journal data
   * directory).
   */
  private fun readItems() = parseJson("items.json").map { it.asJsonObject }

  /**
   * Verifies the schema of the registry in [registryFile] (relative to the journal data directory).
   * The expected schema is an object containing key (string), title (string), description (string),
   * and items (list of keys). The validity of the keys is not checked.
   */
  private fun verifyCommonRegistrySchema(registryFile: String) {
    for (entry in parseJson(registryFile).map { it.asJsonObject }) {
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

  /**
   * Extracts all entries within the "items" array across all registry objects in a given JSON file.
   */
  private fun extractAllItemKeys(registryName: String): List<String> =
      parseJson(registryName)
          .map { it.asJsonObject }
          .filter { it.has("items") }
          .flatMap { it.getAsJsonArray("items").map { item -> item.asString } }

  /**
   * Verifies that the provided [keys] are valid. Keys are valid when they have an associated entry
   * in `items.json` (relative to the journal data directory).
   */
  private fun verifyKeysExist(keys: Iterable<String>) {
    val keysInItems = readItems().map { it.get("key").asString }.toSet()

    // There is no direct 'subject is subset of expected' operator, so use subtraction as a proxy.
    assertThat(keys.toSet() - keysInItems).isEmpty()
  }
}
