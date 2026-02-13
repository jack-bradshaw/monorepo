package com.jackbradshaw.site.tests.menu.primary

import com.jackbradshaw.site.tests.MenuType
import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.menu.BaseMenuAppearanceTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PrimaryMenuAppearanceTest :
    BaseMenuAppearanceTest(MenuType.PRIMARY, PrimaryMenuAppearanceTest.GOLDEN_BASE_PATH) {

  @Test fun index_smallScreen_collapsed() = runPopupCollapsedTest("/")

  @Test fun index_smallScreen_expanded() = runPopupExpandedTest("/")

  @Test fun index_mediumScreen() = runAlwaysExpandedTest("/", ScreenWidth.MEDIUM)

  @Test fun index_largeScreen() = runAlwaysExpandedTest("/", ScreenWidth.LARGE)

  @Test fun about_smallScreen_collapsed() = runPopupCollapsedTest("/about")

  @Test fun about_smallScreen_expanded() = runPopupExpandedTest("/about")

  @Test fun about_mediumScreen() = runAlwaysExpandedTest("/about", ScreenWidth.MEDIUM)

  @Test fun about_largeScreen() = runAlwaysExpandedTest("/about", ScreenWidth.LARGE)

  @Test
  fun aboutCareerItem_smallScreen_collapsed() = runPopupCollapsedTest("/about/career/item/waymo")

  @Test
  fun aboutCareerItem_smallScreen_expanded() = runPopupExpandedTest("/about/career/item/waymo")

  @Test
  fun aboutCareerItem_mediumScreen() =
      runAlwaysExpandedTest("/about/career/item/waymo", ScreenWidth.MEDIUM)

  @Test
  fun aboutCareerItem_largeScreen() =
      runAlwaysExpandedTest("/about/career/item/waymo", ScreenWidth.LARGE)

  @Test fun galleryHighlights_smallScreen_collapsed() = runPopupCollapsedTest("/gallery/highlights")

  @Test fun galleryHighlights_smallScreen_expanded() = runPopupExpandedTest("/gallery/highlights")

  @Test
  fun galleryHighlights_mediumScreen() =
      runAlwaysExpandedTest("/gallery/highlights", ScreenWidth.MEDIUM)

  @Test
  fun galleryHighlights_largeScreen() =
      runAlwaysExpandedTest("/gallery/highlights", ScreenWidth.LARGE)

  @Test fun gallerySubjects_smallScreen_collapsed() = runPopupCollapsedTest("/gallery/subjects")

  @Test fun gallerySubjects_smallScreen_expanded() = runPopupExpandedTest("/gallery/subjects")

  @Test
  fun gallerySubjects_mediumScreen() =
      runAlwaysExpandedTest("/gallery/subjects", ScreenWidth.MEDIUM)

  @Test
  fun gallerySubjects_largeScreen() = runAlwaysExpandedTest("/gallery/subjects", ScreenWidth.LARGE)

  @Test
  fun galleryPalettes_smallScreen_collapsed() =
      runPopupCollapsedTest("/gallery/palettes", captureFullPage = false)

  @Test
  fun galleryPalettes_smallScreen_expanded() =
      runPopupExpandedTest("/gallery/palettes", captureFullPage = false)

  @Test
  fun galleryPalettes_mediumScreen() =
      runAlwaysExpandedTest("/gallery/palettes", ScreenWidth.MEDIUM)

  @Test
  fun galleryPalettes_largeScreen() = runAlwaysExpandedTest("/gallery/palettes", ScreenWidth.LARGE)

  @Test
  fun galleryChronological_smallScreen_collapsed() =
      runPopupCollapsedTest("/gallery/chronological", captureFullPage = false)

  @Test
  fun galleryChronological_smallScreen_expanded() =
      runPopupExpandedTest("/gallery/chronological", captureFullPage = false)

  @Test
  fun galleryChronological_mediumScreen() =
      runAlwaysExpandedTest("/gallery/chronological", ScreenWidth.MEDIUM)

  @Test
  fun galleryChronological_largeScreen() =
      runAlwaysExpandedTest("/gallery/chronological", ScreenWidth.LARGE)

  @Test
  fun galleryItem_smallScreen_collapsed() =
      runPopupCollapsedTest("/gallery/item/restructuring_2024")

  @Test
  fun galleryItem_smallScreen_expanded() = runPopupExpandedTest("/gallery/item/restructuring_2024")

  @Test
  fun galleryItem_mediumScreen() =
      runAlwaysExpandedTest("/gallery/item/restructuring_2024", ScreenWidth.MEDIUM)

  @Test
  fun galleryItem_largeScreen() =
      runAlwaysExpandedTest("/gallery/item/restructuring_2024", ScreenWidth.LARGE)

  @Test
  fun gallerySeries_smallScreen_collapsed() =
      runPopupCollapsedTest("/gallery/series/genesis_ex_nihilo")

  @Test
  fun gallerySeries_smallScreen_expanded() =
      runPopupExpandedTest("/gallery/series/genesis_ex_nihilo")

  @Test
  fun gallerySeries_mediumScreen() =
      runAlwaysExpandedTest("/gallery/series/genesis_ex_nihilo", ScreenWidth.MEDIUM)

  @Test
  fun gallerySeries_largeScreen() =
      runAlwaysExpandedTest("/gallery/series/genesis_ex_nihilo", ScreenWidth.LARGE)

  @Test fun journalHighlights_smallScreen_collapsed() = runPopupCollapsedTest("/journal/highlights")

  @Test fun journalHighlights_smallScreen_expanded() = runPopupExpandedTest("/journal/highlights")

  @Test
  fun journalHighlights_mediumScreen() =
      runAlwaysExpandedTest("/journal/highlights", ScreenWidth.MEDIUM)

  @Test
  fun journalHighlights_largeScreen() =
      runAlwaysExpandedTest("/journal/highlights", ScreenWidth.LARGE)

  @Test fun journalTopics_smallScreen_collapsed() = runPopupCollapsedTest("/journal/topics")

  @Test fun journalTopics_smallScreen_expanded() = runPopupExpandedTest("/journal/topics")

  @Test
  fun journalTopics_mediumScreen() = runAlwaysExpandedTest("/journal/topics", ScreenWidth.MEDIUM)

  @Test
  fun journalTopics_largeScreen() = runAlwaysExpandedTest("/journal/topics", ScreenWidth.LARGE)

  @Test fun journalSeries_smallScreen_collapsed() = runPopupCollapsedTest("/journal/serieslist")

  @Test fun journalSeries_smallScreen_expanded() = runPopupExpandedTest("/journal/serieslist")

  @Test
  fun journalSeries_mediumScreen() =
      runAlwaysExpandedTest("/journal/serieslist", ScreenWidth.MEDIUM)

  @Test
  fun journalSeries_largeScreen() = runAlwaysExpandedTest("/journal/serieslist", ScreenWidth.LARGE)

  @Test fun journalGenres_smallScreen_collapsed() = runPopupCollapsedTest("/journal/genres")

  @Test fun journalGenres_smallScreen_expanded() = runPopupExpandedTest("/journal/genres")

  @Test
  fun journalGenres_mediumScreen() = runAlwaysExpandedTest("/journal/genres", ScreenWidth.MEDIUM)

  @Test
  fun journalGenres_largeScreen() = runAlwaysExpandedTest("/journal/genres", ScreenWidth.LARGE)

  @Test
  fun journalChronological_smallScreen_collapsed() = runPopupCollapsedTest("/journal/chronological")

  @Test
  fun journalChronological_smallScreen_expanded() = runPopupExpandedTest("/journal/chronological")

  @Test
  fun journalChronological_mediumScreen() =
      runAlwaysExpandedTest("/journal/chronological", ScreenWidth.MEDIUM)

  @Test
  fun journalChronological_largeScreen() =
      runAlwaysExpandedTest("/journal/chronological", ScreenWidth.LARGE)

  @Test
  fun journalItem_smallScreen_collapsed() = runPopupCollapsedTest("/journal/item/into-the-subverse")

  @Test
  fun journalItem_smallScreen_expanded() = runPopupExpandedTest("/journal/item/into-the-subverse")

  @Test
  fun journalItem_mediumScreen() =
      runAlwaysExpandedTest("/journal/item/into-the-subverse", ScreenWidth.MEDIUM)

  @Test
  fun journalItem_largeScreen() =
      runAlwaysExpandedTest("/journal/item/into-the-subverse", ScreenWidth.LARGE)

  @Test
  fun repositoryHighlights_smallScreen_collapsed() = runPopupCollapsedTest("/repository/highlights")

  @Test
  fun repositoryHighlights_smallScreen_expanded() = runPopupExpandedTest("/repository/highlights")

  @Test
  fun repositoryHighlights_mediumScreen() =
      runAlwaysExpandedTest("/repository/highlights", ScreenWidth.MEDIUM)

  @Test
  fun repositoryHighlights_largeScreen() =
      runAlwaysExpandedTest("/repository/highlights", ScreenWidth.LARGE)

  @Test
  fun repositoryLocations_smallScreen_collapsed() = runPopupCollapsedTest("/repository/locations")

  @Test
  fun repositoryLocations_smallScreen_expanded() = runPopupExpandedTest("/repository/locations")

  @Test
  fun repositoryLocations_mediumScreen() =
      runAlwaysExpandedTest("/repository/locations", ScreenWidth.MEDIUM)

  @Test
  fun repositoryLocations_largeScreen() =
      runAlwaysExpandedTest("/repository/locations", ScreenWidth.LARGE)

  @Test
  fun repositoryTechnologies_smallScreen_collapsed() =
      runPopupCollapsedTest("/repository/technologies")

  @Test
  fun repositoryTechnologies_smallScreen_expanded() =
      runPopupExpandedTest("/repository/technologies")

  @Test
  fun repositoryTechnologies_mediumScreen() =
      runAlwaysExpandedTest("/repository/technologies", ScreenWidth.MEDIUM)

  @Test
  fun repositoryTechnologies_largeScreen() =
      runAlwaysExpandedTest("/repository/technologies", ScreenWidth.LARGE)

  companion object {
    /** The path to the golden files directory relative to the runfiles root. */
    private const val GOLDEN_BASE_PATH = "_main/first_party/site/tests/menu/primary/goldens"
  }
}
