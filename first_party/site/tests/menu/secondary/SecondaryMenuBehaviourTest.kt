package com.jackbradshaw.site.tests.menu.secondary

import com.jackbradshaw.site.tests.MenuType
import com.jackbradshaw.site.tests.menu.BaseMenuBehaviourTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SecondaryMenuBehaviourTest : BaseMenuBehaviourTest(MenuType.SECONDARY) {

  @Test
  fun gallery_startingCollapsed_highlights() =
      runStartingCollapsedTest(
          pagePath = "/gallery/subjects",
          itemSelector = "Highlights",
          expectedDestinationPath = "/gallery/highlights")

  @Test
  fun gallery_startingCollapsed_subjects() =
      runStartingCollapsedTest(
          pagePath = "/gallery/highlights",
          itemSelector = "Subjects",
          expectedDestinationPath = "/gallery/subjects")

  @Test
  fun gallery_startingCollapsed_palettes() =
      runStartingCollapsedTest(
          pagePath = "/gallery/highlights",
          itemSelector = "Palettes",
          expectedDestinationPath = "/gallery/palettes")

  @Test
  fun gallery_startingCollapsed_chronological() =
      runStartingCollapsedTest(
          pagePath = "/gallery/highlights",
          itemSelector = "Chronological",
          expectedDestinationPath = "/gallery/chronological")

  @Test
  fun gallery_alwaysExpanded_highlights() =
      runAlwaysExpandedTest(
          pagePath = "/gallery/subjects",
          itemSelector = "Highlights",
          expectedDestinationPath = "/gallery/highlights")

  @Test
  fun gallery_alwaysExpanded_subjects() =
      runAlwaysExpandedTest(
          pagePath = "/gallery/highlights",
          itemSelector = "Subjects",
          expectedDestinationPath = "/gallery/subjects")

  @Test
  fun gallery_alwaysExpanded_palettes() =
      runAlwaysExpandedTest(
          pagePath = "/gallery/highlights",
          itemSelector = "Palettes",
          expectedDestinationPath = "/gallery/palettes")

  @Test
  fun gallery_alwaysExpanded_chronological() =
      runAlwaysExpandedTest(
          pagePath = "/gallery/highlights",
          itemSelector = "Chronological",
          expectedDestinationPath = "/gallery/chronological")

  @Test
  fun journal_startingCollapsed_highlights() =
      runStartingCollapsedTest(
          pagePath = "/journal/topics",
          itemSelector = "Highlights",
          expectedDestinationPath = "/journal/highlights")

  @Test
  fun journal_startingCollapsed_topics() =
      runStartingCollapsedTest(
          pagePath = "/journal/highlights",
          itemSelector = "Topics",
          expectedDestinationPath = "/journal/topics")

  @Test
  fun journal_startingCollapsed_series() =
      runStartingCollapsedTest(
          pagePath = "/journal/highlights",
          itemSelector = "Series",
          expectedDestinationPath = "/journal/serieslist")

  @Test
  fun journal_startingCollapsed_genres() =
      runStartingCollapsedTest(
          pagePath = "/journal/highlights",
          itemSelector = "Genres",
          expectedDestinationPath = "/journal/genres")

  @Test
  fun journal_startingCollapsed_chronological() =
      runStartingCollapsedTest(
          pagePath = "/journal/highlights",
          itemSelector = "Chronological",
          expectedDestinationPath = "/journal/chronological")

  @Test
  fun journal_alwaysExpanded_highlights() =
      runAlwaysExpandedTest(
          pagePath = "/journal/topics",
          itemSelector = "Highlights",
          expectedDestinationPath = "/journal/highlights")

  @Test
  fun journal_alwaysExpanded_topics() =
      runAlwaysExpandedTest(
          pagePath = "/journal/highlights",
          itemSelector = "Topics",
          expectedDestinationPath = "/journal/topics")

  @Test
  fun journal_alwaysExpanded_series() =
      runAlwaysExpandedTest(
          pagePath = "/journal/highlights",
          itemSelector = "Series",
          expectedDestinationPath = "/journal/serieslist")

  @Test
  fun journal_alwaysExpanded_genres() =
      runAlwaysExpandedTest(
          pagePath = "/journal/highlights",
          itemSelector = "Genres",
          expectedDestinationPath = "/journal/genres")

  @Test
  fun journal_alwaysExpanded_chronological() =
      runAlwaysExpandedTest(
          pagePath = "/journal/highlights",
          itemSelector = "Chronological",
          expectedDestinationPath = "/journal/chronological")

  @Test
  fun repository_startingCollapsed_highlights() =
      runStartingCollapsedTest(
          pagePath = "/repository/locations",
          itemSelector = "Highlights",
          expectedDestinationPath = "/repository/highlights")

  @Test
  fun repository_startingCollapsed_locations() =
      runStartingCollapsedTest(
          pagePath = "/repository/highlights",
          itemSelector = "Locations",
          expectedDestinationPath = "/repository/locations")

  @Test
  fun repository_startingCollapsed_technologies() =
      runStartingCollapsedTest(
          pagePath = "/repository/highlights",
          itemSelector = "Technologies",
          expectedDestinationPath = "/repository/technologies")

  @Test
  fun repository_alwaysExpanded_highlights() =
      runAlwaysExpandedTest(
          pagePath = "/repository/locations",
          itemSelector = "Highlights",
          expectedDestinationPath = "/repository/highlights")

  @Test
  fun repository_alwaysExpanded_locations() =
      runAlwaysExpandedTest(
          pagePath = "/repository/highlights",
          itemSelector = "Locations",
          expectedDestinationPath = "/repository/locations")

  @Test
  fun repository_alwaysExpanded_technologies() =
      runAlwaysExpandedTest(
          pagePath = "/repository/highlights",
          itemSelector = "Technologies",
          expectedDestinationPath = "/repository/technologies")
}
