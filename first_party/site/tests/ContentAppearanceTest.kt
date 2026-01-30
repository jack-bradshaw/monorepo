package com.jackbradshaw.site.tests

import io.ktor.server.routing.get
import java.net.URI
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** These tests verify the appearance of the site content using screendiffing. */
@RunWith(JUnit4::class)
class ContentAppearanceTest {

  /** Used to derive the name of the golden file. */
  @get:Rule val testCaseName = TestName()

  private val harness = TestHarness()

  @After
  fun tearDown() {
    harness.tearDown()
  }

  @Test
  fun indexPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/"), ScreenWidth.SMALL)
  }

  @Test
  fun indexPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/"), ScreenWidth.MEDIUM)
  }

  @Test
  fun indexPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/"), ScreenWidth.LARGE)
  }

  @Test
  fun galleryHighlightsPage_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/highlights"), ScreenWidth.LARGE)
  }

  @Test
  fun gallerySubjectsPage_largeScreen_defaultExpansion_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/subjects"), ScreenWidth.LARGE)
  }

  @Test
  fun gallerySubjectsPage_largeScreen_expanded_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/subjects"), ScreenWidth.LARGE, ExpansionState.EXPAND)
  }

  @Test
  fun gallerySubjectsPage_largeScreen_collapsed_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/subjects"), ScreenWidth.LARGE, ExpansionState.COLLAPSE)
  }

  @Test
  fun galleryPalettesPage_largeScreen_defaultExpansion_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/palettes"), ScreenWidth.LARGE)
  }

  @Test
  fun galleryPalettesPage_largeScreen_expanded_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/palettes"), ScreenWidth.LARGE, ExpansionState.EXPAND)
  }

  @Test
  fun galleryPalettesPage_largeScreen_collapsed_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/palettes"), ScreenWidth.LARGE, ExpansionState.COLLAPSE)
  }

  @Test
  fun galleryChronologicalPage_largeScreen_defaultExpansion_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/chronological"), ScreenWidth.LARGE)
  }

  @Test
  fun galleryChronologicalPage_largeScreen_expanded_matchesGolden() {
    runScreendiffTest(
        URI.create("/gallery/chronological"), ScreenWidth.LARGE, ExpansionState.EXPAND)
  }

  @Test
  fun galleryChronologicalPage_largeScreen_collapsed_matchesGolden() {
    runScreendiffTest(
        URI.create("/gallery/chronological"), ScreenWidth.LARGE, ExpansionState.COLLAPSE)
  }

  @Test
  fun journalHighlightsPage_matchesGolden() {
    runScreendiffTest(URI.create("/journal/highlights"), ScreenWidth.LARGE)
  }

  @Test
  fun journalTopicsPage_largeScreen_defaultExpansion_matchesGolden() {
    runScreendiffTest(URI.create("/journal/topics"), ScreenWidth.LARGE)
  }

  @Test
  fun journalTopicsPage_largeScreen_expanded_matchesGolden() {
    runScreendiffTest(URI.create("/journal/topics"), ScreenWidth.LARGE, ExpansionState.EXPAND)
  }

  @Test
  fun journalTopicsPage_largeScreen_collapsed_matchesGolden() {
    runScreendiffTest(URI.create("/journal/topics"), ScreenWidth.LARGE, ExpansionState.COLLAPSE)
  }

  @Test
  fun journalSeriesPage_largeScreen_defaultExpansion_matchesGolden() {
    runScreendiffTest(URI.create("/journal/series"), ScreenWidth.LARGE)
  }

  @Test
  fun journalSeriesPage_largeScreen_expanded_matchesGolden() {
    runScreendiffTest(URI.create("/journal/series"), ScreenWidth.LARGE, ExpansionState.EXPAND)
  }

  @Test
  fun journalSeriesPage_largeScreen_collapsed_matchesGolden() {
    runScreendiffTest(URI.create("/journal/series"), ScreenWidth.LARGE, ExpansionState.COLLAPSE)
  }

  @Test
  fun journalGenresPage_largeScreen_defaultExpansion_matchesGolden() {
    runScreendiffTest(URI.create("/journal/genres"), ScreenWidth.LARGE)
  }

  @Test
  fun journalGenresPage_largeScreen_expanded_matchesGolden() {
    runScreendiffTest(URI.create("/journal/genres"), ScreenWidth.LARGE, ExpansionState.EXPAND)
  }

  @Test
  fun journalGenresPage_largeScreen_collapsed_matchesGolden() {
    runScreendiffTest(URI.create("/journal/genres"), ScreenWidth.LARGE, ExpansionState.COLLAPSE)
  }

  @Test
  fun journalChronologicalPage_largeScreen_defaultExpansion_matchesGolden() {
    runScreendiffTest(URI.create("/journal/chronological"), ScreenWidth.LARGE)
  }

  @Test
  fun journalChronologicalPage_largeScreen_expanded_matchesGolden() {
    runScreendiffTest(
        URI.create("/journal/chronological"), ScreenWidth.LARGE, ExpansionState.EXPAND)
  }

  @Test
  fun journalChronologicalPage_largeScreen_collapsed_matchesGolden() {
    runScreendiffTest(
        URI.create("/journal/chronological"), ScreenWidth.LARGE, ExpansionState.COLLAPSE)
  }

  @Test
  fun repositoryHighlightsPage_matchesGolden() {
    runScreendiffTest(URI.create("/repository/highlights"), ScreenWidth.LARGE)
  }

  @Test
  fun repositoryLocationsPage_largeScreen_defaultExpansion_matchesGolden() {
    runScreendiffTest(URI.create("/repository/locations"), ScreenWidth.LARGE)
  }

  @Test
  fun repositoryLocationsPage_largeScreen_expanded_matchesGolden() {
    runScreendiffTest(URI.create("/repository/locations"), ScreenWidth.LARGE, ExpansionState.EXPAND)
  }

  @Test
  fun repositoryLocationsPage_largeScreen_collapsed_matchesGolden() {
    runScreendiffTest(
        URI.create("/repository/locations"), ScreenWidth.LARGE, ExpansionState.COLLAPSE)
  }

  @Test
  fun repositoryTechnologiesPage_largeScreen_defaultExpansion_matchesGolden() {
    runScreendiffTest(URI.create("/repository/technologies"), ScreenWidth.LARGE)
  }

  @Test
  fun repositoryTechnologiesPage_largeScreen_expanded_matchesGolden() {
    runScreendiffTest(
        URI.create("/repository/technologies"), ScreenWidth.LARGE, ExpansionState.EXPAND)
  }

  @Test
  fun repositoryTechnologiesPage_largeScreen_collapsed_matchesGolden() {
    runScreendiffTest(
        URI.create("/repository/technologies"), ScreenWidth.LARGE, ExpansionState.COLLAPSE)
  }

  @Test
  fun aboutPage_largeScreen_defaultExpansion_matchesGolden() {
    runScreendiffTest(URI.create("/about"), ScreenWidth.LARGE)
  }

  @Test
  fun aboutPage_largeScreen_expanded_matchesGolden() {
    runScreendiffTest(URI.create("/about"), ScreenWidth.LARGE, ExpansionState.EXPAND)
  }

  @Test
  fun aboutPage_largeScreen_collapsed_matchesGolden() {
    runScreendiffTest(URI.create("/about"), ScreenWidth.LARGE, ExpansionState.COLLAPSE)
  }

  /**
   * Verifies the page at [path] matches the golden screenshot.
   *
   * Opens [path] on a screen width of [size], adjusts the expanded state to [expansionState] (if
   * necessary), captures a screenshot, and compares the screenshot to a golden. The test fails if
   * the golden file does not exist or if the screenshots do not match exactly (byte wise).
   *
   * The [path] value must be relative to the root of the site.
   */
  private fun runScreendiffTest(
      path: URI,
      size: ScreenWidth = ScreenWidth.LARGE,
      expansionState: ExpansionState = ExpansionState.DEFAULT
  ) {
    harness.setup(size)

    val page = harness.openPage(path).also { it.waitForLoad() }

    when (expansionState) {
      ExpansionState.EXPAND -> page.expandAllDetailsContentBlocks()
      ExpansionState.COLLAPSE -> page.collapseAllDetailsContentBlocks()
      ExpansionState.DEFAULT -> {
        /* Already in default state by definition. Nothing to do. */
      }
    }
    page.waitForLoad()

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.png"
    harness.checkScreendiff(page, goldenName)
  }

  /** Defines the intended expansion state of collapsible sections during a test. */
  enum class ExpansionState {
    /** All sections assume the default expansion state defined in the site configuration. */
    DEFAULT,

    /** All sections forced to expand. */
    EXPAND,

    /** All sections forced to collapse. */
    COLLAPSE
  }
}
