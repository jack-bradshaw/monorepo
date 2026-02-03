package com.jackbradshaw.site.tests

import com.google.common.truth.Truth.assertThat
import java.net.URI
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName

/**
 * Tests the visual appearance of the cookie consent banner.
 *
 * The tests cover the following across a variety of screen sizes:
 *
 * - Initial banner presentation.
 *
 * Caveats:
 * 
 * - These tests must be run via CI machine to cross-platform rendering differences (fonts, sub-pixel rendering).
 */
class CookieBannerAppearanceTest {

  /** Used to derive the name of the golden file. */
  @get:Rule val testCaseName = TestName()

  private val harness = TestHarness()

  @After
  fun tearDown() {
    harness.tearDown()
  }

  @Test fun verifyBannerAppearance_small() = runTest(ScreenWidth.SMALL)
  @Test fun verifyBannerAppearance_medium() = runTest(ScreenWidth.MEDIUM)
  @Test fun verifyBannerAppearance_large() = runTest(ScreenWidth.LARGE)

  private fun runTest(width: ScreenWidth) {
    harness.setup(width, cookieConsentGranted = false)

    val page = harness.openPage(URI.create("/"))
    page.waitForLoad()

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.png"
    harness.checkScreendiff(page, goldenName)
  }
}
