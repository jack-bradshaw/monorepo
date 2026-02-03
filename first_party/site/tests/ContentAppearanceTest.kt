package com.jackbradshaw.site.tests

import java.net.URI
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests the visual appearance of the site's content pages by screendiffing entire pages.
 *
 * The tests cover the following across a variety of screen sizes and section expansion states:
 * 
 * - Basic pages (Index, About).
 * - Gallery catalogue pages (Highlights, Chronological, etc).
 * - Journal catalogue pages (Highlights, Chronological, etc).
 * - Repository catalogue pages (Highlights, Technologies, etc).
 * - Representative sample of item/series pages (one of each type).
 * 
 * Caveats:
 * 
 * - These tests implicitly cover aspects that are covered in other tests explicitly (e.g. menu appearance test). The overlap ensures tests remain independent.
 * - These tests must be run via CI machine to cross-platform rendering differences (fonts, sub-pixel rendering).
 */
@RunWith(JUnit4::class)
class ContentAppearanceTest {

  @get:Rule val testCaseName = TestName()

  private val harness = TestHarness()

  @After
  fun tearDown() {
    harness.tearDown()
  }

  @Test fun indexPage_smallScreen_appearance() = runTest("/", ScreenWidth.SMALL)
  @Test fun indexPage_mediumScreen_appearance() = runTest("/", ScreenWidth.MEDIUM)
  @Test fun indexPage_largeScreen_appearance() = runTest("/", ScreenWidth.LARGE)

  @Test
  fun aboutPage_smallScreen_defaultExpansion_appearance() = runTest("/about", ScreenWidth.SMALL)

  @Test
  fun aboutPage_smallScreen_expandAll_appearance() =
      runTest("/about", ScreenWidth.SMALL, ExpansionState.EXPAND)

  @Test
  fun aboutPage_smallScreen_collapseAll_appearance() =
      runTest("/about", ScreenWidth.SMALL, ExpansionState.COLLAPSE)

  @Test
  fun aboutPage_mediumScreen_defaultExpansion_appearance() = runTest("/about", ScreenWidth.MEDIUM)

  @Test
  fun aboutPage_mediumScreen_expandAll_appearance() =
      runTest("/about", ScreenWidth.MEDIUM, ExpansionState.EXPAND)

  @Test
  fun aboutPage_mediumScreen_collapseAll_appearance() =
      runTest("/about", ScreenWidth.MEDIUM, ExpansionState.COLLAPSE)

  @Test
  fun aboutPage_largeScreen_defaultExpansion_appearance() = runTest("/about", ScreenWidth.LARGE)

  @Test
  fun aboutPage_largeScreen_expandAll_appearance() =
      runTest("/about", ScreenWidth.LARGE, ExpansionState.EXPAND)

  @Test
  fun aboutPage_largeScreen_collapseAll_appearance() =
      runTest("/about", ScreenWidth.LARGE, ExpansionState.COLLAPSE)

  @Test
  fun galleryHighlightsPage_smallScreen_appearance() =
      runTest("/gallery/highlights", ScreenWidth.SMALL)

  @Test
  fun galleryHighlightsPage_mediumScreen_appearance() =
      runTest("/gallery/highlights", ScreenWidth.MEDIUM)

  @Test
  fun galleryHighlightsPage_largeScreen_appearance() =
      runTest("/gallery/highlights", ScreenWidth.LARGE)

  @Test
  fun gallerySubjectsPage_smallScreen_defaultExpansion_appearance() =
      runTest("/gallery/subjects", ScreenWidth.SMALL)

  @Test
  fun gallerySubjectsPage_smallScreen_expandAll_appearance() =
      runTest("/gallery/subjects", ScreenWidth.SMALL, ExpansionState.EXPAND)

  @Test
  fun gallerySubjectsPage_smallScreen_collapseAll_appearance() =
      runTest("/gallery/subjects", ScreenWidth.SMALL, ExpansionState.COLLAPSE)

  @Test
  fun gallerySubjectsPage_mediumScreen_defaultExpansion_appearance() =
      runTest("/gallery/subjects", ScreenWidth.MEDIUM)

  @Test
  fun gallerySubjectsPage_mediumScreen_expandAll_appearance() =
      runTest("/gallery/subjects", ScreenWidth.MEDIUM, ExpansionState.EXPAND)

