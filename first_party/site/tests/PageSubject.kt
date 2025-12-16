package com.jackbradshaw.site.tests

import com.google.common.truth.FailureMetadata
import com.google.common.truth.Subject
import com.google.common.truth.Subject.Factory
import com.google.common.truth.Truth.assertAbout
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
    check("nullability").that(actual).isNotNull()
    val page = actual!!

    val actualUri = page.url().removePrefix("/").removeSuffix("/")
    val expectedUri = uri.toString().removePrefix("/").removeSuffix("/")

    check("uri (trailing slashes removed)").that(actualUri).isEqualTo(expectedUri)
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
