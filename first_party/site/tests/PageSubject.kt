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

    val actualUri = page.url().removePrefix("/").removeSuffix("/")
    val expectedUri = uri.toString().removePrefix("/").removeSuffix("/")

    check("uri (trailing slashes removed)").that(actualUri).isEqualTo(expectedUri)
  }

  /** Verifies the subject has a primary menu. */
  fun hasPrimaryMenu() {
    val page = checkPageNotNull()

    val locator = page.locator("nav.primary")
    check("primary menu exists").that(locator.count() > 0).isTrue()
    check("primary menu visibility").that(locator.isVisible()).isTrue()
  }

  /** Verifies the subject does not have a primary menu. */
  fun hasNoPrimaryMenu() {
    val page = checkPageNotNull()
    check("primary menu").that(page.locator("nav.primary").isVisible()).isFalse()
  }

  /** Verifies the subject has a secondary menu. */
  fun hasSecondaryMenu() {
    val page = checkPageNotNull()
    val locator = page.locator("nav.secondary")
    check("secondary menu exists").that(locator.count() > 0).isTrue()
    check("secondary menu visibility").that(locator.isVisible()).isTrue()
  }

  /** Verifies the subject does not have a secondary menu. */
  fun hasNoSecondaryMenu() {
    val page = checkPageNotNull()
    check("secondary menu").that(page.locator("nav.secondary").isVisible()).isFalse()
  }

  /** Verifies the subject has a menu item with the given [label]. */
  fun hasMenuItem(label: String) {
    val page = checkPageNotNull()
    check("menu item with label '$label'")
        .that(page.locator("nav a, nav summary").filter(Locator.FilterOptions().setHasText(label)).isVisible())
        .isTrue()
  }

  private fun checkPageNotNull(): Page {
    check("nullability").that(actual).isNotNull()
    return actual!!
  }

  companion object {

    /**
     * Factory for [PageSubject].
     *
     * Example usage:
     * ```kotlin
     * assertWithMessage("uri not foo").about(pages).that(page).hasUri(URI.create("http://foo.com"))
     * ```
     */
    private val pages =
        Factory<PageSubject, Page> { metadata, actual -> PageSubject(metadata, actual) }

    /**
     * Creates a [PageSubject] that makes assertions on [page].
     *
     * Example usage:
     * ```kotlin
     * assertThat(page).hasUri(URI.create("http://foo.com"))
     * ```
     */
    @JvmStatic fun assertThat(page: Page): PageSubject = assertAbout(pages).that(page)
  }
}