  @Test
  fun gallerySubjectsPage_mediumScreen_collapseAll_appearance() =
      runTest("/gallery/subjects", ScreenWidth.MEDIUM, ExpansionState.COLLAPSE)

  @Test
  fun gallerySubjectsPage_largeScreen_defaultExpansion_appearance() =
      runTest("/gallery/subjects", ScreenWidth.LARGE)

  @Test
  fun gallerySubjectsPage_largeScreen_expandAll_appearance() =
      runTest("/gallery/subjects", ScreenWidth.LARGE, ExpansionState.EXPAND)

  @Test
  fun gallerySubjectsPage_largeScreen_collapseAll_appearance() =
      runTest("/gallery/subjects", ScreenWidth.LARGE, ExpansionState.COLLAPSE)

  @Test
  fun galleryPalettesPage_smallScreen_defaultExpansion_appearance() =
      runTest("/gallery/palettes", ScreenWidth.SMALL)

  @Test
  fun galleryPalettesPage_smallScreen_expandAll_appearance() =
      runTest("/gallery/palettes", ScreenWidth.SMALL, ExpansionState.EXPAND)

  @Test
  fun galleryPalettesPage_smallScreen_collapseAll_appearance() =
      runTest("/gallery/palettes", ScreenWidth.SMALL, ExpansionState.COLLAPSE)

  @Test
  fun galleryPalettesPage_mediumScreen_defaultExpansion_appearance() =
      runTest("/gallery/palettes", ScreenWidth.MEDIUM)

  @Test
  fun galleryPalettesPage_mediumScreen_expandAll_appearance() =
      runTest("/gallery/palettes", ScreenWidth.MEDIUM, ExpansionState.EXPAND)

  @Test
  fun galleryPalettesPage_mediumScreen_collapseAll_appearance() =
      runTest("/gallery/palettes", ScreenWidth.MEDIUM, ExpansionState.COLLAPSE)

  @Test fun galleryPalettesPage_largeScreen_defaultExpansion_appearance() =
      runTest("/gallery/palettes", ScreenWidth.LARGE)

  @Test fun galleryPalettesPage_largeScreen_expandAll_appearance() =
      runTest("/gallery/palettes", ScreenWidth.LARGE, ExpansionState.EXPAND)

  @Test fun galleryPalettesPage_largeScreen_collapseAll_appearance() =
      runTest("/gallery/palettes", ScreenWidth.LARGE, ExpansionState.COLLAPSE)

  @Test
  fun galleryChronologicalPage_smallScreen_defaultExpansion_appearance() =
      runTest("/gallery/chronological", ScreenWidth.SMALL)

  @Test
  fun galleryChronologicalPage_smallScreen_expandAll_appearance() =
      runTest("/gallery/chronological", ScreenWidth.SMALL, ExpansionState.EXPAND)

  @Test
  fun galleryChronologicalPage_smallScreen_collapseAll_appearance() =
      runTest("/gallery/chronological", ScreenWidth.SMALL, ExpansionState.COLLAPSE)

  @Test
  fun galleryChronologicalPage_mediumScreen_defaultExpansion_appearance() =
      runTest("/gallery/chronological", ScreenWidth.MEDIUM)

  @Test
  fun galleryChronologicalPage_mediumScreen_expandAll_appearance() =
      runTest("/gallery/chronological", ScreenWidth.MEDIUM, ExpansionState.EXPAND)

  @Test
  fun galleryChronologicalPage_mediumScreen_collapseAll_appearance() =
      runTest("/gallery/chronological", ScreenWidth.MEDIUM, ExpansionState.COLLAPSE)

  @Test
  fun galleryChronologicalPage_largeScreen_defaultExpansion_appearance() =
      runTest("/gallery/chronological", ScreenWidth.LARGE)

  @Test
  fun galleryChronologicalPage_largeScreen_expandAll_appearance() =
      runTest("/gallery/chronological", ScreenWidth.LARGE, ExpansionState.EXPAND)

  @Test
  fun galleryChronologicalPage_largeScreen_collapseAll_appearance() =
      runTest("/gallery/chronological", ScreenWidth.LARGE, ExpansionState.COLLAPSE)

  @Test
  fun journalHighlightsPage_smallScreen_appearance() =
      runTest("/journal/highlights", ScreenWidth.SMALL)

  @Test
  fun journalHighlightsPage_mediumScreen_appearance() =
      runTest("/journal/highlights", ScreenWidth.MEDIUM)

