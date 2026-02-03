package com.jackbradshaw.site.tests

import com.google.common.truth.Truth.assertWithMessage
import com.jackbradshaw.site.tests.PageSubject.Companion.assertThat
import java.net.URI
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests the functional routing (navigation) for in-page content items.
 *
 * The tests cover the following across a variety of screen sizes:
 * 
 * - Internal links (e.g. gallery items, gallery series, career items).
 * - External links (e.g. repository items, network profiles, etc).
 *
 * Caveats:
 * 
 * - These tests exercise content links on pages but do not exercise the menus.
 * - These tests verify the correct page was opened but do not make assertions about the destination
 *   itself (e.g. a 404 is considered "correct" if the link points to a 404).
 */
@RunWith(JUnit4::class)
class ContentBehaviourTest {

  @get:Rule val testCaseName = TestName()

  private val harness = TestHarness()

  @After
  fun tearDown() {
    harness.tearDown()
  }

  @Test
  fun indexPage_largeScreen_clickGalleryHighlights_pageOpens() {
    runInternalLinkTest(
      size = ScreenWidth.LARGE,
      startPagePath = URI.create("/"),
      itemLabel = "gallery",
      expectedDestinationPagePath = URI.create("/gallery/highlights/")
    )
  }

  @Test
  fun galleryHighlightsPage_mediumScreen_clickItem_pageOpens() {
    runInternalLinkTest(
        ScreenWidth.MEDIUM,
        startPagePath = URI.create("/gallery/highlights"),
        itemLabel = "Restructuring",
        expectedDestinationPagePath = URI.create("/gallery/item/restructuring_2024"))
  }

  @Test
  fun gallerySubjectsPage_mediumScreen_clickSeries_pageOpens() {
    runInternalLinkTest(
        ScreenWidth.MEDIUM,
        startPagePath = URI.create("/gallery/subjects"),
        itemLabel = "Genesis Ex Nihilo",
        expectedDestinationPagePath = URI.create("/gallery/series/genesis_ex_nihilo"))
  }

  @Test
  fun galleryPalettesPage_mediumScreen_clickItem_pageOpens() {
    runInternalLinkTest(
        ScreenWidth.MEDIUM,
        startPagePath = URI.create("/gallery/palettes"),
        itemLabel = "Singularity",
        expectedDestinationPagePath = URI.create("/gallery/item/singularity_2025"))
  }

  @Test
  fun galleryChronologicalPage_mediumScreen_clickItem_pageOpens() {
    runInternalLinkTest(
        ScreenWidth.MEDIUM,
        startPagePath = URI.create("/gallery/chronological"),
        itemLabel = "Singularity",
        expectedDestinationPagePath = URI.create("/gallery/item/singularity_2025"))
  }

  @Test
  fun journalHighlightsPage_mediumScreen_clickItem_pageOpens() {
    runInternalLinkTest(
        ScreenWidth.MEDIUM,
        startPagePath = URI.create("/journal/highlights"),
        itemLabel = "Into the Subverse",
        expectedDestinationPagePath = URI.create("/journal/item/into-the-subverse"))
  }

  @Test
  fun journalTopicsPage_mediumScreen_clickItem_pageOpens() {
    runInternalLinkTest(
        ScreenWidth.MEDIUM,
        startPagePath = URI.create("/journal/topics"),
        itemLabel = "Into the Subverse",
        expectedDestinationPagePath = URI.create("/journal/item/into-the-subverse"))
  }

  @Test
  fun journalSeriesPage_largeScreen_clickSeriesItem_pageOpens() {
    runInternalLinkTest(
        ScreenWidth.LARGE,
        startPagePath = URI.create("/journal/series"),
        itemLabel = "Death of a Critic",
        expectedDestinationPagePath = URI.create("/journal/item/death-of-a-critic"))
  }

  @Test
  fun journalGenresPage_mediumScreen_clickItem_pageOpens() {
    runInternalLinkTest(
        ScreenWidth.MEDIUM,
        startPagePath = URI.create("/journal/genres"),
        itemLabel = "Sports Bar",
        expectedDestinationPagePath = URI.create("/journal/item/sports-bar"))
  }

  @Test
  fun journalChronologicalPage_mediumScreen_clickItem_pageOpens() {
    runInternalLinkTest(
        ScreenWidth.MEDIUM,
        startPagePath = URI.create("/journal/chronological"),
        itemLabel = "Death of a Critic",
        expectedDestinationPagePath = URI.create("/journal/item/death-of-a-critic"))
  }

  @Test
  fun repositoryHighlightsPage_mediumScreen_clickExternalItem_pageOpens() {
    runExternalLinkTest(
        ScreenWidth.MEDIUM,
        startPagePath = URI.create("/repository/highlights"),
        itemLabel = "KMonkey",
        destinationUri =
            URI.create("https://github.com/jack-bradshaw/monorepo/tree/main/first_party/kmonkey"))
  }

  @Test
  fun repositoryLocationsPage_mediumScreen_clickExternalItem_pageOpens() {
    runExternalLinkTest(
        ScreenWidth.MEDIUM,
        startPagePath = URI.create("/repository/locations"),
        itemLabel = "AutoFactory",
        destinationUri =
            URI.create("https://github.com/jack-bradshaw/monorepo/tree/main/first_party/autofactory"))
  }

  @Test
  fun repositoryTechnologiesPage_mediumScreen_clickExternalItem_pageOpens() {
    runExternalLinkTest(
        ScreenWidth.MEDIUM,
        startPagePath = URI.create("/repository/technologies"),
        itemLabel = "AutoFactory",
        destinationUri =
            URI.create("https://github.com/jack-bradshaw/monorepo/tree/main/first_party/autofactory"))
  }

  @Test
  fun aboutPage_smallScreen_clickSocialLink_pageOpens() {
    runExternalLinkTest(
        ScreenWidth.SMALL,
        startPagePath = URI.create("/about"),
        itemLabel = "Bluesky",
        destinationUri = URI.create("https://bsky.app/profile/jack-bradshaw.com"))
  }

  @Test
  fun aboutPage_smallScreen_clickWaymoLink_pageOpens() {
    runExternalLinkTest(
        ScreenWidth.SMALL,
        startPagePath = URI.create("/about/career/item/waymo"),
        itemLabel = "Waymo",
        destinationUri = URI.create("https://play.google.com/store/apps/details?id=com.waymo.carapp"))
  }

  private fun runInternalLinkTest(
      size: ScreenWidth,
      startPagePath: URI,
      itemLabel: String,
      expectedDestinationPagePath: URI
  ) {
    harness.setup(size)
    val page =
        harness.openPage(startPagePath).also {
          it.expandAllDetailsContentBlocks()
          it.findElement("a:has-text('$itemLabel')").click()
        }

    assertThat(page).hasUri(harness.getServerEndpoint().resolve(expectedDestinationPagePath))
  }

  private fun runExternalLinkTest(
      size: ScreenWidth,
      startPagePath: URI,
      itemLabel: String,
      destinationUri: URI
  ) {
    harness.setup(size)
    val page = harness.openPage(startPagePath).also { it.expandAllDetailsContentBlocks() }
    val locator = page.findElement("a:has-text('$itemLabel')")
    val popup = page.waitForPopup { locator.click() }

    assertWithMessage("Page URI").that(popup.url()).isEqualTo(destinationUri.toString())
  }
}
