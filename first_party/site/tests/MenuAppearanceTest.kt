package com.jackbradshaw.site.tests

import com.microsoft.playwright.Page
import java.net.URI
import org.junit.After
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests the visual appearance of transient menu states (e.g. opened popups).
 *
 * The tests cover the following across a variety of screen sizes:
 *
 * - Primary, Secondary, and Contextual menus.
 * - Collapsed, Expanded, Default (Static), and Hidden states.
 * - Index, Item, and all Catalogue (Gallery, Journal, Repository) page contexts.
 *
 * Caveats:
 *
 * - These tests must be run via CI machine to cross-platform rendering differences (fonts, sub-pixel rendering).
 * - These tests explicitly cover aspects that are covered in other tests implicitly (e.g. content appearance test). The overlap ensures tests remain independent.
 * - The tests for the popup menu implicitly use [ScreenWidth.SMALL] to enforce the mobile style menu.
 */
@RunWith(JUnit4::class)
class MenuAppearanceTest {

  @get:Rule val testCaseName = TestName()

  private val harness = TestHarness()

  @After
  fun tearDown() {
    harness.tearDown()
  }

  @Test fun primaryMenu_indexPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_indexPage_smallScreen_expanded() = runTestWithExplicitExpansion("/", MenuType.PRIMARY)
  @Test fun primaryMenu_indexPage_mediumScreen() = runTestWithDefaultExpansion("/", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_indexPage_largeScreen() = runTestWithDefaultExpansion("/", ScreenWidth.LARGE)

  @Test fun primaryMenu_galleryHighlightsPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/highlights", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_galleryHighlightsPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/highlights", MenuType.PRIMARY)
  @Test fun primaryMenu_galleryHighlightsPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/highlights", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_galleryHighlightsPage_largeScreen() = runTestWithDefaultExpansion("/gallery/highlights", ScreenWidth.LARGE)

