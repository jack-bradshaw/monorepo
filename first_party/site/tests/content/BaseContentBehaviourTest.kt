package com.jackbradshaw.site.tests.content

import com.google.common.truth.Truth.assertWithMessage
import com.jackbradshaw.site.tests.Instrumentation
import com.jackbradshaw.site.tests.PageSubject.Companion.assertThat
import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.expandAllDetailsContentBlocks
import com.jackbradshaw.site.tests.waitForLoad
import com.microsoft.playwright.Locator
import java.net.URI
import org.junit.After

/** Verifies the click behaviour of site content by clicking and checking the opened destination. */
abstract class BaseContentBehaviourTest {

  protected val instrumentation = Instrumentation()

  @After
  fun tearDown() {
    instrumentation.tearDown()
  }

  /**
   * Sets the screen to medium, opens [startPagePath], and verifies that clicking the item with
   * [itemLabel] correctly navigates to [expectedDestinationPagePath].
   */
  protected fun runInternalLinkTest(
      startPagePath: URI,
      itemLabel: String,
      expectedDestinationPagePath: URI
  ) {
    instrumentation.setup(ScreenWidth.MEDIUM)

    val page = instrumentation.openPage(startPagePath)
    page.expandAllDetailsContentBlocks()
    page.waitForLoad()

    page.locator("a").filter(Locator.FilterOptions().setHasText(itemLabel)).first().click()

    assertThat(page).hasUri(expectedDestinationPagePath)
  }

  /**
   * Sets the screen to medium, opens [startPagePath], and verifies that clicking the item with
   * [itemLabel] correctly navigates to [destinationUri].
   *
   * This test only verifies the right page was opened without checking its contents; therefore,
   * reaching a 404 will not fail the test, so long as the URL is correct.
   */
  protected fun runExternalLinkTest(startPagePath: URI, itemLabel: String, destinationUri: URI) {
    instrumentation.setup(ScreenWidth.MEDIUM)

    val page = instrumentation.openPage(startPagePath)
    page.expandAllDetailsContentBlocks()
    page.waitForLoad()

    val link = page.locator("a").filter(Locator.FilterOptions().setHasText(itemLabel)).first()
    val popup = page.waitForPopup { link.click() }

    assertWithMessage("Expected navigation to $destinationUri")
        .that(popup.url())
        .contains(destinationUri.toString())
  }
}
