package com.jackbradshaw.site.tests

import com.jackbradshaw.site.tests.PageSubject.Companion.assertThat
import java.net.URI
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * These tests verify the navigation behaviour of menus across the site (e.g. clicking the home menu
 * button opens the home page).
 */
@RunWith(JUnit4::class)
class MenuNavigationTest {

  private val harness = TestHarness()

  @After
  fun tearDown() {
    harness.tearDown()
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_home_opensHome() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Home,
        startPagePath = URI.create("/gallery/highlights"),
        destinationPagePath = URI.create("/"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_gallery_opensGalleryHighlights() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Gallery,
        startPagePath = URI.create("/"),
        destinationPagePath = URI.create("/gallery/highlights"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_journal_opensJournalHighlights() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Journal,
        startPagePath = URI.create("/"),
        destinationPagePath = URI.create("/journal/highlights"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_repository_opensRepositoryHighlights() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Repository,
        startPagePath = URI.create("/"),
        destinationPagePath = URI.create("/repository/highlights"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_about_opensAbout() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.About, startPagePath = URI.create("/"), destinationPagePath = URI.create("/about"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_galleryHighlights_opensGalleryHighlights() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Gallery.Highlights,
        startPagePath = URI.create("/gallery/subjects"),
        destinationPagePath = URI.create("/gallery/highlights"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_gallerySubjects_opensGallerySubjects() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Gallery.Subjects,
        startPagePath = URI.create("/gallery/highlights"),
        destinationPagePath = URI.create("/gallery/subjects"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_galleryPalettes_opensGalleryPalettes() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Gallery.Palettes,
        startPagePath = URI.create("/gallery/highlights"),
        destinationPagePath = URI.create("/gallery/palettes"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_galleryChronological_opensGalleryChronological() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Gallery.Chronological,
        startPagePath = URI.create("/gallery/highlights"),
        destinationPagePath = URI.create("/gallery/chronological"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_journalHighlights_opensJournalHighlights() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Journal.Highlights,
        startPagePath = URI.create("/journal/topics"),
        destinationPagePath = URI.create("/journal/highlights"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_journalTopics_opensJournalTopics() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Journal.Topics,
        startPagePath = URI.create("/journal/highlights"),
        destinationPagePath = URI.create("/journal/topics"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_journalSeries_opensJournalSeries() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Journal.Series,
        startPagePath = URI.create("/journal/highlights"),
        destinationPagePath = URI.create("/journal/series"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_journalGenres_opensJournalGenres() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Journal.Genres,
        startPagePath = URI.create("/journal/highlights"),
        destinationPagePath = URI.create("/journal/genres"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_journalChronological_opensJournalChronological() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Journal.Chronological,
        startPagePath = URI.create("/journal/highlights"),
        destinationPagePath = URI.create("/journal/chronological"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_repositoryHighlights_opensRepositoryHighlights() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Repository.Highlights,
        startPagePath = URI.create("/repository/locations"),
        destinationPagePath = URI.create("/repository/highlights"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_repositoryLocations_opensRepositoryLocations() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Repository.Locations,
        startPagePath = URI.create("/repository/highlights"),
        destinationPagePath = URI.create("/repository/locations"))
  }

  @Test
  fun clickMenuItem_alwaysExpandedMenu_repositoryTechnologies_opensRepositoryTechnologies() {
    runAlwaysExpandedMenuItemTest(
        MenuItem.Repository.Technologies,
        startPagePath = URI.create("/repository/highlights"),
        destinationPagePath = URI.create("/repository/technologies"))
  }

  @Test
  fun clickMenuItem_collapsedPrimaryMenu_home_opensHome() {
    runCollapsiblePrimaryMenuItemTest(
        MenuItem.Home,
        startPagePath = URI.create("/gallery/highlights"),
        destinationPagePath = URI.create("/"))
  }

  @Test
  fun clickMenuItem_collapsedPrimaryMenu_gallery_opensGalleryHighlights() {
    runCollapsiblePrimaryMenuItemTest(
        MenuItem.Gallery,
        startPagePath = URI.create("/"),
        destinationPagePath = URI.create("/gallery/highlights"))
  }

  @Test
  fun clickMenuItem_collapsedPrimaryMenu_journal_opensJournalHighlights() {
    runCollapsiblePrimaryMenuItemTest(
        MenuItem.Journal,
        startPagePath = URI.create("/"),
        destinationPagePath = URI.create("/journal/highlights"))
  }

  @Test
  fun clickMenuItem_collapsedPrimaryMenu_repository_opensRepositoryHighlights() {
    runCollapsiblePrimaryMenuItemTest(
        MenuItem.Repository,
        startPagePath = URI.create("/"),
        destinationPagePath = URI.create("/repository/highlights"))
  }

  @Test
  fun clickMenuItem_collapsedPrimaryMenu_about_opensAbout() {
    runCollapsiblePrimaryMenuItemTest(
        MenuItem.About, startPagePath = URI.create("/"), destinationPagePath = URI.create("/about"))
  }

  @Test
  fun clickMenuItem_collapsedSecondaryMenu_galleryHighlights_opensGalleryHighlights() {
    runCollapsibleSecondaryMenuItemTest(
        MenuItem.Gallery.Highlights,
        startPagePath = URI.create("/gallery/subjects"),
        destinationPagePath = URI.create("/gallery/highlights"))
  }

  @Test
  fun clickMenuItem_collapsedSecondaryMenu_gallerySubjects_opensGallerySubjects() {
    runCollapsibleSecondaryMenuItemTest(
        MenuItem.Gallery.Subjects,
        startPagePath = URI.create("/gallery/highlights"),
        destinationPagePath = URI.create("/gallery/subjects"))
  }

  @Test
  fun clickMenuItem_collapsedSecondaryMenu_galleryPalettes_opensGalleryPalettes() {
    runCollapsibleSecondaryMenuItemTest(
        MenuItem.Gallery.Palettes,
        startPagePath = URI.create("/gallery/highlights"),
        destinationPagePath = URI.create("/gallery/palettes"))
  }

  @Test
  fun clickMenuItem_collapsedSecondaryMenu_galleryChronological_opensGalleryChronological() {
    runCollapsibleSecondaryMenuItemTest(
        MenuItem.Gallery.Chronological,
        startPagePath = URI.create("/gallery/highlights"),
        destinationPagePath = URI.create("/gallery/chronological"))
  }

  @Test
  fun clickMenuItem_collapsedSecondaryMenu_journalHighlights_opensJournalHighlights() {
    runCollapsibleSecondaryMenuItemTest(
        MenuItem.Journal.Highlights,
        startPagePath = URI.create("/journal/topics"),
        destinationPagePath = URI.create("/journal/highlights"))
  }

  @Test
  fun clickMenuItem_collapsedSecondaryMenu_journalTopics_opensJournalTopics() {
    runCollapsibleSecondaryMenuItemTest(
        MenuItem.Journal.Topics,
        startPagePath = URI.create("/journal/highlights"),
        destinationPagePath = URI.create("/journal/topics"))
  }

  @Test
  fun clickMenuItem_collapsedSecondaryMenu_journalSeries_opensJournalSeries() {
    runCollapsibleSecondaryMenuItemTest(
        MenuItem.Journal.Series,
        startPagePath = URI.create("/journal/highlights"),
        destinationPagePath = URI.create("/journal/series"))
  }

  @Test
  fun clickMenuItem_collapsedSecondaryMenu_journalGenres_opensJournalGenres() {
    runCollapsibleSecondaryMenuItemTest(
        MenuItem.Journal.Genres,
        startPagePath = URI.create("/journal/highlights"),
        destinationPagePath = URI.create("/journal/genres"))
  }

  @Test
  fun clickMenuItem_collapsedSecondaryMenu_journalChronological_opensJournalChronological() {
    runCollapsibleSecondaryMenuItemTest(
        MenuItem.Journal.Chronological,
        startPagePath = URI.create("/journal/highlights"),
        destinationPagePath = URI.create("/journal/chronological"))
  }

  @Test
  fun clickMenuItem_collapsedSecondaryMenu_repositoryHighlights_opensRepositoryHighlights() {
    runCollapsibleSecondaryMenuItemTest(
        MenuItem.Repository.Highlights,
        startPagePath = URI.create("/repository/locations"),
        destinationPagePath = URI.create("/repository/highlights"))
  }

  @Test
  fun clickMenuItem_collapsedSecondaryMenu_repositoryLocations_opensRepositoryLocations() {
    runCollapsibleSecondaryMenuItemTest(
        MenuItem.Repository.Locations,
        startPagePath = URI.create("/repository/highlights"),
        destinationPagePath = URI.create("/repository/locations"))
  }

  @Test
  fun clickMenuItem_collapsedSecondaryMenu_repositoryTechnologies_opensRepositoryTechnologies() {
    runCollapsibleSecondaryMenuItemTest(
        MenuItem.Repository.Technologies,
        startPagePath = URI.create("/repository/highlights"),
        destinationPagePath = URI.create("/repository/technologies"))
  }

  /**
   * Opens [startPagePath] on a medium screen, clicks [menuItem], and verifies the browser opened
   * [destinationPagePath].
   */
  private fun runAlwaysExpandedMenuItemTest(
      menuItem: MenuItem,
      startPagePath: URI = URI.create("/"),
      destinationPagePath: URI
  ) {
    harness.setup(ScreenWidth.MEDIUM)
    val page = harness.openPage(startPagePath)

    harness.findElement(page, "text=${menuItem.uiLabel}").click()

    assertThat(page).hasUri(harness.endpoint().resolve(destinationPagePath))
  }

  /**
   * Opens [startPagePath] on a small screen, opens the primary navigation panel, clicks [menuItem],
   * and verifies the browser opened [destinationPagePath].
   */
  private fun runCollapsiblePrimaryMenuItemTest(
      menuItem: MenuItem,
      startPagePath: URI = URI.create("/"),
      destinationPagePath: URI
  ) {
    harness.setup(ScreenWidth.SMALL)
    val page = harness.openPage(startPagePath)

    harness.findElement(page, "nav.primary summary").click()
    harness.findElement(page, "text=${menuItem.uiLabel}").click()

    assertThat(page).hasUri(harness.endpoint().resolve(destinationPagePath))
  }

  /**
   * Opens [startPagePath] on a small screen, opens the secondary navigation panel, clicks
   * [menuItem], and verifies the browser opened [destinationPagePath].
   */
  private fun runCollapsibleSecondaryMenuItemTest(
      menuItem: MenuItem,
      startPagePath: URI = URI.create("/"),
      destinationPagePath: URI
  ) {
    harness.setup(ScreenWidth.SMALL)
    val page = harness.openPage(startPagePath)

    harness.findElement(page, "nav.secondary summary").click()
    harness.findElement(page, "text=${menuItem.uiLabel}").click()

    assertThat(page).hasUri(harness.endpoint().resolve(destinationPagePath))
  }
}

sealed class MenuItem(val uiLabel: String) {
  object Home : MenuItem("Home")

  object Gallery : MenuItem("Gallery") {
    object Highlights : MenuItem("Highlights")

    object Subjects : MenuItem("Subjects")

    object Palettes : MenuItem("Palettes")

    object Chronological : MenuItem("Chronological")
  }

  object Journal : MenuItem("Journal") {
    object Highlights : MenuItem("Highlights")

    object Topics : MenuItem("Topics")

    object Series : MenuItem("Series")

    object Genres : MenuItem("Genres")

    object Chronological : MenuItem("Chronological")
  }

  object Repository : MenuItem("Repository") {
    object Highlights : MenuItem("Highlights")

    object Locations : MenuItem("Locations")

    object Technologies : MenuItem("Technologies")
  }

  object About : MenuItem("About")
}