  @Test
  fun journalHighlightsPage_largeScreen_appearance() =
      runTest("/journal/highlights", ScreenWidth.LARGE)

  @Test
  fun journalTopicsPage_smallScreen_defaultExpansion_appearance() =
      runTest("/journal/topics", ScreenWidth.SMALL)

  @Test
  fun journalTopicsPage_smallScreen_expandAll_appearance() =
      runTest("/journal/topics", ScreenWidth.SMALL, ExpansionState.EXPAND)

  @Test
  fun journalTopicsPage_smallScreen_collapseAll_appearance() =
      runTest("/journal/topics", ScreenWidth.SMALL, ExpansionState.COLLAPSE)

  @Test
  fun journalTopicsPage_mediumScreen_defaultExpansion_appearance() =
      runTest("/journal/topics", ScreenWidth.MEDIUM)

  @Test
  fun journalTopicsPage_mediumScreen_expandAll_appearance() =
      runTest("/journal/topics", ScreenWidth.MEDIUM, ExpansionState.EXPAND)

  @Test
  fun journalTopicsPage_mediumScreen_collapseAll_appearance() =
      runTest("/journal/topics", ScreenWidth.MEDIUM, ExpansionState.COLLAPSE)

  @Test
  fun journalTopicsPage_largeScreen_defaultExpansion_appearance() =
      runTest("/journal/topics", ScreenWidth.LARGE)

  @Test
  fun journalTopicsPage_largeScreen_expandAll_appearance() =
      runTest("/journal/topics", ScreenWidth.LARGE, ExpansionState.EXPAND)

  @Test
  fun journalTopicsPage_largeScreen_collapseAll_appearance() =
      runTest("/journal/topics", ScreenWidth.LARGE, ExpansionState.COLLAPSE)

  @Test
  fun journalSeriesPage_smallScreen_defaultExpansion_appearance() =
      runTest("/journal/series", ScreenWidth.SMALL)

  @Test
  fun journalSeriesPage_smallScreen_expandAll_appearance() =
      runTest("/journal/series", ScreenWidth.SMALL, ExpansionState.EXPAND)

  @Test
  fun journalSeriesPage_smallScreen_collapseAll_appearance() =
      runTest("/journal/series", ScreenWidth.SMALL, ExpansionState.COLLAPSE)

  @Test
  fun journalSeriesPage_mediumScreen_defaultExpansion_appearance() =
      runTest("/journal/series", ScreenWidth.MEDIUM)

  @Test
  fun journalSeriesPage_mediumScreen_expandAll_appearance() =
      runTest("/journal/series", ScreenWidth.MEDIUM, ExpansionState.EXPAND)

  @Test
  fun journalSeriesPage_mediumScreen_collapseAll_appearance() =
      runTest("/journal/series", ScreenWidth.MEDIUM, ExpansionState.COLLAPSE)

  @Test
  fun journalSeriesPage_largeScreen_defaultExpansion_appearance() =
      runTest("/journal/series", ScreenWidth.LARGE)

  @Test
  fun journalSeriesPage_largeScreen_expandAll_appearance() =
      runTest("/journal/series", ScreenWidth.LARGE, ExpansionState.EXPAND)

  @Test
  fun journalSeriesPage_largeScreen_collapseAll_appearance() =
      runTest("/journal/series", ScreenWidth.LARGE, ExpansionState.COLLAPSE)

  @Test
  fun journalGenresPage_smallScreen_defaultExpansion_appearance() =
      runTest("/journal/genres", ScreenWidth.SMALL)

  @Test
  fun journalGenresPage_smallScreen_expandAll_appearance() =
      runTest("/journal/genres", ScreenWidth.SMALL, ExpansionState.EXPAND)

  @Test
  fun journalGenresPage_smallScreen_collapseAll_appearance() =
      runTest("/journal/genres", ScreenWidth.SMALL, ExpansionState.COLLAPSE)

  @Test
  fun journalGenresPage_mediumScreen_defaultExpansion_appearance() =
      runTest("/journal/genres", ScreenWidth.MEDIUM)

  @Test
  fun journalGenresPage_mediumScreen_expandAll_appearance() =
      runTest("/journal/genres", ScreenWidth.MEDIUM, ExpansionState.EXPAND)

  @Test
  fun journalGenresPage_mediumScreen_collapseAll_appearance() =
      runTest("/journal/genres", ScreenWidth.MEDIUM, ExpansionState.COLLAPSE)

