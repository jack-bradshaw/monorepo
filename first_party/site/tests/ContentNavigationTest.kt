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
        itemLabel = "Restructuring",
        expectedDestinationPagePath = URI.create("/gallery/item/restructuring_2024"))
  }

  @Test
  fun clickGalleryItem_subjectsPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/gallery/subjects"),
        itemLabel = "Genesis Ex Nihilo",
        expectedDestinationPagePath = URI.create("/gallery/series/genesis_ex_nihilo"))
  }

  @Test
  fun clickGalleryItem_palettesPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/gallery/palettes"),
        itemLabel = "Singularity",
        expectedDestinationPagePath = URI.create("/gallery/item/singularity_2025"))
  }

  @Test
  fun clickGalleryItem_chronologicalPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/gallery/chronological"),
        itemLabel = "Singularity",
        expectedDestinationPagePath = URI.create("/gallery/item/singularity_2025"))
  }

  @Test
  fun clickJournalItem_highlightsPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/journal/highlights"),
        itemLabel = "Into the Subverse",
        expectedDestinationPagePath = URI.create("/journal/item/into-the-subverse"))
  }

  @Test
  fun clickJournalItem_topicsPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/journal/topics"),
        itemLabel = "Into the Subverse",
        expectedDestinationPagePath = URI.create("/journal/item/into-the-subverse"))
  }

  @Test
  fun clickJournalItem_seriesPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/journal/series"),
        itemLabel = "Death of a Critic",
        expectedDestinationPagePath = URI.create("/journal/item/death-of-a-critic"))
  }

  @Test
  fun clickJournalItem_genresPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/journal/genres"),
        itemLabel = "Sports Bar",
        expectedDestinationPagePath = URI.create("/journal/item/sports-bar"))
  }

  @Test
  fun clickJournalItem_chronologicalPage_opensItem() {
    runClickInternalLinkTest(
        startPagePath = URI.create("/journal/chronological"),
        itemLabel = "Death of a Critic",
        expectedDestinationPagePath = URI.create("/journal/item/death-of-a-critic"))
  }

  @Test
  fun clickRepositoryItem_highlightsPage_opensItem() {
    runClickExternalLinkTest(
        startPagePath = URI.create("/repository/highlights"),
        itemLabel = "KMonkey",
        destinationUri =
            URI.create("https://github.com/jack-bradshaw/main/tree/main/first_party/kmonkey"))
  }

  @Test
  fun clickRepositoryItem_locationsPage_opensItem() {
    runClickExternalLinkTest(
        startPagePath = URI.create("/repository/locations"),
        itemLabel = "AutoFactory",
        destinationUri =
            URI.create("https://github.com/jack-bradshaw/main/tree/main/first_party/autofactory"))
  }

  @Test
  fun clickRepositoryItem_technologiesPage_opensItem() {
    runClickExternalLinkTest(
        startPagePath = URI.create("/repository/technologies"),
        itemLabel = "AutoFactory",
        destinationUri =
            URI.create("https://github.com/jack-bradshaw/main/tree/main/first_party/autofactory"))
  }

  @Test
  fun clickAboutItem_networkItem_opensItem() {
    runClickExternalLinkTest(
        startPagePath = URI.create("/about"),
        itemLabel = "Bluesky",
        destinationUri = URI.create("https://bsky.app/profile/jack-bradshaw.com"))
  }

  @Test
  fun clickAboutItem_careerItem_opensItem() {
    runClickExternalLinkTest(
        startPagePath = URI.create("/about/career/item/waymo"),
        itemLabel = "Waymo",
        destinationUri =
            URI.create("https://play.google.com/store/apps/details?id=com.waymo.carapp"))
  }

  /**
   * Opens [startPagePath] in a browser, scrolls until the item with [itemLabel] is visible in the
   * window, clicks the item, then verifies the URL of the opened page matches
   * [expectedDestinationPagePath]. Both paths must be relative to the root of the site.
   */
  private fun runClickInternalLinkTest(
      startPagePath: URI,
      itemLabel: String,
      expectedDestinationPagePath: URI
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
  private fun runClickExternalLinkTest(startPagePath: URI, itemLabel: String, destinationUri: URI) {
    harness.setup(ScreenWidth.MEDIUM)

    val page = harness.openPage(startPagePath)
    val locator = harness.findElement(page, "a:has-text('${itemLabel}')")
    val popup = page.waitForPopup { locator.click() }

    assertWithMessage("Page URI").that(popup.url()).isEqualTo(destinationUri.toString())
  }
}
