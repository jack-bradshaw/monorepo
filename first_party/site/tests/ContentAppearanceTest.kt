package com.jackbradshaw.site.tests

import io.ktor.server.routing.get
import java.net.URI
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** These tests verify the site appearance using screendiffing. */
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
  fun galleryHighlightsPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/highlights"), ScreenWidth.SMALL)
  }

  @Test
  fun galleryHighlightsPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/highlights"), ScreenWidth.MEDIUM)
  }

  @Test
  fun galleryHighlightsPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/highlights"), ScreenWidth.LARGE)
  }

  @Test
  fun gallerySubjectsPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/subjects"), ScreenWidth.SMALL)
  }

  @Test
  fun gallerySubjectsPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/subjects"), ScreenWidth.MEDIUM)
  }

  @Test
  fun gallerySubjectsPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/subjects"), ScreenWidth.LARGE)
  }

  @Test
  fun galleryPalettesPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/palettes"), ScreenWidth.SMALL)
  }

  @Test
  fun galleryPalettesPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/palettes"), ScreenWidth.MEDIUM)
  }

  @Test
  fun galleryPalettesPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/palettes"), ScreenWidth.LARGE)
  }

  @Test
  fun galleryChronologicalPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/chronological"), ScreenWidth.SMALL)
  }

  @Test
  fun galleryChronologicalPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/chronological"), ScreenWidth.MEDIUM)
  }

  @Test
  fun galleryChronologicalPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/chronological"), ScreenWidth.LARGE)
  }

  @Test
  fun galleryItemPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/item/restructuring_2024"), ScreenWidth.SMALL)
  }

  @Test
  fun galleryItemPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/item/restructuring_2024"), ScreenWidth.MEDIUM)
  }

  @Test
  fun galleryItemPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/gallery/item/restructuring_2024"), ScreenWidth.LARGE)
  }

  @Test
  fun journalHighlightsPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/highlights"), ScreenWidth.SMALL)
  }

  @Test
  fun journalHighlightsPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/highlights"), ScreenWidth.MEDIUM)
  }

  @Test
  fun journalHighlightsPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/highlights"), ScreenWidth.LARGE)
  }

  @Test
  fun journalTopicsPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/topics"), ScreenWidth.SMALL)
  }

  @Test
  fun journalTopicsPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/topics"), ScreenWidth.MEDIUM)
  }

  @Test
  fun journalTopicsPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/topics"), ScreenWidth.LARGE)
  }

  @Test
  fun journalSeriesPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/series"), ScreenWidth.SMALL)
  }

  @Test
  fun journalSeriesPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/series"), ScreenWidth.MEDIUM)
  }

  @Test
  fun journalSeriesPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/series"), ScreenWidth.LARGE)
  }

  @Test
  fun journalChronologicalPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/chronological"), ScreenWidth.SMALL)
  }

  @Test
  fun journalChronologicalPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/chronological"), ScreenWidth.MEDIUM)
  }

  @Test
  fun journalChronologicalPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/chronological"), ScreenWidth.LARGE)
  }

  @Test
  fun journalItemPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/item/sports-bar"), ScreenWidth.SMALL)
  }

  @Test
  fun journalItemPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/item/sports-bar"), ScreenWidth.MEDIUM)
  }

  @Test
  fun journalItemPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/journal/item/sports-bar"), ScreenWidth.LARGE)
  }

  @Test
  fun repositoryHighlightsPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/repository/highlights"), ScreenWidth.SMALL)
  }

  @Test
  fun repositoryHighlightsPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/repository/highlights"), ScreenWidth.MEDIUM)
  }

  @Test
  fun repositoryHighlightsPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/repository/highlights"), ScreenWidth.LARGE)
  }

  @Test
  fun repositoryLocationsPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/repository/locations"), ScreenWidth.SMALL)
  }

  @Test
  fun repositoryLocationsPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/repository/locations"), ScreenWidth.MEDIUM)
  }

  @Test
  fun repositoryLocationsPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/repository/locations"), ScreenWidth.LARGE)
  }

  @Test
  fun repositoryTechnologiesPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/repository/technologies"), ScreenWidth.SMALL)
  }

  @Test
  fun repositoryTechnologiesPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/repository/technologies"), ScreenWidth.MEDIUM)
  }

  @Test
  fun repositoryTechnologiesPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/repository/technologies"), ScreenWidth.LARGE)
  }

  @Test
  fun aboutPage_smallScreen_matchesGolden() {
    runScreendiffTest(URI.create("/about"), ScreenWidth.SMALL)
  }

  @Test
  fun aboutPage_mediumScreen_matchesGolden() {
    runScreendiffTest(URI.create("/about"), ScreenWidth.MEDIUM)
  }

  @Test
  fun aboutPage_largeScreen_matchesGolden() {
    runScreendiffTest(URI.create("/about"), ScreenWidth.LARGE)
  }

  /**
   * Verifies the page at [path] matches the golden screenshot.
   *
   * Opens [path] on a screen widht width [size], captures a screenshot, and compares it to a golden
   * screenshot. The test will fail if the golden file does not exist, or if the screenshots do not
   * match exactly (byte wise). The [path] must be relative to the root of the site.
   */
  private fun runScreendiffTest(
      path: URI,
      size: ScreenWidth = ScreenWidth.LARGE /* Default for legacy tests if any */
  ) {
    harness.setup(size)

    val page = harness.openPage(path).also { it.waitForLoad() }

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.png"
    harness.checkScreendiff(page, goldenName)
  }
}