  @Test
  fun journalGenresPage_largeScreen_defaultExpansion_appearance() =
      runTest("/journal/genres", ScreenWidth.LARGE)

  @Test
  fun journalGenresPage_largeScreen_expandAll_appearance() =
      runTest("/journal/genres", ScreenWidth.LARGE, ExpansionState.EXPAND)

  @Test
  fun journalGenresPage_largeScreen_collapseAll_appearance() =
      runTest("/journal/genres", ScreenWidth.LARGE, ExpansionState.COLLAPSE)

  @Test
  fun journalChronologicalPage_smallScreen_defaultExpansion_appearance() =
      runTest("/journal/chronological", ScreenWidth.SMALL)

  @Test
  fun journalChronologicalPage_smallScreen_expandAll_appearance() =
      runTest("/journal/chronological", ScreenWidth.SMALL, ExpansionState.EXPAND)

  @Test
  fun journalChronologicalPage_smallScreen_collapseAll_appearance() =
      runTest("/journal/chronological", ScreenWidth.SMALL, ExpansionState.COLLAPSE)

  @Test
  fun journalChronologicalPage_mediumScreen_defaultExpansion_appearance() =
      runTest("/journal/chronological", ScreenWidth.MEDIUM)

  @Test
  fun journalChronologicalPage_mediumScreen_expandAll_appearance() =
      runTest("/journal/chronological", ScreenWidth.MEDIUM, ExpansionState.EXPAND)

  @Test
  fun journalChronologicalPage_mediumScreen_collapseAll_appearance() =
      runTest("/journal/chronological", ScreenWidth.MEDIUM, ExpansionState.COLLAPSE)

  @Test
  fun journalChronologicalPage_largeScreen_defaultExpansion_appearance() =
      runTest("/journal/chronological", ScreenWidth.LARGE)

  @Test
  fun journalChronologicalPage_largeScreen_expandAll_appearance() =
      runTest("/journal/chronological", ScreenWidth.LARGE, ExpansionState.EXPAND)

  @Test
  fun journalChronologicalPage_largeScreen_collapseAll_appearance() =
      runTest("/journal/chronological", ScreenWidth.LARGE, ExpansionState.COLLAPSE)

  @Test
  fun repositoryHighlightsPage_smallScreen_appearance() =
      runTest("/repository/highlights", ScreenWidth.SMALL)

  @Test
  fun repositoryHighlightsPage_mediumScreen_appearance() =
      runTest("/repository/highlights", ScreenWidth.MEDIUM)

  @Test
  fun repositoryHighlightsPage_largeScreen_appearance() =
      runTest("/repository/highlights", ScreenWidth.LARGE)

  @Test
  fun repositoryLocationsPage_smallScreen_defaultExpansion_appearance() =
      runTest("/repository/locations", ScreenWidth.SMALL)

  @Test
  fun repositoryLocationsPage_smallScreen_expandAll_appearance() =
      runTest("/repository/locations", ScreenWidth.SMALL, ExpansionState.EXPAND)

  @Test
  fun repositoryLocationsPage_smallScreen_collapseAll_appearance() =
      runTest("/repository/locations", ScreenWidth.SMALL, ExpansionState.COLLAPSE)

  @Test
  fun repositoryLocationsPage_mediumScreen_defaultExpansion_appearance() =
      runTest("/repository/locations", ScreenWidth.MEDIUM)

  @Test
  fun repositoryLocationsPage_mediumScreen_expandAll_appearance() =
      runTest("/repository/locations", ScreenWidth.MEDIUM, ExpansionState.EXPAND)

  @Test
  fun repositoryLocationsPage_mediumScreen_collapseAll_appearance() =
      runTest("/repository/locations", ScreenWidth.MEDIUM, ExpansionState.COLLAPSE)

  @Test
  fun repositoryLocationsPage_largeScreen_defaultExpansion_appearance() =
      runTest("/repository/locations", ScreenWidth.LARGE)

  @Test
  fun repositoryLocationsPage_largeScreen_expandAll_appearance() =
      runTest("/repository/locations", ScreenWidth.LARGE, ExpansionState.EXPAND)

  @Test
  fun repositoryLocationsPage_largeScreen_collapseAll_appearance() =
      runTest("/repository/locations", ScreenWidth.LARGE, ExpansionState.COLLAPSE)

