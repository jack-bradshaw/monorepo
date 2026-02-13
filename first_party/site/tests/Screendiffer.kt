package com.jackbradshaw.site.tests

import com.google.common.truth.Truth.assertWithMessage
import com.google.devtools.build.runfiles.Runfiles
import com.microsoft.playwright.Page
import com.microsoft.playwright.options.ScreenshotAnimations
import com.microsoft.playwright.options.ScreenshotCaret
import com.microsoft.playwright.options.ScreenshotType
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Provides functionality for verifying page appearance via screendiffing.
 *
 * @param goldenBasePath The base path where screendiff goldens are stored, relative to the runfiles
 *   root.
 */
class Screendiffer(private val goldenBasePath: Path) {

  init {
    val os = System.getProperty("os.name").lowercase()
    check(os.contains("linux")) {
      "Tests can only run on the Linux CI machine for pixel-perfect golden matching. Found OS: $os"
    }
  }

  /**
   * Captures a screenshot of [page] and verifies it matches the golden screenshot named
   * [goldenName] (found in [goldenBasePath]).
   *
   * If the comparison fails, the new screenshot is saved to the test outputs (details in logs).
   *
   * Expects to be run in a sharded test (i.e. shard_count set in Bazel test rule) and fails if not.
   */
  fun check(page: Page, goldenName: String, captureFullPage: Boolean = true) {
    val screenshot =
        page.screenshot(
            Page.ScreenshotOptions()
                .setFullPage(captureFullPage)
                .setAnimations(ScreenshotAnimations.DISABLED)
                .setCaret(ScreenshotCaret.HIDE)
                .setType(ScreenshotType.JPEG)
                .setQuality(30))
    val goldenPath = goldenBasePath.resolve(goldenName).fromRunfiles()
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

  /**
   * Saves [screenshot] to the during-test output directory in a file named [goldenName], and
   * returns the absolute path to the file in the post-test output directory.
   */
  private fun saveScreenshot(screenshot: ByteArray, goldenName: String): Path {
    val path = getDuringTestScreenshotOutputLocation(goldenName)
    path.toFile().writeBytes(screenshot)
    return getPostTestScreenshotOutputLocation(goldenName)
  }

  /**
   * The directory where [goldenName] can be saved during test execution to keep it after the test
   * completes.
   */
  private fun getDuringTestScreenshotOutputLocation(goldenName: String): Path =
      Paths.get(System.getenv("TEST_UNDECLARED_OUTPUTS_DIR"))
          .also {
            check(it != null) { "TEST_UNDECLARED_OUTPUTS_DIR environmental variable not set." }
          }
          .resolve(goldenName)

  /**
   * Gets the path to the test output directory where Bazel moves the screenshot named [goldenName]
   * after the test completes.
   */
  private fun getPostTestScreenshotOutputLocation(goldenName: String): Path =
      Paths.get("bazel-testlogs/first_party/site/tests")
          .resolve(generateTestShardPathString())
          .resolve("test.outputs")
          .resolve(goldenName)

  /**
   * Generates a path string for the current test shard (e.g. "shard_1_of_2").
   *
   * Throws `IllegalStateException` if test is not sharded.
   */
  private fun generateTestShardPathString(): String {
    val shardIndexEnv = System.getenv("TEST_SHARD_INDEX")
    val shardCountEnv = System.getenv("TEST_TOTAL_SHARDS")

    check(shardIndexEnv != null || shardCountEnv != null) { "test is not running with shards" }

    // Add 1 because the index is 0-based, but the test output dir is named using a 1-based index.
    val shardIndex = shardIndexEnv.toInt().plus(1)
    val shardCount = shardCountEnv.toInt()

    return "shard_${shardIndex}_of_${shardCount}"
  }

  /** Resolves [this] path relative to the runfiles root. */
  private fun Path.fromRunfiles(): Path = Paths.get(runfiles.rlocation(toString()))

  companion object {
    /** Files from Bazel dependencies. */
    private val runfiles = Runfiles.preload().unmapped()
  }
}
