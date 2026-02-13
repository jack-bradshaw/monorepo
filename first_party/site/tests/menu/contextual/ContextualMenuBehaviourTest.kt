package com.jackbradshaw.site.tests.menu.contextual

import com.jackbradshaw.site.tests.MenuType
import com.jackbradshaw.site.tests.menu.BaseMenuBehaviourTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ContextualMenuBehaviourTest : BaseMenuBehaviourTest(MenuType.CONTEXTUAL) {

  @Test
  fun galleryItem_startingCollapsed_next() =
      runStartingCollapsedTest(
          pagePath = "/gallery/item/nothing_2022",
          itemSelector = "Next >",
          expectedDestinationPath = "/gallery/item/singularity_2025")

  @Test
  fun galleryItem_startingCollapsed_previous() =
      runStartingCollapsedTest(
          pagePath = "/gallery/item/intelligence_2023",
          itemSelector = "< Previous",
          expectedDestinationPath = "/gallery/item/cohesion_2023")

  @Test
  fun galleryItem_startingCollapsed_series() =
      runStartingCollapsedTest(
          pagePath = "/gallery/item/anything_2025",
          itemSelector = "More in Series (Genesis Ex Nihilo)",
          expectedDestinationPath = "/gallery/series/genesis_ex_nihilo/")

  @Test
  fun galleryItem_startingCollapsed_subject() =
      runStartingCollapsedTest(
          pagePath = "/gallery/item/anything_2025",
          itemSelector = "More in Subject (The Bigger Picture)",
          expectedDestinationPath = "/gallery/subjects#the_bigger_picture")

  @Test
  fun galleryItem_alwaysExpanded_next() =
      runAlwaysExpandedTest(
          pagePath = "/gallery/item/nothing_2022",
          itemSelector = "Next >",
          expectedDestinationPath = "/gallery/item/singularity_2025")

  @Test
  fun galleryItem_alwaysExpanded_previous() =
      runAlwaysExpandedTest(
          pagePath = "/gallery/item/intelligence_2023",
          itemSelector = "< Previous",
          expectedDestinationPath = "/gallery/item/cohesion_2023")

  @Test
  fun galleryItem_alwaysExpanded_series() =
      runAlwaysExpandedTest(
          pagePath = "/gallery/item/anything_2025",
          itemSelector = "More in Series (Genesis Ex Nihilo)",
          expectedDestinationPath = "/gallery/series/genesis_ex_nihilo/")

  @Test
  fun galleryItem_alwaysExpanded_subject() =
      runAlwaysExpandedTest(
          pagePath = "/gallery/item/anything_2025",
          itemSelector = "More in Subject (The Bigger Picture)",
          expectedDestinationPath = "/gallery/subjects#the_bigger_picture")

  @Test
  fun journalItem_startingCollapsed_next() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_startingCollapsed_previous() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_startingCollapsed_topic() =
      runStartingCollapsedTest(
          pagePath = "/journal/item/into-the-subverse",
          itemSelector = "More in Topic (Philosophy)",
          expectedDestinationPath = "/journal/topics#philosophy")

  @Test
  fun journalItem_startingCollapsed_genre() =
      runStartingCollapsedTest(
          pagePath = "/journal/item/into-the-subverse",
          itemSelector = "More in Genre (Essays)",
          expectedDestinationPath = "/journal/genres#essays")

  @Test
  fun journalItem_alwaysExpanded_next() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_alwaysExpanded_previous() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_alwaysExpanded_topic() =
      runAlwaysExpandedTest(
          pagePath = "/journal/item/into-the-subverse",
          itemSelector = "More in Topic (Philosophy)",
          expectedDestinationPath = "/journal/topics#philosophy")

  @Test
  fun journalItem_alwaysExpanded_genre() =
      runAlwaysExpandedTest(
          pagePath = "/journal/item/into-the-subverse",
          itemSelector = "More in Genre (Essays)",
          expectedDestinationPath = "/journal/genres#essays")
}