  @Test
  fun repositoryTechnologiesPage_smallScreen_defaultExpansion_appearance() =
      runTest("/repository/technologies", ScreenWidth.SMALL)

  @Test
  fun repositoryTechnologiesPage_smallScreen_expandAll_appearance() =
      runTest("/repository/technologies", ScreenWidth.SMALL, ExpansionState.EXPAND)

  @Test
  fun repositoryTechnologiesPage_smallScreen_collapseAll_appearance() =
      runTest("/repository/technologies", ScreenWidth.SMALL, ExpansionState.COLLAPSE)

  @Test
  fun repositoryTechnologiesPage_mediumScreen_defaultExpansion_appearance() =
      runTest("/repository/technologies", ScreenWidth.MEDIUM)

  @Test
  fun repositoryTechnologiesPage_mediumScreen_expandAll_appearance() =
      runTest("/repository/technologies", ScreenWidth.MEDIUM, ExpansionState.EXPAND)

  @Test
  fun repositoryTechnologiesPage_mediumScreen_collapseAll_appearance() =
      runTest("/repository/technologies", ScreenWidth.MEDIUM, ExpansionState.COLLAPSE)

  @Test
  fun repositoryTechnologiesPage_largeScreen_defaultExpansion_appearance() =
      runTest("/repository/technologies", ScreenWidth.LARGE)

  @Test
  fun repositoryTechnologiesPage_largeScreen_expandAll_appearance() =
      runTest("/repository/technologies", ScreenWidth.LARGE, ExpansionState.EXPAND)

  @Test
  fun repositoryTechnologiesPage_largeScreen_collapseAll_appearance() =
      runTest("/repository/technologies", ScreenWidth.LARGE, ExpansionState.COLLAPSE)

  @Test
  fun galleryItem_restructuring_smallScreen_appearance() =
      runTest("/gallery/item/restructuring_2024", ScreenWidth.SMALL)

  @Test
  fun galleryItem_restructuring_mediumScreen_appearance() =
      runTest("/gallery/item/restructuring_2024", ScreenWidth.MEDIUM)

  @Test
  fun galleryItem_restructuring_largeScreen_appearance() =
      runTest("/gallery/item/restructuring_2024", ScreenWidth.LARGE)

  @Test
  fun gallerySeries_genesis_smallScreen_appearance() =
      runTest("/gallery/series/genesis_ex_nihilo", ScreenWidth.SMALL)

  @Test
  fun gallerySeries_genesis_mediumScreen_appearance() =
      runTest("/gallery/series/genesis_ex_nihilo", ScreenWidth.MEDIUM)

  @Test
  fun gallerySeries_genesis_largeScreen_appearance() =
      runTest("/gallery/series/genesis_ex_nihilo", ScreenWidth.LARGE)

  @Test
  fun journalItem_subverse_smallScreen_appearance() =
      runTest("/journal/item/into-the-subverse", ScreenWidth.SMALL)

  @Test
  fun journalItem_subverse_mediumScreen_appearance() =
      runTest("/journal/item/into-the-subverse", ScreenWidth.MEDIUM)

  @Test
  fun journalItem_subverse_largeScreen_appearance() =
      runTest("/journal/item/into-the-subverse", ScreenWidth.LARGE)

  @Test
  fun careerItem_waymo_smallScreen_appearance() =
      runTest("/about/career/item/waymo", ScreenWidth.SMALL)

  @Test
  fun careerItem_waymo_mediumScreen_appearance() =
      runTest("/about/career/item/waymo", ScreenWidth.MEDIUM)

  @Test
  fun careerItem_waymo_largeScreen_appearance() =
      runTest("/about/career/item/waymo", ScreenWidth.LARGE)
  // endregion

  private fun runTest(
      path: String,
      size: ScreenWidth,
      expansionState: ExpansionState = ExpansionState.DEFAULT
  ) {
    harness.setup(size)
    val page = harness.openPage(URI.create(path)).also { it.waitForLoad() }

    when (expansionState) {
      ExpansionState.EXPAND -> page.expandAllDetailsContentBlocks()
      ExpansionState.COLLAPSE -> page.collapseAllDetailsContentBlocks()
      ExpansionState.DEFAULT -> {
        /* Default state */
      }
    }
    page.waitForLoad()

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.png"
    harness.checkScreendiff(page, goldenName)
  }

  enum class ExpansionState {
    DEFAULT,
    EXPAND,
    COLLAPSE
  }
}
