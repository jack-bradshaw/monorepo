package com.jackbradshaw.site.tests.menu

import com.jackbradshaw.site.tests.Instrumentation
import com.jackbradshaw.site.tests.MenuType
import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.Screendiffer
import com.jackbradshaw.site.tests.expandPopupMenu
import com.jackbradshaw.site.tests.waitForLoad
import java.net.URI
import java.nio.file.Paths
import org.junit.After
import org.junit.Rule
import org.junit.rules.TestName

/** Verifies the visual appearance of menus by comparing a live screenshot against a golden. */
abstract class BaseMenuAppearanceTest(private val menuType: MenuType, goldenBasePath: String) {

  @get:Rule val testCaseName = TestName()

  protected val instrumentation = Instrumentation()

  protected val screendiffer = Screendiffer(Paths.get(goldenBasePath))

  @After
  fun tearDown() {
    instrumentation.tearDown()
  }

  /**
   * Sets the screen to [ScreenWidth.SMALL], opens the page at [path], verifies its appearance with
   * the menu collapsed.
   */
  protected fun runPopupCollapsedTest(path: String, captureFullPage: Boolean = true) {
    instrumentation.setup(ScreenWidth.SMALL)

    val page = instrumentation.openPage(URI.create(path))
    page.waitForLoad()

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.jpg"
    screendiffer.check(page, goldenName, captureFullPage)
  }

  /**
   * Sets the screen to [ScreenWidth.SMALL], opens the page at [path], expands the menu, and
   * verifies its appearance.
   */
  protected fun runPopupExpandedTest(path: String, captureFullPage: Boolean = true) {
    instrumentation.setup(ScreenWidth.SMALL)

    val page = instrumentation.openPage(URI.create(path))
    page.waitForLoad()

    page.expandPopupMenu(menuType)

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.jpg"
    screendiffer.check(page, goldenName, captureFullPage)
  }

  /** Sets the screen to [size], opens the page at [path], and verifies the menu's appearance. */
  protected fun runAlwaysExpandedTest(
      path: String,
      size: ScreenWidth,
      captureFullPage: Boolean = true
  ) {
    instrumentation.setup(size)

    val page = instrumentation.openPage(URI.create(path))
    page.waitForLoad()

    // No need to expand, menu is always expanded on MEDIUM screens.

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.jpg"
    screendiffer.check(page, goldenName, captureFullPage)
  }
}
