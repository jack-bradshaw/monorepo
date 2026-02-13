package com.jackbradshaw.site.tests.cookiebanner

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.site.tests.CookieConsent
import com.jackbradshaw.site.tests.Instrumentation
import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.Screendiffer
import com.jackbradshaw.site.tests.waitForLoad
import java.net.URI
import java.nio.file.Paths
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Verifies the appearance of the cookie banner. */
@RunWith(JUnit4::class)
class CookieBannerAppearanceTest {

  @get:Rule val testCaseName = TestName()

  private val instrumentation = Instrumentation()

  private val screendiffer = Screendiffer(GOLDEN_BASE_PATH)

  @After
  fun tearDown() {
    instrumentation.tearDown()
  }

  @Test
  fun checkVisuals_smallScreen() {
    checkVisuals(ScreenWidth.SMALL)
  }

  @Test
  fun checkVisuals_mediumScreen() {
    checkVisuals(ScreenWidth.MEDIUM)
  }

  @Test
  fun checkVisuals_largeScreen() {
    checkVisuals(ScreenWidth.LARGE)
  }

  @Test
  fun checkText() {
    instrumentation.setup(ScreenWidth.MEDIUM, cookieConsent = CookieConsent.NotSet)

    val page = instrumentation.openPage(URI.create("/"))
    page.waitForLoad()

    assertThat(page.locator("#privacy-notice").innerText())
        .contains("Cookies are used for Google Analytics to analyze site traffic.")
  }

  @Test
  fun checkAriaAttributes() {
    instrumentation.setup(ScreenWidth.MEDIUM, cookieConsent = CookieConsent.NotSet)

    val page = instrumentation.openPage(URI.create("/"))
    page.waitForLoad()

    val banner = page.locator("#privacy-notice")
    assertThat(banner.getAttribute("role")).isEqualTo("region")
    assertThat(banner.getAttribute("aria-label")).isEqualTo("Privacy notice")
    assertThat(banner.getAttribute("aria-live")).isEqualTo("polite")
  }

  /**
   * Sets up the instrumentation with the given [size], opens the home page, and verifies the cookie
   * banner's visual appearance matches the golden image.
   */
  private fun checkVisuals(size: ScreenWidth) {
    instrumentation.setup(size, cookieConsent = CookieConsent.NotSet)

    val page = instrumentation.openPage(URI.create("/"))
    page.waitForLoad()

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.jpg"
    screendiffer.check(page, goldenName)
  }

  companion object {
    /** The path to the goldens directory in runfiles. */
    private val GOLDEN_BASE_PATH = Paths.get("_main/first_party/site/tests/cookiebanner/goldens")
  }
}
