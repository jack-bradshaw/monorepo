package com.jackbradshaw.site.tests.menu

import com.jackbradshaw.site.tests.Instrumentation
import com.jackbradshaw.site.tests.MenuType
import com.jackbradshaw.site.tests.PageSubject.Companion.assertThat
import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.expandPopupMenu
import com.jackbradshaw.site.tests.findElement
import com.jackbradshaw.site.tests.waitForLoad
import java.net.URI
import org.junit.After

/** Verifies the click behaviour of menu items by clicking and checking the opened destination. */
abstract class BaseMenuBehaviourTest(private val type: MenuType) {

  protected val instrumentation = Instrumentation()

  @After
  fun tearDown() {
    instrumentation.tearDown()
  }

  /**
   * Opens the [pagePath], expands the menu, clicks the [itemSelector], and verifies it navigates to
   * [expectedDestinationPath]. Always uses a `SMALL` screen to make the menu collapsible.
   */
  protected fun runStartingCollapsedTest(
      pagePath: String,
      itemSelector: String,
      expectedDestinationPath: String
  ) {
    instrumentation.setup(ScreenWidth.SMALL)
    val page = instrumentation.openPage(URI.create(pagePath))

    page.expandPopupMenu(type)

    page.findElement("text=$itemSelector").click()

    assertThat(page).hasUrlPath(expectedDestinationPath)
  }

  /**
   * Opens the [pagePath], forces the menu to an expanded state, clicks the [itemSelector], and
   * verifies it navigates to [expectedDestinationPath]. Always uses a `MEDIUM` screen to make the
   * menu non-collapsible
   */
  protected fun runAlwaysExpandedTest(
      pagePath: String,
      itemSelector: String,
      expectedDestinationPath: String
  ) {
    instrumentation.setup(ScreenWidth.MEDIUM)
    val page = instrumentation.openPage(URI.create(pagePath))

    // Ensure the menu is expanded without performing a toggle action if it's already open.
    page.evaluate("document.querySelector('${type.selector} details').open = true")
    page.waitForLoad()

    page.findElement("text=$itemSelector").click()

    assertThat(page).hasUrlPath(expectedDestinationPath)
  }
}
