package com.jackbradshaw.site.tests.content

import com.jackbradshaw.site.tests.Instrumentation
import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.Screendiffer
import com.jackbradshaw.site.tests.collapseAllDetailsContentBlocks
import com.jackbradshaw.site.tests.expandAllDetailsContentBlocks
import com.jackbradshaw.site.tests.waitForLoad
import java.net.URI
import java.nio.file.Paths
import org.junit.After
import org.junit.Rule
import org.junit.rules.TestName

/**
 * Verifies the visual appearance of site content by comparing a live screenshot against a golden.
 */
abstract class BaseContentAppearanceTest(goldenBasePath: String) {

  @get:Rule val testCaseName = TestName()

  protected val instrumentation = Instrumentation()

  protected val screendiffer = Screendiffer(Paths.get(goldenBasePath))

  @After
  fun tearDown() {
    instrumentation.tearDown()
  }

  /**
   * Sets the screen to [size], opens the page at [path], collapses all sections, and checks the
   * appearance.
   */
  protected fun runCollapsedTest(path: String, size: ScreenWidth) {
    instrumentation.setup(size)

    val page = instrumentation.openPage(URI.create(path))
    page.collapseAllDetailsContentBlocks()
    page.waitForLoad()

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.jpg"
    screendiffer.check(page, goldenName)
  }

  /**
   * Sets the screen to [size], opens the page at [path], expands all sections, and checks the
   * appearance.
   */
  protected fun runExpandedTest(path: String, size: ScreenWidth, captureFullPage: Boolean = true) {
    instrumentation.setup(size)

    val page = instrumentation.openPage(URI.create(path))
    page.expandAllDetailsContentBlocks()
    page.waitForLoad()

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.jpg"
    screendiffer.check(page, goldenName, captureFullPage)
  }

  /**
   * Sets the screen to [size], opens the page at [path], and checks the appearance. This is used
   * for pages that do not have collapsible content, so neither collapsing nor expanding logic is
   * applied.
   */
  protected fun runNonCollapsibleTest(path: String, size: ScreenWidth) {
    instrumentation.setup(size)

    val page = instrumentation.openPage(URI.create(path))
    page.waitForLoad()

    val goldenName = "${this::class.simpleName}_${testCaseName.methodName}.jpg"
    screendiffer.check(page, goldenName)
  }
}
