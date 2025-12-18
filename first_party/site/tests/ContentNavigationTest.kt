package com.jackbradshaw.site.tests

import com.google.common.truth.Truth.assertWithMessage
import com.jackbradshaw.site.tests.PageSubject.Companion.assertThat
import java.net.URI
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * These tests verify the navigation behaviours of content items across the site (e.g. clicking a
 * gallery item from a the gallery highlights page opens the associated gallery item). Internal
 * pages are expected to open in the same tab, and external pages are exected to open in a new tab.
 */
@RunWith(JUnit4::class)
class ContentNavigationTest {

  private val harness = TestHarness()

  @After
  fun tearDown() {
    harness.tearDown()
  }

  @Test
  fun clickGalleryItem_highlightsPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/gallery/highlights"),
        expectedDestinationPagePath = URI.create("/gallery/item/restructuring_2024"),
        itemLabel = "Restructuring")
  }

  @Test
  fun clickGalleryItem_curatedPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/gallery/curated"),
        expectedDestinationPagePath = URI.create("/gallery/series/genesis_ex_nihilo"),
        itemLabel = "Genesis Ex Nihilo")
  }

  @Test
  fun clickGalleryItem_palettesPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/gallery/palettes"),
        expectedDestinationPagePath = URI.create("/gallery/item/singularity_2025"),
        itemLabel = "Singularity")
  }

  @Test
  fun clickGalleryItem_chronologicalPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/gallery/chronological"),
        expectedDestinationPagePath = URI.create("/gallery/item/singularity_2025"),
        itemLabel = "Singularity")
  }

  @Test
  fun clickJournalItem_highlightsPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/journal/highlights"),
        expectedDestinationPagePath = URI.create("/journal/item/sports-bar"),
        itemLabel = "Sports Bar")
  }

  @Test
  fun clickJournalItem_topicsPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/journal/topics"),
        expectedDestinationPagePath = URI.create("/journal/item/pragmatic-altruism"),
        itemLabel = "Pragmatic Altruism")
  }

  @Test
  fun clickJournalItem_seriesPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/journal/series"),
        expectedDestinationPagePath = URI.create("/journal/item/death-of-a-critic"),
        itemLabel = "Death of a Critic")
  }

  @Test
  fun clickJournalItem_genresPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/journal/genres"),
        expectedDestinationPagePath = URI.create("/journal/item/sports-bar"),
        itemLabel = "Sports Bar")
  }

  @Test
  fun clickJournalItem_chronologicalPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/journal/chronological"),
        expectedDestinationPagePath = URI.create("/journal/item/death-of-a-critic"),
        itemLabel = "Death of a Critic")
  }

  @Test
  fun clickRepositoryItem_highlightsPage_opensItem() {
    runClickExternalLinkTest(
        startPagePath = URI.create("/repository/highlights"),
        destinationUri =
            URI.create("https://github.com/jack-bradshaw/monorepo/tree/main/first_party/kmonkey"),
        itemLabel = "KMonkey")
  }

  @Test
  fun clickRepositoryItem_locationPage_opensItem() {
    runClickExternalLinkTest(
        startPagePath = URI.create("/repository/locations"),
        destinationUri =
            URI.create(
                "https://github.com/jack-bradshaw/monorepo/tree/main/first_party/autofactory"),
        itemLabel = "AutoFactory")
  }

  @Test
  fun clickRepositoryItem_technologyPage_opensItem() {
    runClickExternalLinkTest(
        startPagePath = URI.create("/repository/technologies"),
        destinationUri =
            URI.create(
                "https://github.com/jack-bradshaw/monorepo/tree/main/first_party/autofactory"),
        itemLabel = "AutoFactory")
  }

  @Test
  fun clickAboutItem_networkItem_opensItem() {
    runClickExternalLinkTest(
        startPagePath = URI.create("/about"),
        destinationUri = URI.create("https://bsky.app/profile/jack-bradshaw.com"),
        itemLabel = "Bluesky")
  }

  @Test
  fun clickAboutItem_careerItem_opensItem() {
    runClickExternalLinkTest(
        startPagePath = URI.create("/about/career/item/waymo"),
        destinationUri =
            URI.create("https://play.google.com/store/apps/details?id=com.waymo.carapp"),
        itemLabel = "Waymo")
  }

  /**
   * Opens [startPagePath] in a browser, scrolls until the item with [itemLabel] is visible in the
   * window, clicks the item, then verifies the URL of the opened page matches
   * [expectedDestinationPagePath]. Both paths must be relative to the root of the site.
   */
  private fun runClickInternalLinkTest(
      startPagePath: URI,
      expectedDestinationPagePath: URI,
      itemLabel: String
  ) {
    harness.setup(ScreenWidth.MEDIUM)
    val page = harness.openPage(startPagePath)

    harness.findElement(page, "a:has-text('${itemLabel}')").click()

    assertThat(page).hasUri(harness.endpoint().resolve(expectedDestinationPagePath))
  }

  /**
   * Opens [startPagePath] in a browser, scrolls until the item with [itemLabel] is visible in the
   * window, clicks the item, then verifies the URL of the opened page matches [destinationUri].
   * Start page path must be relative to the root of the site, and destination path must be
   * absolute.
   */
  private fun runClickExternalLinkTest(startPagePath: URI, destinationUri: URI, itemLabel: String) {
    harness.setup(ScreenWidth.MEDIUM)

    val page = harness.openPage(startPagePath)
    val locator = harness.findElement(page, "a:has-text('${itemLabel}')")
    val popup = page.waitForPopup { locator.click() }

    assertWithMessage("Page URI").that(popup.url()).isEqualTo(destinationUri.toString())
  }
}
