package com.jackbradshaw.site.tests

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import com.microsoft.playwright.options.ScreenshotAnimations
import com.microsoft.playwright.options.ScreenshotCaret
import com.microsoft.playwright.options.ScreenshotType

/** Blocks until this [Page] has loaded all fonts and images. */
fun Page.waitForLoad() {
  evaluate("document.querySelectorAll('img').forEach(img => img.loading = 'eager')")
  waitForFunction("document.fonts.status === 'loaded'")
  waitForFunction("Array.from(document.images).every(img => img.complete)")
}

/**
 * Finds the first element in this [Page] that matches [locator], scrolls it into view (if
 * necessary), and returns it.
 */
fun Page.findElement(locator: String): Locator =
    locator(locator).first().also { it.scrollIntoViewIfNeeded() }

/** Captures a screenshot of this [Page] and returns the bytes of the image (in PNG format). */
fun Page.captureScreenshot(): ByteArray =
    screenshot(
        Page.ScreenshotOptions()
            .setFullPage(true)
            .setType(ScreenshotType.PNG)
            .setAnimations(ScreenshotAnimations.DISABLED)
            .setCaret(ScreenshotCaret.HIDE))
