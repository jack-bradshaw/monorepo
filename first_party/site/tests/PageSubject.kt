package com.jackbradshaw.site.tests

import com.google.common.truth.FailureMetadata
import com.google.common.truth.Subject
import com.google.common.truth.Subject.Factory
import com.google.common.truth.Truth.assertAbout
import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import java.net.URI

/** Truth [Subject] for [Page] objects. */
class PageSubject
private constructor(private val metadata: FailureMetadata, private val actual: Page?) :
    Subject(metadata, actual) {

  /**
   * Verifies the subject is non-null and has a URI that matches [uri].
   *
   * Trailing and leading slashes are removed during equality evaluation; for example,
   * `localhost:8080/foo/` matches `localhost:8080/foo` but not `localhost:8080/foo/bar`.
   */
  fun hasUri(uri: URI) {
    val page = checkPageNotNull()

    val actualUri = URI.create(page.url()).path.removePrefix("/").removeSuffix("/")
    val expectedUri = uri.toString().removePrefix("/").removeSuffix("/")

    check("uri (trailing slashes removed)").that(actualUri).isEqualTo(expectedUri)
  }

  /** Verifies the page URL path and fragment match [expectedPath]. */
  fun hasUrlPath(expectedPath: String) {
    val page = checkPageNotNull()

    val actualUri = URI.create(page.url())
    val actualPathWithoutFragment = actualUri.path.removeSuffix("/")
    val actualFragment = actualUri.fragment?.let { "#$it" } ?: ""
    val actualFullPath = actualPathWithoutFragment + actualFragment

    val normalizedExpectedPath = expectedPath.removeSuffix("/")

    check("urlPath").that(actualFullPath).isEqualTo(normalizedExpectedPath)
  }

  /** Verifies the page has an element matching [selector]. */
  fun hasElement(selector: String) {
    val page = checkPageNotNull()
    check("element exists: $selector").that(page.locator(selector).isVisible()).isTrue()
  }

  /** Verifies the page does not have an element matching [selector]. */
  fun hasNoElement(selector: String) {
    val page = checkPageNotNull()
    check("element does not exist: $selector").that(page.locator(selector).isVisible()).isFalse()
  }

  /** Verifies the subject has a menu of [type]. */
  fun hasMenu(type: MenuType) {
    val page = checkPageNotNull()
    val locator = page.locator(type.selector)
    check("${type.name.lowercase()} menu exists").that(locator.count() > 0).isTrue()
    check("${type.name.lowercase()} menu visibility").that(locator.isVisible()).isTrue()
  }

  /** Verifies the subject does not have a menu of [type]. */
  fun hasNoMenu(type: MenuType) {
    val page = checkPageNotNull()
    check("${type.name.lowercase()} menu").that(page.locator(type.selector).isVisible()).isFalse()
  }

  /** Verifies the subject has a menu item with the given [label]. */
  fun hasMenuItem(label: String) {
    val page = checkPageNotNull()
    check("menu item with label '$label'")
        .that(
            page
                .locator("nav a, nav summary")
                .filter(Locator.FilterOptions().setHasText(label))
                .isVisible())
        .isTrue()
  }

  /** Checks the subject is not null. */
  private fun checkPageNotNull(): Page {
    check("nullability").that(actual).isNotNull()
    return actual!!
  }

  companion object {

    /** Factory for [PageSubject]. */
    private val pages =
        Factory<PageSubject, Page> { metadata, actual -> PageSubject(metadata, actual) }

    /** Creates a [PageSubject] that makes assertions on [page]. */
    @JvmStatic fun assertThat(page: Page): PageSubject = assertAbout(pages).that(page)
  }
}
