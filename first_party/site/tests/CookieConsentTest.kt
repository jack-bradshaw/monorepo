package com.jackbradshaw.site.tests

import com.google.common.truth.Truth.assertThat
import com.microsoft.playwright.Page
import com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat
import java.net.URI
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName

class CookieConsentTest {

  /** Used to derive the name of the golden file. */
  @get:Rule val testCaseName = TestName()

  private val harness = TestHarness()

  @After
  fun tearDown() {
    harness.tearDown()
  }

  @Test
  fun verifyBannerAppearance() {
    harness.setup(cookieConsentGranted = false)

    val page = harness.openPage(URI.create("/"))
    page.waitForLoad()

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.png"
    harness.checkScreendiff(page, goldenName)
  }

  @Test
  fun verifyBannerText() {
    harness.setup(cookieConsentGranted = false)

    val page = harness.openPage(URI.create("/"))
    page.waitForLoad()

    page.assertBannerText()
  }

  @Test
  fun bannerPresented_whenConsentNotGranted() {
    harness.setup(cookieConsentGranted = false)

    val page = harness.openPage(URI.create("/"))
    page.waitForLoad()

    page.assertBannerVisible()
  }

  @Test
  fun bannerNotPresented_whenConsentGranted() {
    harness.setup(cookieConsentGranted = true)

    val page = harness.openPage(URI.create("/"))
    page.waitForLoad()

    page.assertBannerHidden()
  }

  @Test
  fun bannerPresented_clickAccept_hidesBannerAndGrantsConsent() {
    harness.setup(cookieConsentGranted = false)

    val page = harness.openPage(URI.create("/"))
    page.waitForLoad()

    page.locator("#accept-privacy").click()
    page.assertBannerHidden()
    page.assertConsentGranted()
  }

  @Test
  fun bannerPresented_clickDecline_hidesBannerAndDeniesConsent() {
    harness.setup(cookieConsentGranted = false)

    val page = harness.openPage(URI.create("/"))
    page.waitForLoad()

    page.locator("#decline-privacy").click()
    page.assertBannerHidden()
    page.assertConsentDenied()
  }

  @Test
  fun bannerHasCorrectAriaAttributes() {
    harness.setup(cookieConsentGranted = false)

    val page = harness.openPage(URI.create("/"))
    page.waitForLoad()

    val banner = page.locator("#privacy-notice")
    assertThat(banner).hasAttribute("role", "region")
    assertThat(banner).hasAttribute("aria-label", "Privacy notice")
    assertThat(banner).hasAttribute("aria-live", "polite")
  }

  /** Verifies the banner is visible in the UI and contains the correct text. */
  private fun Page.assertBannerVisible() {
    val banner = this.locator("#privacy-notice")

    assertThat(banner).isVisible()
  }

  /** Verifies the banner is hidden in the UI. */
  private fun Page.assertBannerHidden() {
    val banner = this.locator("#privacy-notice")

    assertThat(banner).not().isVisible()
  }

  private fun Page.assertBannerText() {
    val banner = this.locator("#privacy-notice")

    assertThat(banner)
        .containsText("Cookies are used for Google Analytics to analyze site traffic.")
  }

  /** Verifies that the consent has been set to granted in storage. */
  private fun Page.assertConsentGranted() {
    val consent = this.evaluate("window.localStorage.getItem('analytics_consent_granted')")
    assertThat(consent).isEqualTo("true")
  }

  /** Verifies that the consent has been set to denied in storage. */
  private fun Page.assertConsentDenied() {
    val consent = this.evaluate("window.localStorage.getItem('analytics_consent_granted')")
    assertThat(consent).isEqualTo("false")
  }
}
