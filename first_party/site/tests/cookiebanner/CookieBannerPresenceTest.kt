package com.jackbradshaw.site.tests.cookiebanner

import com.jackbradshaw.site.tests.CookieConsent
import com.jackbradshaw.site.tests.Instrumentation
import com.jackbradshaw.site.tests.PageSubject.Companion.assertThat
import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.waitForLoad
import java.net.URI
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Verifies the cookie banner is present/absent as expected. */
@RunWith(JUnit4::class)
class CookieBannerPresenceTest {

  private val instrumentation = Instrumentation()

  @After
  fun tearDown() {
    instrumentation.tearDown()
  }

  @Test
  fun consentGranted_notPresent() {
    instrumentation.setup(ScreenWidth.MEDIUM, cookieConsent = CookieConsent.Set(true))

    val page = instrumentation.openPage(URI.create("/"))
    page.waitForLoad()

    assertThat(page).hasNoElement("#privacy-notice")
  }

  @Test
  fun consentDenied_notPresent() {
    instrumentation.setup(ScreenWidth.MEDIUM, cookieConsent = CookieConsent.Set(false))

    val page = instrumentation.openPage(URI.create("/"))
    page.waitForLoad()

    assertThat(page).hasNoElement("#privacy-notice")
  }

  @Test
  fun consentNotSet_present() {
    instrumentation.setup(ScreenWidth.MEDIUM, cookieConsent = CookieConsent.NotSet)

    val page = instrumentation.openPage(URI.create("/"))
    page.waitForLoad()

    assertThat(page).hasElement("#privacy-notice")
  }
}
