package com.jackbradshaw.site.tests.content.journal

import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.content.BaseContentAppearanceTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class JournalContentAppearanceTest :
    BaseContentAppearanceTest(JournalContentAppearanceTest.GOLDEN_BASE_PATH) {

  @Test
  fun highlights_smallScreen() = runNonCollapsibleTest("/journal/highlights", ScreenWidth.SMALL)

  @Test
  fun highlights_mediumScreen() = runNonCollapsibleTest("/journal/highlights", ScreenWidth.MEDIUM)

  @Test
  fun highlights_largeScreen() = runNonCollapsibleTest("/journal/highlights", ScreenWidth.LARGE)

  @Test fun topics_smallScreen_collapsed() = runCollapsedTest("/journal/topics", ScreenWidth.SMALL)

  @Test fun topics_smallScreen_expanded() = runExpandedTest("/journal/topics", ScreenWidth.SMALL)

  @Test
  fun topics_mediumScreen_collapsed() = runCollapsedTest("/journal/topics", ScreenWidth.MEDIUM)

  @Test fun topics_mediumScreen_expanded() = runExpandedTest("/journal/topics", ScreenWidth.MEDIUM)

  @Test fun topics_largeScreen_collapsed() = runCollapsedTest("/journal/topics", ScreenWidth.LARGE)

  @Test fun topics_largeScreen_expanded() = runExpandedTest("/journal/topics", ScreenWidth.LARGE)

  @Test
  fun series_smallScreen_collapsed() = runCollapsedTest("/journal/serieslist", ScreenWidth.SMALL)

  @Test
  fun series_smallScreen_expanded() = runExpandedTest("/journal/serieslist", ScreenWidth.SMALL)

  @Test
  fun series_mediumScreen_collapsed() = runCollapsedTest("/journal/serieslist", ScreenWidth.MEDIUM)

  @Test
  fun series_mediumScreen_expanded() = runExpandedTest("/journal/serieslist", ScreenWidth.MEDIUM)

  @Test
  fun series_largeScreen_collapsed() = runCollapsedTest("/journal/serieslist", ScreenWidth.LARGE)

  @Test
  fun series_largeScreen_expanded() = runExpandedTest("/journal/serieslist", ScreenWidth.LARGE)

  @Test fun genres_smallScreen_collapsed() = runCollapsedTest("/journal/genres", ScreenWidth.SMALL)

  @Test fun genres_smallScreen_expanded() = runExpandedTest("/journal/genres", ScreenWidth.SMALL)

  @Test
  fun genres_mediumScreen_collapsed() = runCollapsedTest("/journal/genres", ScreenWidth.MEDIUM)

  @Test fun genres_mediumScreen_expanded() = runExpandedTest("/journal/genres", ScreenWidth.MEDIUM)

  @Test fun genres_largeScreen_collapsed() = runCollapsedTest("/journal/genres", ScreenWidth.LARGE)

  @Test fun genres_largeScreen_expanded() = runExpandedTest("/journal/genres", ScreenWidth.LARGE)

  @Test
  fun chronological_smallScreen_collapsed() =
      runCollapsedTest("/journal/chronological", ScreenWidth.SMALL)

  @Test
  fun chronological_smallScreen_expanded() =
      runExpandedTest("/journal/chronological", ScreenWidth.SMALL)

  @Test
  fun chronological_mediumScreen_collapsed() =
      runCollapsedTest("/journal/chronological", ScreenWidth.MEDIUM)

  @Test
  fun chronological_mediumScreen_expanded() =
      runExpandedTest("/journal/chronological", ScreenWidth.MEDIUM)

  @Test
  fun chronological_largeScreen_collapsed() =
      runCollapsedTest("/journal/chronological", ScreenWidth.LARGE)

  @Test
  fun chronological_largeScreen_expanded() =
      runExpandedTest("/journal/chronological", ScreenWidth.LARGE)

  @Test
  fun item_smallScreen() =
      runNonCollapsibleTest("/journal/item/into-the-subverse", ScreenWidth.SMALL)

  @Test
  fun item_mediumScreen() =
      runNonCollapsibleTest("/journal/item/into-the-subverse", ScreenWidth.MEDIUM)

  @Test
  fun item_largeScreen() =
      runNonCollapsibleTest("/journal/item/into-the-subverse", ScreenWidth.LARGE)

  @Test
  fun series_smallScreen() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun series_mediumScreen() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun series_largeScreen() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  companion object {
    /** The path to the golden files directory relative to the runfiles root. */
    private const val GOLDEN_BASE_PATH = "_main/first_party/site/tests/content/journal/goldens"
  }
}