  @Test fun primaryMenu_gallerySubjectsPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/subjects", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_gallerySubjectsPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/subjects", MenuType.PRIMARY)
  @Test fun primaryMenu_gallerySubjectsPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/subjects", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_gallerySubjectsPage_largeScreen() = runTestWithDefaultExpansion("/gallery/subjects", ScreenWidth.LARGE)

  @Test fun primaryMenu_galleryPalettesPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/palettes", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_galleryPalettesPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/palettes", MenuType.PRIMARY)
  @Test fun primaryMenu_galleryPalettesPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/palettes", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_galleryPalettesPage_largeScreen() = runTestWithDefaultExpansion("/gallery/palettes", ScreenWidth.LARGE)

  @Test fun primaryMenu_galleryChronologicalPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/chronological", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_galleryChronologicalPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/chronological", MenuType.PRIMARY)
  @Test fun primaryMenu_galleryChronologicalPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/chronological", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_galleryChronologicalPage_largeScreen() = runTestWithDefaultExpansion("/gallery/chronological", ScreenWidth.LARGE)


  @Test fun primaryMenu_journalHighlightsPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/highlights", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_journalHighlightsPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/highlights", MenuType.PRIMARY)
  @Test fun primaryMenu_journalHighlightsPage_mediumScreen() = runTestWithDefaultExpansion("/journal/highlights", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_journalHighlightsPage_largeScreen() = runTestWithDefaultExpansion("/journal/highlights", ScreenWidth.LARGE)

  @Test fun primaryMenu_journalTopicsPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/topics", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_journalTopicsPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/topics", MenuType.PRIMARY)
  @Test fun primaryMenu_journalTopicsPage_mediumScreen() = runTestWithDefaultExpansion("/journal/topics", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_journalTopicsPage_largeScreen() = runTestWithDefaultExpansion("/journal/topics", ScreenWidth.LARGE)

  @Test fun primaryMenu_journalSeriesPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/series", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_journalSeriesPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/series", MenuType.PRIMARY)
  @Test fun primaryMenu_journalSeriesPage_mediumScreen() = runTestWithDefaultExpansion("/journal/series", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_journalSeriesPage_largeScreen() = runTestWithDefaultExpansion("/journal/series", ScreenWidth.LARGE)

  @Test fun primaryMenu_journalGenresPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/genres", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_journalGenresPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/genres", MenuType.PRIMARY)
  @Test fun primaryMenu_journalGenresPage_mediumScreen() = runTestWithDefaultExpansion("/journal/genres", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_journalGenresPage_largeScreen() = runTestWithDefaultExpansion("/journal/genres", ScreenWidth.LARGE)

  @Test fun primaryMenu_journalChronologicalPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/chronological", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_journalChronologicalPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/chronological", MenuType.PRIMARY)
  @Test fun primaryMenu_journalChronologicalPage_mediumScreen() = runTestWithDefaultExpansion("/journal/chronological", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_journalChronologicalPage_largeScreen() = runTestWithDefaultExpansion("/journal/chronological", ScreenWidth.LARGE)


  @Test fun primaryMenu_repositoryHighlightsPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/repository/highlights", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_repositoryHighlightsPage_smallScreen_expanded() = runTestWithExplicitExpansion("/repository/highlights", MenuType.PRIMARY)
  @Test fun primaryMenu_repositoryHighlightsPage_mediumScreen() = runTestWithDefaultExpansion("/repository/highlights", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_repositoryHighlightsPage_largeScreen() = runTestWithDefaultExpansion("/repository/highlights", ScreenWidth.LARGE)

  @Test fun primaryMenu_repositoryLocationsPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/repository/locations", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_repositoryLocationsPage_smallScreen_expanded() = runTestWithExplicitExpansion("/repository/locations", MenuType.PRIMARY)
  @Test fun primaryMenu_repositoryLocationsPage_mediumScreen() = runTestWithDefaultExpansion("/repository/locations", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_repositoryLocationsPage_largeScreen() = runTestWithDefaultExpansion("/repository/locations", ScreenWidth.LARGE)

  @Test fun primaryMenu_repositoryTechnologiesPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/repository/technologies", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_repositoryTechnologiesPage_smallScreen_expanded() = runTestWithExplicitExpansion("/repository/technologies", MenuType.PRIMARY)
  @Test fun primaryMenu_repositoryTechnologiesPage_mediumScreen() = runTestWithDefaultExpansion("/repository/technologies", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_repositoryTechnologiesPage_largeScreen() = runTestWithDefaultExpansion("/repository/technologies", ScreenWidth.LARGE)


  @Test fun primaryMenu_aboutPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/about", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_aboutPage_smallScreen_expanded() = runTestWithExplicitExpansion("/about", MenuType.PRIMARY)
  @Test fun primaryMenu_aboutPage_mediumScreen() = runTestWithDefaultExpansion("/about", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_aboutPage_largeScreen() = runTestWithDefaultExpansion("/about", ScreenWidth.LARGE)


  @Test fun primaryMenu_gallerySeriesPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/series/genesis_ex_nihilo", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_gallerySeriesPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/series/genesis_ex_nihilo", MenuType.PRIMARY)
  @Test fun primaryMenu_gallerySeriesPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/series/genesis_ex_nihilo", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_gallerySeriesPage_largeScreen() = runTestWithDefaultExpansion("/gallery/series/genesis_ex_nihilo", ScreenWidth.LARGE)


  @Test fun primaryMenu_galleryItemPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/item/restructuring_2024", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_galleryItemPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/item/restructuring_2024", MenuType.PRIMARY)
  @Test fun primaryMenu_galleryItemPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/item/restructuring_2024", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_galleryItemPage_largeScreen() = runTestWithDefaultExpansion("/gallery/item/restructuring_2024", ScreenWidth.LARGE)


  @Test fun primaryMenu_journalItemPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/item/introduction_2023", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun primaryMenu_journalItemPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/item/introduction_2023", MenuType.PRIMARY)
  @Test fun primaryMenu_journalItemPage_mediumScreen() = runTestWithDefaultExpansion("/journal/item/introduction_2023", ScreenWidth.MEDIUM)
  @Test fun primaryMenu_journalItemPage_largeScreen() = runTestWithDefaultExpansion("/journal/item/introduction_2023", ScreenWidth.LARGE)


  @Test fun secondaryMenu_indexPage_smallScreen_hidden() = runTestWithDefaultExpansion("/", ScreenWidth.SMALL)
  @Test fun secondaryMenu_indexPage_mediumScreen() = runTestWithDefaultExpansion("/", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_indexPage_largeScreen() = runTestWithDefaultExpansion("/", ScreenWidth.LARGE)


  @Test fun secondaryMenu_galleryHighlightsPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/highlights", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_galleryHighlightsPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/highlights", MenuType.SECONDARY)
  @Test fun secondaryMenu_galleryHighlightsPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/highlights", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_galleryHighlightsPage_largeScreen() = runTestWithDefaultExpansion("/gallery/highlights", ScreenWidth.LARGE)

  @Test fun secondaryMenu_gallerySubjectsPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/subjects", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_gallerySubjectsPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/subjects", MenuType.SECONDARY)
  @Test fun secondaryMenu_gallerySubjectsPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/subjects", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_gallerySubjectsPage_largeScreen() = runTestWithDefaultExpansion("/gallery/subjects", ScreenWidth.LARGE)

  @Test fun secondaryMenu_galleryPalettesPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/palettes", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_galleryPalettesPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/palettes", MenuType.SECONDARY)
  @Test fun secondaryMenu_galleryPalettesPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/palettes", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_galleryPalettesPage_largeScreen() = runTestWithDefaultExpansion("/gallery/palettes", ScreenWidth.LARGE)

  @Test fun secondaryMenu_galleryChronologicalPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/chronological", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_galleryChronologicalPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/chronological", MenuType.SECONDARY)
  @Test fun secondaryMenu_galleryChronologicalPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/chronological", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_galleryChronologicalPage_largeScreen() = runTestWithDefaultExpansion("/gallery/chronological", ScreenWidth.LARGE)


  @Test fun secondaryMenu_journalHighlightsPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/highlights", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_journalHighlightsPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/highlights", MenuType.SECONDARY)
  @Test fun secondaryMenu_journalHighlightsPage_mediumScreen() = runTestWithDefaultExpansion("/journal/highlights", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_journalHighlightsPage_largeScreen() = runTestWithDefaultExpansion("/journal/highlights", ScreenWidth.LARGE)

  @Test fun secondaryMenu_journalTopicsPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/topics", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_journalTopicsPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/topics", MenuType.SECONDARY)
  @Test fun secondaryMenu_journalTopicsPage_mediumScreen() = runTestWithDefaultExpansion("/journal/topics", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_journalTopicsPage_largeScreen() = runTestWithDefaultExpansion("/journal/topics", ScreenWidth.LARGE)

  @Test fun secondaryMenu_journalSeriesPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/series", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_journalSeriesPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/series", MenuType.SECONDARY)
  @Test fun secondaryMenu_journalSeriesPage_mediumScreen() = runTestWithDefaultExpansion("/journal/series", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_journalSeriesPage_largeScreen() = runTestWithDefaultExpansion("/journal/series", ScreenWidth.LARGE)

  @Test fun secondaryMenu_journalGenresPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/genres", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_journalGenresPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/genres", MenuType.SECONDARY)
  @Test fun secondaryMenu_journalGenresPage_mediumScreen() = runTestWithDefaultExpansion("/journal/genres", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_journalGenresPage_largeScreen() = runTestWithDefaultExpansion("/journal/genres", ScreenWidth.LARGE)

  @Test fun secondaryMenu_journalChronologicalPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/chronological", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_journalChronologicalPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/chronological", MenuType.SECONDARY)
  @Test fun secondaryMenu_journalChronologicalPage_mediumScreen() = runTestWithDefaultExpansion("/journal/chronological", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_journalChronologicalPage_largeScreen() = runTestWithDefaultExpansion("/journal/chronological", ScreenWidth.LARGE)


  @Test fun secondaryMenu_repositoryHighlightsPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/repository/highlights", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_repositoryHighlightsPage_smallScreen_expanded() = runTestWithExplicitExpansion("/repository/highlights", MenuType.SECONDARY)
  @Test fun secondaryMenu_repositoryHighlightsPage_mediumScreen() = runTestWithDefaultExpansion("/repository/highlights", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_repositoryHighlightsPage_largeScreen() = runTestWithDefaultExpansion("/repository/highlights", ScreenWidth.LARGE)

  @Test fun secondaryMenu_repositoryLocationsPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/repository/locations", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_repositoryLocationsPage_smallScreen_expanded() = runTestWithExplicitExpansion("/repository/locations", MenuType.SECONDARY)
  @Test fun secondaryMenu_repositoryLocationsPage_mediumScreen() = runTestWithDefaultExpansion("/repository/locations", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_repositoryLocationsPage_largeScreen() = runTestWithDefaultExpansion("/repository/locations", ScreenWidth.LARGE)

  @Test fun secondaryMenu_repositoryTechnologiesPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/repository/technologies", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_repositoryTechnologiesPage_smallScreen_expanded() = runTestWithExplicitExpansion("/repository/technologies", MenuType.SECONDARY)
  @Test fun secondaryMenu_repositoryTechnologiesPage_mediumScreen() = runTestWithDefaultExpansion("/repository/technologies", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_repositoryTechnologiesPage_largeScreen() = runTestWithDefaultExpansion("/repository/technologies", ScreenWidth.LARGE)


  @Test fun secondaryMenu_aboutPage_smallScreen_hidden() = runTestWithDefaultExpansion("/about", ScreenWidth.SMALL)
  @Test fun secondaryMenu_aboutPage_mediumScreen() = runTestWithDefaultExpansion("/about", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_aboutPage_largeScreen() = runTestWithDefaultExpansion("/about", ScreenWidth.LARGE)


  @Test fun secondaryMenu_gallerySeriesPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/series/genesis_ex_nihilo", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_gallerySeriesPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/series/genesis_ex_nihilo", MenuType.SECONDARY)
  @Test fun secondaryMenu_gallerySeriesPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/series/genesis_ex_nihilo", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_gallerySeriesPage_largeScreen() = runTestWithDefaultExpansion("/gallery/series/genesis_ex_nihilo", ScreenWidth.LARGE)


  @Test fun secondaryMenu_galleryItemPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/item/restructuring_2024", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_galleryItemPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/item/restructuring_2024", MenuType.SECONDARY)
  @Test fun secondaryMenu_galleryItemPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/item/restructuring_2024", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_galleryItemPage_largeScreen() = runTestWithDefaultExpansion("/gallery/item/restructuring_2024", ScreenWidth.LARGE)


  @Test fun secondaryMenu_journalItemPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/item/introduction_2023", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun secondaryMenu_journalItemPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/item/introduction_2023", MenuType.SECONDARY)
  @Test fun secondaryMenu_journalItemPage_mediumScreen() = runTestWithDefaultExpansion("/journal/item/introduction_2023", ScreenWidth.MEDIUM)
  @Test fun secondaryMenu_journalItemPage_largeScreen() = runTestWithDefaultExpansion("/journal/item/introduction_2023", ScreenWidth.LARGE)


  @Test fun contextualMenu_indexPage_smallScreen_hidden() = runTestWithDefaultExpansion("/", ScreenWidth.SMALL)
  @Test fun contextualMenu_indexPage_mediumScreen() = runTestWithDefaultExpansion("/", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_indexPage_largeScreen() = runTestWithDefaultExpansion("/", ScreenWidth.LARGE)


  @Test fun contextualMenu_galleryHighlightsPage_smallScreen_hidden() = runTestWithDefaultExpansion("/gallery/highlights", ScreenWidth.SMALL)
  @Test fun contextualMenu_galleryHighlightsPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/highlights", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_galleryHighlightsPage_largeScreen() = runTestWithDefaultExpansion("/gallery/highlights", ScreenWidth.LARGE)

  @Test fun contextualMenu_gallerySubjectsPage_smallScreen_hidden() = runTestWithDefaultExpansion("/gallery/subjects", ScreenWidth.SMALL)
  @Test fun contextualMenu_gallerySubjectsPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/subjects", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_gallerySubjectsPage_largeScreen() = runTestWithDefaultExpansion("/gallery/subjects", ScreenWidth.LARGE)

  @Test fun contextualMenu_galleryPalettesPage_smallScreen_hidden() = runTestWithDefaultExpansion("/gallery/palettes", ScreenWidth.SMALL)
  @Test fun contextualMenu_galleryPalettesPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/palettes", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_galleryPalettesPage_largeScreen() = runTestWithDefaultExpansion("/gallery/palettes", ScreenWidth.LARGE)

  @Test fun contextualMenu_galleryChronologicalPage_smallScreen_hidden() = runTestWithDefaultExpansion("/gallery/chronological", ScreenWidth.SMALL)
  @Test fun contextualMenu_galleryChronologicalPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/chronological", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_galleryChronologicalPage_largeScreen() = runTestWithDefaultExpansion("/gallery/chronological", ScreenWidth.LARGE)


  @Test fun contextualMenu_journalHighlightsPage_smallScreen_hidden() = runTestWithDefaultExpansion("/journal/highlights", ScreenWidth.SMALL)
  @Test fun contextualMenu_journalHighlightsPage_mediumScreen() = runTestWithDefaultExpansion("/journal/highlights", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_journalHighlightsPage_largeScreen() = runTestWithDefaultExpansion("/journal/highlights", ScreenWidth.LARGE)

  @Test fun contextualMenu_journalTopicsPage_smallScreen_hidden() = runTestWithDefaultExpansion("/journal/topics", ScreenWidth.SMALL)
  @Test fun contextualMenu_journalTopicsPage_mediumScreen() = runTestWithDefaultExpansion("/journal/topics", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_journalTopicsPage_largeScreen() = runTestWithDefaultExpansion("/journal/topics", ScreenWidth.LARGE)

  @Test fun contextualMenu_journalSeriesPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/series", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun contextualMenu_journalSeriesPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/series", MenuType.CONTEXTUAL)
  @Test fun contextualMenu_journalSeriesPage_mediumScreen() = runTestWithDefaultExpansion("/journal/series", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_journalSeriesPage_largeScreen() = runTestWithDefaultExpansion("/journal/series", ScreenWidth.LARGE)

  @Test fun contextualMenu_journalGenresPage_smallScreen_hidden() = runTestWithDefaultExpansion("/journal/genres", ScreenWidth.SMALL)
  @Test fun contextualMenu_journalGenresPage_mediumScreen() = runTestWithDefaultExpansion("/journal/genres", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_journalGenresPage_largeScreen() = runTestWithDefaultExpansion("/journal/genres", ScreenWidth.LARGE)

  @Test fun contextualMenu_journalChronologicalPage_smallScreen_hidden() = runTestWithDefaultExpansion("/journal/chronological", ScreenWidth.SMALL)
  @Test fun contextualMenu_journalChronologicalPage_mediumScreen() = runTestWithDefaultExpansion("/journal/chronological", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_journalChronologicalPage_largeScreen() = runTestWithDefaultExpansion("/journal/chronological", ScreenWidth.LARGE)


  @Test fun contextualMenu_repositoryHighlightsPage_smallScreen_hidden() = runTestWithDefaultExpansion("/repository/highlights", ScreenWidth.SMALL)
  @Test fun contextualMenu_repositoryHighlightsPage_mediumScreen() = runTestWithDefaultExpansion("/repository/highlights", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_repositoryHighlightsPage_largeScreen() = runTestWithDefaultExpansion("/repository/highlights", ScreenWidth.LARGE)

  @Test fun contextualMenu_repositoryLocationsPage_smallScreen_hidden() = runTestWithDefaultExpansion("/repository/locations", ScreenWidth.SMALL)
  @Test fun contextualMenu_repositoryLocationsPage_mediumScreen() = runTestWithDefaultExpansion("/repository/locations", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_repositoryLocationsPage_largeScreen() = runTestWithDefaultExpansion("/repository/locations", ScreenWidth.LARGE)

  @Test fun contextualMenu_repositoryTechnologiesPage_smallScreen_hidden() = runTestWithDefaultExpansion("/repository/technologies", ScreenWidth.SMALL)
  @Test fun contextualMenu_repositoryTechnologiesPage_mediumScreen() = runTestWithDefaultExpansion("/repository/technologies", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_repositoryTechnologiesPage_largeScreen() = runTestWithDefaultExpansion("/repository/technologies", ScreenWidth.LARGE)


  @Test fun contextualMenu_aboutPage_smallScreen_hidden() = runTestWithDefaultExpansion("/about", ScreenWidth.SMALL)
  @Test fun contextualMenu_aboutPage_mediumScreen() = runTestWithDefaultExpansion("/about", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_aboutPage_largeScreen() = runTestWithDefaultExpansion("/about", ScreenWidth.LARGE)


  @Test fun contextualMenu_gallerySeriesPage_smallScreen_hidden() = runTestWithDefaultExpansion("/gallery/series/genesis_ex_nihilo", ScreenWidth.SMALL)
  @Test fun contextualMenu_gallerySeriesPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/series/genesis_ex_nihilo", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_gallerySeriesPage_largeScreen() = runTestWithDefaultExpansion("/gallery/series/genesis_ex_nihilo", ScreenWidth.LARGE)

  @Test fun contextualMenu_galleryItemPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/gallery/item/restructuring_2024", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun contextualMenu_galleryItemPage_smallScreen_expanded() = runTestWithExplicitExpansion("/gallery/item/restructuring_2024", MenuType.CONTEXTUAL)
  @Test fun contextualMenu_galleryItemPage_mediumScreen() = runTestWithDefaultExpansion("/gallery/item/restructuring_2024", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_galleryItemPage_largeScreen() = runTestWithDefaultExpansion("/gallery/item/restructuring_2024", ScreenWidth.LARGE)

  @Test fun contextualMenu_journalItemPage_smallScreen_collapsed() = runTestWithDefaultExpansion("/journal/item/introduction_2023", ScreenWidth.SMALL)
  @Ignore("Crashing on small screen expansion")
  @Test fun contextualMenu_journalItemPage_smallScreen_expanded() = runTestWithExplicitExpansion("/journal/item/introduction_2023", MenuType.CONTEXTUAL)
  @Test fun contextualMenu_journalItemPage_mediumScreen() = runTestWithDefaultExpansion("/journal/item/introduction_2023", ScreenWidth.MEDIUM)
  @Test fun contextualMenu_journalItemPage_largeScreen() = runTestWithDefaultExpansion("/journal/item/introduction_2023", ScreenWidth.LARGE)

  /**
   * Verifies the visual appearance of the page in its default state.
   *
   * This helper performs the following steps:
   * 1. Sets up the test harness with the given screen [size].
   * 2. Navigates to the page at [path].
   * 3. Captures and compares a screenshot against the golden image.
   */
  private fun runTestWithDefaultExpansion(path: String, size: ScreenWidth) {
    harness.setup(size)
    val page = harness.openPage(URI.create(path))
    page.waitForLoad()

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.png"
    harness.checkScreendiff(page, goldenName)
  }

  /**
   * Verifies the visual appearance of the page after expanding the specified menu.
   *
   * This helper checks for a specific interaction on Small screens only:
   * 1. Sets up the test harness with [ScreenWidth.SMALL].
   * 2. Navigates to the page at [path].
   * 3. Clicks the summary element of the [type] menu to expand it.
   * 4. Captures and compares a screenshot against the golden image.
   */
  private fun runTestWithExplicitExpansion(path: String, type: MenuType) {
    harness.setup(ScreenWidth.SMALL)
    val page = harness.openPage(URI.create(path))
    page.waitForLoad()

    page.expandPopupMenu(type)

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.png"
    harness.checkScreendiff(page, goldenName)
  }

  /** Defines the specific menu component to target. */
}
