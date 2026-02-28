package com.jackbradshaw.site.tests.content.gallery

import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.content.BaseContentAppearanceTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GalleryContentAppearanceTest :
    BaseContentAppearanceTest(GalleryContentAppearanceTest.GOLDEN_BASE_PATH) {

  @Test
  fun highlights_smallScreen() = runNonCollapsibleTest("/gallery/highlights", ScreenWidth.SMALL)

  @Test
  fun highlights_mediumScreen() = runNonCollapsibleTest("/gallery/highlights", ScreenWidth.MEDIUM)

  @Test
  fun highlights_largeScreen() = runNonCollapsibleTest("/gallery/highlights", ScreenWidth.LARGE)

  @Test
  fun subjects_smallScreen_collapsed() = runCollapsedTest("/gallery/subjects", ScreenWidth.SMALL)

  @Test
  fun subjects_smallScreen_expanded() = runExpandedTest("/gallery/subjects", ScreenWidth.SMALL)

  @Test
  fun subjects_mediumScreen_collapsed() = runCollapsedTest("/gallery/subjects", ScreenWidth.MEDIUM)

  @Test
  fun subjects_mediumScreen_expanded() = runExpandedTest("/gallery/subjects", ScreenWidth.MEDIUM)

  @Test
  fun subjects_largeScreen_collapsed() = runCollapsedTest("/gallery/subjects", ScreenWidth.LARGE)

  @Test
  fun subjects_largeScreen_expanded() = runExpandedTest("/gallery/subjects", ScreenWidth.LARGE)

  @Test
  fun palettes_smallScreen_collapsed() = runCollapsedTest("/gallery/palettes", ScreenWidth.SMALL)

  @Test
  fun palettes_smallScreen_expanded() =
      runExpandedTest("/gallery/palettes", ScreenWidth.SMALL, captureFullPage = false)

  @Test
  fun palettes_mediumScreen_collapsed() = runCollapsedTest("/gallery/palettes", ScreenWidth.MEDIUM)

  @Test
  fun palettes_mediumScreen_expanded() = runExpandedTest("/gallery/palettes", ScreenWidth.MEDIUM)

  @Test
  fun palettes_largeScreen_collapsed() = runCollapsedTest("/gallery/palettes", ScreenWidth.LARGE)

  @Test
  fun palettes_largeScreen_expanded() = runExpandedTest("/gallery/palettes", ScreenWidth.LARGE)

  @Test
  fun chronological_smallScreen_collapsed() =
      runCollapsedTest("/gallery/chronological", ScreenWidth.SMALL)

  @Test
  fun chronological_smallScreen_expanded() =
      runExpandedTest("/gallery/chronological", ScreenWidth.SMALL, captureFullPage = false)

  @Test
  fun chronological_mediumScreen_collapsed() =
      runCollapsedTest("/gallery/chronological", ScreenWidth.MEDIUM)

  @Test
  fun chronological_mediumScreen_expanded() =
      runExpandedTest("/gallery/chronological", ScreenWidth.MEDIUM)

  @Test
  fun chronological_largeScreen_collapsed() =
      runCollapsedTest("/gallery/chronological", ScreenWidth.LARGE)

  @Test
  fun chronological_largeScreen_expanded() =
      runExpandedTest("/gallery/chronological", ScreenWidth.LARGE)

  @Test
  fun item_smallScreen() =
      runNonCollapsibleTest("/gallery/item/restructuring_2024", ScreenWidth.SMALL)

  @Test
  fun item_mediumScreen() =
      runNonCollapsibleTest("/gallery/item/restructuring_2024", ScreenWidth.MEDIUM)

  @Test
  fun item_largeScreen() =
      runNonCollapsibleTest("/gallery/item/restructuring_2024", ScreenWidth.LARGE)

  @Test
  fun series_smallScreen() =
      runNonCollapsibleTest("/gallery/series/genesis_ex_nihilo", ScreenWidth.SMALL)

  @Test
  fun series_mediumScreen() =
      runNonCollapsibleTest("/gallery/series/genesis_ex_nihilo", ScreenWidth.MEDIUM)

  @Test
  fun series_largeScreen() =
      runNonCollapsibleTest("/gallery/series/genesis_ex_nihilo", ScreenWidth.LARGE)

  companion object {
    /** The path to the golden files directory relative to the runfiles root. */
    private const val GOLDEN_BASE_PATH = "_main/first_party/site/tests/content/gallery/goldens"
  }
}
