package com.jackbradshaw.site.tests

import com.google.common.truth.Truth.assertWithMessage
import com.microsoft.playwright.Page
import com.microsoft.playwright.options.ScreenshotAnimations
import com.microsoft.playwright.options.ScreenshotCaret
import com.microsoft.playwright.options.ScreenshotType
import io.ktor.server.routing.get
import java.net.URI
import java.nio.file.Path
import java.nio.file.Paths
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** These tests verify the site appearance using screendiffing. */
@RunWith(JUnit4::class)
class AppearanceTest {

  /** Provides the name of the current test case. Used to derive the name of the golden file. */
  @get:Rule val testCaseName = TestName()

  private val harness = TestHarness()

  @After
  fun tearDown() {
    harness.tearDown()
  }

  @Test
  fun indexPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/"), ScreenWidth.SMALL)
  }

  @Test
  fun indexPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/"), ScreenWidth.MEDIUM)
  }

  @Test
  fun indexPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/"), ScreenWidth.LARGE)
  }

  @Test
  fun galleryHighlightsPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/highlights"), ScreenWidth.SMALL)
  }

  @Test
  fun galleryHighlightsPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/highlights"), ScreenWidth.MEDIUM)
  }

  @Test
  fun galleryHighlightsPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/highlights"), ScreenWidth.LARGE)
  }

  @Test
  fun gallerySubjectsPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/subjects"), ScreenWidth.SMALL)
  }

  @Test
  fun gallerySubjectsPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/subjects"), ScreenWidth.MEDIUM)
  }

  @Test
  fun gallerySubjectsPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/subjects"), ScreenWidth.LARGE)
  }

  @Test
  fun galleryPalettesPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/palettes"), ScreenWidth.SMALL)
  }

  @Test
  fun galleryPalettesPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/palettes"), ScreenWidth.MEDIUM)
  }

  @Test
  fun galleryPalettesPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/palettes"), ScreenWidth.LARGE)
  }

  @Test
  fun galleryChronologicalPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/chronological"), ScreenWidth.SMALL)
  }

  @Test
  fun galleryChronologicalPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/chronological"), ScreenWidth.MEDIUM)
  }

  @Test
  fun galleryChronologicalPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/chronological"), ScreenWidth.LARGE)
  }

  @Test
  fun galleryItemPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/item/restructuring_2024"), ScreenWidth.SMALL)
  }

  @Test
  fun galleryItemPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/item/restructuring_2024"), ScreenWidth.MEDIUM)
  }

  @Test
  fun galleryItemPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/gallery/item/restructuring_2024"), ScreenWidth.LARGE)
  }

  @Test
  fun journalHighlightsPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/highlights"), ScreenWidth.SMALL)
  }

  @Test
  fun journalHighlightsPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/highlights"), ScreenWidth.MEDIUM)
  }

  @Test
  fun journalHighlightsPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/highlights"), ScreenWidth.LARGE)
  }

  @Test
  fun journalTopicsPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/topics"), ScreenWidth.SMALL)
  }

  @Test
  fun journalTopicsPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/topics"), ScreenWidth.MEDIUM)
  }

  @Test
  fun journalTopicsPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/topics"), ScreenWidth.LARGE)
  }

  @Test
  fun journalSeriesPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/series"), ScreenWidth.SMALL)
  }

  @Test
  fun journalSeriesPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/series"), ScreenWidth.MEDIUM)
  }

  @Test
  fun journalSeriesPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/series"), ScreenWidth.LARGE)
  }

  @Test
  fun journalChronologicalPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/chronological"), ScreenWidth.SMALL)
  }

  @Test
  fun journalChronologicalPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/chronological"), ScreenWidth.MEDIUM)
  }

  @Test
  fun journalChronologicalPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/chronological"), ScreenWidth.LARGE)
  }

  @Test
  fun journalItemPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/item/sports-bar"), ScreenWidth.SMALL)
  }

  @Test
  fun journalItemPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/item/sports-bar"), ScreenWidth.MEDIUM)
  }

  @Test
  fun journalItemPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/journal/item/sports-bar"), ScreenWidth.LARGE)
  }

  @Test
  fun repositoryHighlightsPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/repository/highlights"), ScreenWidth.SMALL)
  }

  @Test
  fun repositoryHighlightsPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/repository/highlights"), ScreenWidth.MEDIUM)
  }

  @Test
  fun repositoryHighlightsPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/repository/highlights"), ScreenWidth.LARGE)
  }

  @Test
  fun repositoryLocationsPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/repository/locations"), ScreenWidth.SMALL)
  }

  @Test
  fun repositoryLocationsPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/repository/locations"), ScreenWidth.MEDIUM)
  }

  @Test
  fun repositoryLocationsPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/repository/locations"), ScreenWidth.LARGE)
  }

  @Test
  fun repositoryTechnologiesPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/repository/technologies"), ScreenWidth.SMALL)
  }

  @Test
  fun repositoryTechnologiesPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/repository/technologies"), ScreenWidth.MEDIUM)
  }

  @Test
  fun repositoryTechnologiesPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/repository/technologies"), ScreenWidth.LARGE)
  }

  @Test
  fun aboutPage_smallScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/about"), ScreenWidth.SMALL)
  }

  @Test
  fun aboutPage_mediumScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/about"), ScreenWidth.MEDIUM)
  }

  @Test
  fun aboutPage_largeScreen_matchesGolden() {
    runPageScreendiffTest(URI.create("/about"), ScreenWidth.LARGE)
  }

  @Test
  fun primaryPopupMenu_smallScreen_matchesGolden() {
    harness.setup(ScreenWidth.SMALL)

    val page = harness.openPage(URI.create("/"))
    harness.findElement(page, "nav.primary summary").click()
    harness.findElement(page, "nav.primary details[open]").waitFor()

    performScreendiff(page)
  }

  @Test
  fun secondaryPopupMenu_smallScreen_matchesGolden() {
    harness.setup(ScreenWidth.SMALL)

    val page = harness.openPage(URI.create("/gallery/highlights"))
    harness.findElement(page, "nav.secondary summary").click()
    harness.findElement(page, "nav.secondary details[open]").waitFor()

    performScreendiff(page)
  }

  /**
   * Verifies the page identified by [path] matches the golden screenshot.
   *
   * Opens [path] on a screen widht width [size], captures a screenshot, and compares it to the
   * golden screenshot. The test will fail if the golden file does not exist or if the screenshots
   * do not match exactly (byte wise). Path must be relative to the root of the site.
   */
  private fun runPageScreendiffTest(
      path: URI,
      size: ScreenWidth = ScreenWidth.LARGE /* Default for legacy tests if any */
  ) {
    harness.setup(size)

    val page = harness.openPage(path)

    performScreendiff(page)
  }

  /** Captures a screenshot of [page] and verifies it matches the golden screenshot. */
  private fun performScreendiff(page: Page) {
    val screenshot = captureScreenshot(page)

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.png"
    val goldenPath = harness.getRunfile(GOLDEN_DIR.resolve(goldenName))
    val goldenFile = goldenPath.toFile()

    check(goldenFile.exists()) {
      val savePath = saveScreenshot(screenshot, goldenName)
      "Golden file $goldenPath does not exist. Latest saved to $savePath"
    }

    if (!goldenFile.readBytes().contentEquals(screenshot)) {
      val savePath = saveScreenshot(screenshot, goldenName)
      assertWithMessage("Screenshot and golden do not match. Latest saved to $savePath").fail()
    }
  }

  /** Captures a screenshot of [page] and returns the bytes of the image (in PNG format). */
  fun captureScreenshot(page: Page): ByteArray =
      page.screenshot(
          Page.ScreenshotOptions()
              .setFullPage(true)
              .setType(ScreenshotType.PNG)
              .setAnimations(ScreenshotAnimations.DISABLED)
              .setCaret(ScreenshotCaret.HIDE))

  /**
   * Saves [screenshot] to the during-test output directory in a file named [goldenName], and
   * returns the absolute path to the file in the post-test output directory.
   */
  private fun saveScreenshot(screenshot: ByteArray, goldenName: String): Path {
    val path = DURING_TEST_SCREENSHOT_OUTPUT_LOCATION.resolve(goldenName)
    path.toFile().writeBytes(screenshot)
    return POST_TEST_SCREENSHOT_OUTPUT_LOCATION.resolve(goldenName)
  }

  companion object {
    /** The directory where Bazel saves test artefacts during test operation. */
    private val DURING_TEST_SCREENSHOT_OUTPUT_LOCATION =
        Paths.get(System.getenv("TEST_UNDECLARED_OUTPUTS_DIR")).also {
          check(it != null) { "TEST_UNDECLARED_OUTPUTS_DIR environmental variable not set." }
        }

    /** There directory where Bazel moves test artefacts to after the test ends. */
    private val POST_TEST_SCREENSHOT_OUTPUT_LOCATION =
        Paths.get("bazel-testlogs/first_party/site/tests/appearance_test")
            .resolve(
                "shard_${System.getenv("TEST_SHARD_INDEX").toInt() + 1}" +
                    "_of_${System.getenv("TEST_TOTAL_SHARDS")}")
            .resolve("test.outputs")

    /** The directory where screendiff goldens are stored, relative to the runfiles root. */
    private val GOLDEN_DIR = Paths.get("_main/first_party/site/tests/goldens")
  }
}
