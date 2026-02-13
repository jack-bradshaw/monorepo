package com.jackbradshaw.site.tests.menu

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.site.tests.Instrumentation
import com.jackbradshaw.site.tests.MenuType
import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.waitForLoad
import com.microsoft.playwright.Page
import java.net.URI
import org.junit.After

/** Verifies the presence of menus by consulting the DOM (not a screendiff). */
abstract class BaseMenuPresenceTest(private val type: MenuType) {

  protected val instrumentation = Instrumentation()

  @After
  fun tearDown() {
    instrumentation.tearDown()
  }

  /**
   * Opens the page at [path] and verifies whether the menu of [type] is [present] by reading the
   * DOM.
   */
  protected fun runTest(path: String, present: Boolean) {
    instrumentation.setup(ScreenWidth.MEDIUM)

    val page = instrumentation.openPage(URI.create(path))
    page.waitForLoad()

    assertThat(page.hasMenu(type)).isEqualTo(present)
  }

  /** Returns whether this page has a menu of [type] in the DOM. */
  private fun Page.hasMenu(type: MenuType): Boolean {
    return locator(type.selector).isVisible()
  }
}
