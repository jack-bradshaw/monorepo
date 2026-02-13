package com.jackbradshaw.site.tests.cookiebanner

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.site.tests.CookieConsent
import com.jackbradshaw.site.tests.Instrumentation
import com.jackbradshaw.site.tests.PageSubject.Companion.assertThat
import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.waitForLoad
import java.net.URI
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Verifies the click behaviour of the cookie banner. */
@RunWith(JUnit4::class)
class CookieBannerBehaviourTest {

  @get:Rule val testCaseName = TestName()

  private val instrumentation = Instrumentation()

  @After
  fun tearDown() {
    instrumentation.tearDown()
  }

  @Test
  fun clickAccept_hidesBannerAndUpdatesDataLayer() {
    instrumentation.setup(ScreenWidth.MEDIUM, cookieConsent = CookieConsent.NotSet)

    val page = instrumentation.openPage(URI.create("/"))
    page.waitForLoad()

    page.locator("#accept-privacy").click()

    assertThat(page).hasNoElement("#privacy-notice")
    assertThat(page.evaluate("window.localStorage.getItem('$ANALYTICS_CONSENT_KEY')"))
        .isEqualTo("true")
  }

  @Test
  fun clickDecline_hidesBannerAndUpdatesDataLayer() {
    instrumentation.setup(ScreenWidth.MEDIUM, cookieConsent = CookieConsent.NotSet)

    val page = instrumentation.openPage(URI.create("/"))
    page.waitForLoad()

    page.locator("#decline-privacy").click()

    assertThat(page).hasNoElement("#privacy-notice")
    assertThat(page.evaluate("window.localStorage.getItem('$ANALYTICS_CONSENT_KEY')"))
        .isEqualTo("false")
  }

  companion object {
    /** The local storage key of the consent state (boolean). */
    private const val ANALYTICS_CONSENT_KEY = "analytics_consent_granted"
  }
}
