package com.jackbradshaw.site.tests.content

import com.google.common.truth.Truth.assertWithMessage
import com.jackbradshaw.site.tests.Instrumentation
import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.waitForLoad
import java.net.URI
import org.junit.After
import org.junit.Rule
import org.junit.rules.TestName

/**
 * Verifies the default expansion state of content pages by consulting the DOM.
 *
 * These tests check presence by consulting the DOM (not a screendiff) to improve performance and
 * avoid unnecessary overlap with the appearance tests.
 *
 * These tests do not check pages that have no expansion capability.
 */
abstract class BaseContentExpansionTest {

  @get:Rule val testCaseName = TestName()

  protected val instrumentation = Instrumentation()

  @After
  fun tearDown() {
    instrumentation.tearDown()
  }

  protected fun runExpansionTest(path: String, isExpandedByDefault: Boolean) {
    instrumentation.setup(ScreenWidth.MEDIUM)

    val page = instrumentation.openPage(URI.create(path))
    page.waitForLoad()

    val result =
        page.evaluate(
            """
            (() => {
                const details = Array.from(document.querySelectorAll('details.content.block'));
                const total = details.length;
                const openCount = details.filter(d => d.open).length;
                return { total, openCount };
            })()
            """)
            as Map<String, Any>

    val total = result["total"] as Int
    val openCount = result["openCount"] as Int

    val expectedOpenCount = if (isExpandedByDefault) total else 0
    assertWithMessage("Open details blocks").that(openCount).isEqualTo(expectedOpenCount)
  }
}
