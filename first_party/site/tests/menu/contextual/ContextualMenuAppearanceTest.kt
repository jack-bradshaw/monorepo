package com.jackbradshaw.site.tests.menu.contextual

import com.jackbradshaw.site.tests.MenuType
import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.menu.BaseMenuAppearanceTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ContextualMenuAppearanceTest :
    BaseMenuAppearanceTest(MenuType.CONTEXTUAL, ContextualMenuAppearanceTest.GOLDEN_BASE_PATH) {

  @Test
  fun galleryItem_smallScreen_collapsed() = runPopupCollapsedTest("/gallery/item/singularity_2025")

  @Test
  fun galleryItem_smallScreen_expanded_nextOnly() =
      runPopupExpandedTest("/gallery/item/nothing_2022")

  @Test
  fun galleryItem_smallScreen_expanded_prevOnly() =
      runPopupExpandedTest("/gallery/item/anything_2025")

  @Test
  fun galleryItem_smallScreen_expanded_nextAndPrev() =
      runPopupExpandedTest("/gallery/item/singularity_2025")

  @Test
  fun galleryItem_smallScreen_expanded_noNextOrPrev() =
      runPopupExpandedTest("/gallery/item/hungarian_notation_2022")

  @Test
  fun galleryItem_mediumScreen_nextOnly() =
      runAlwaysExpandedTest("/gallery/item/nothing_2022", ScreenWidth.MEDIUM)

  @Test
  fun galleryItem_mediumScreen_prevOnly() =
      runAlwaysExpandedTest("/gallery/item/anything_2025", ScreenWidth.MEDIUM)

  @Test
  fun galleryItem_mediumScreen_nextAndPrev() =
      runAlwaysExpandedTest("/gallery/item/singularity_2025", ScreenWidth.MEDIUM)

  @Test
  fun galleryItem_mediumScreen_noNextOrPrev() =
      runAlwaysExpandedTest("/gallery/item/hungarian_notation_2022", ScreenWidth.MEDIUM)

  @Test
  fun galleryItem_largeScreen_nextOnly() =
      runAlwaysExpandedTest("/gallery/item/nothing_2022", ScreenWidth.LARGE)

  @Test
  fun galleryItem_largeScreen_prevOnly() =
      runAlwaysExpandedTest("/gallery/item/anything_2025", ScreenWidth.LARGE)

  @Test
  fun galleryItem_largeScreen_nextAndPrev() =
      runAlwaysExpandedTest("/gallery/item/singularity_2025", ScreenWidth.LARGE)

  @Test
  fun galleryItem_largeScreen_noNextOrPrev() =
      runAlwaysExpandedTest("/gallery/item/hungarian_notation_2022", ScreenWidth.LARGE)

  @Test
  fun journalItem_smallScreen_collapsed() = runPopupCollapsedTest("/journal/item/into-the-subverse")

  @Test
  fun journalItem_smallScreen_expanded_nextOnly() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_smallScreen_expanded_prevOnly() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_smallScreen_expanded_nextAndPrev() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_smallScreen_expanded_noNextOrPrev() =
      runPopupExpandedTest("/journal/item/into-the-subverse")

  @Test
  fun journalItem_mediumScreen_nextOnly() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_mediumScreen_prevOnly() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_mediumScreen_nextAndPrev() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_mediumScreen_noNextOrPrev() =
      runAlwaysExpandedTest("/journal/item/into-the-subverse", ScreenWidth.MEDIUM)

  @Test
  fun journalItem_largeScreen_nextOnly() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_largeScreen_prevOnly() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_largeScreen_nextAndPrev() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test
  fun journalItem_largeScreen_noNextOrPrev() =
      runAlwaysExpandedTest("/journal/item/into-the-subverse", ScreenWidth.LARGE)

  companion object {
    /** The path to the golden files directory relative to the runfiles root. */
    private const val GOLDEN_BASE_PATH = "_main/first_party/site/tests/menu/contextual/goldens"
  }
}
