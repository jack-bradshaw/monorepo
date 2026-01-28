package com.jackbradshaw.site.tests

import com.jackbradshaw.site.tests.PageSubject.Companion.assertThat
import com.microsoft.playwright.Page
import java.net.URI
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * These tests verify the navigation behavior of menus across the site (e.g. clicking the home menu
 * button opens the home page). Popup menu tests use a small screen and expanded menu tests use a
 * medium screen because the menu type is controlled by screen size.
 */
@RunWith(JUnit4::class)
class MenuTest {

  /** Used to derive the name of the golden file. */
  @get:Rule val testCaseName = TestName()

  private val harness = TestHarness()

  @After
  fun tearDown() {
    harness.tearDown()
  }

  @Test
  fun primaryPopupMenu_matchesGolden() {
    harness.setup(ScreenWidth.SMALL)

    val page = harness.openPage(URI.create("/"))
    page.waitForLoad()
    openPrimaryPopup(page)

    harness.checkScreendiff(page, generateGoldenName())
  }

  @Test
  fun secondaryPopupMenu_matchesGolden() {
    harness.setup(ScreenWidth.SMALL)

    val page = harness.openPage(URI.create("/gallery/highlights"))
    page.waitForLoad()
    openSecondaryPopup(page)

    harness.checkScreendiff(page, generateGoldenName())
  }

  @Test
  fun clickMenuItem_expandedMenu_home_opensHome() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/gallery/highlights"),
        menuItem = MenuItem.Home,
        destinationPagePath = URI.create("/"))
  }

  @Test
  fun clickMenuItem_expandedMenu_gallery_opensGalleryHighlights() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/"),
        menuItem = MenuItem.Gallery,
        destinationPagePath = URI.create("/gallery/highlights"))
  }

  @Test
  fun clickMenuItem_expandedMenu_journal_opensJournalHighlights() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/"),
        menuItem = MenuItem.Journal,
        destinationPagePath = URI.create("/journal/highlights"))
  }

  @Test
  fun clickMenuItem_expandedMenu_repository_opensRepositoryHighlights() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/"),
        menuItem = MenuItem.Repository,
        destinationPagePath = URI.create("/repository/highlights"))
  }

  @Test
  fun clickMenuItem_expandedMenu_about_opensAbout() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/"),
        menuItem = MenuItem.About,
        destinationPagePath = URI.create("/about"))
  }

  @Test
  fun clickMenuItem_expandedMenu_galleryHighlights_opensGalleryHighlights() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/gallery/subjects"),
        menuItem = MenuItem.Gallery.Highlights,
        destinationPagePath = URI.create("/gallery/highlights"))
  }

  @Test
  fun clickMenuItem_expandedMenu_gallerySubjects_opensGallerySubjects() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/gallery/highlights"),
        menuItem = MenuItem.Gallery.Subjects,
        destinationPagePath = URI.create("/gallery/subjects"))
  }

  @Test
  fun clickMenuItem_expandedMenu_galleryPalettes_opensGalleryPalettes() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/gallery/highlights"),
        menuItem = MenuItem.Gallery.Palettes,
        destinationPagePath = URI.create("/gallery/palettes"))
  }

  @Test
  fun clickMenuItem_expandedMenu_galleryChronological_opensGalleryChronological() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/gallery/highlights"),
        menuItem = MenuItem.Gallery.Chronological,
        destinationPagePath = URI.create("/gallery/chronological"))
  }

  @Test
  fun clickMenuItem_expandedMenu_journalHighlights_opensJournalHighlights() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/journal/topics"),
        menuItem = MenuItem.Journal.Highlights,
        destinationPagePath = URI.create("/journal/highlights"))
  }

  @Test
  fun clickMenuItem_expandedMenu_journalTopics_opensJournalTopics() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/journal/highlights"),
        menuItem = MenuItem.Journal.Topics,
        destinationPagePath = URI.create("/journal/topics"))
  }

  @Test
  fun clickMenuItem_expandedMenu_journalSeries_opensJournalSeries() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/journal/highlights"),
        menuItem = MenuItem.Journal.Series,
        destinationPagePath = URI.create("/journal/series"))
  }

  @Test
  fun clickMenuItem_expandedMenu_journalGenres_opensJournalGenres() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/journal/highlights"),
        menuItem = MenuItem.Journal.Genres,
        destinationPagePath = URI.create("/journal/genres"))
  }

  @Test
  fun clickMenuItem_expandedMenu_journalChronological_opensJournalChronological() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/journal/highlights"),
        menuItem = MenuItem.Journal.Chronological,
        destinationPagePath = URI.create("/journal/chronological"))
  }

  @Test
  fun clickMenuItem_expandedMenu_repositoryHighlights_opensRepositoryHighlights() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/repository/locations"),
        menuItem = MenuItem.Repository.Highlights,
        destinationPagePath = URI.create("/repository/highlights"))
  }

  @Test
  fun clickMenuItem_expandedMenu_repositoryLocations_opensRepositoryLocations() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/repository/highlights"),
        menuItem = MenuItem.Repository.Locations,
        destinationPagePath = URI.create("/repository/locations"))
  }

  @Test
  fun clickMenuItem_expandedMenu_repositoryTechnologies_opensRepositoryTechnologies() {
    runExpandedMenuItemTest(
        startPagePath = URI.create("/repository/highlights"),
        menuItem = MenuItem.Repository.Technologies,
        destinationPagePath = URI.create("/repository/technologies"))
  }

  @Test
  fun clickMenuItem_primaryPopupMenu_home_opensHome() {
    runPrimaryPopupMenuItemTest(
        startPagePath = URI.create("/gallery/highlights"),
        menuItem = MenuItem.Home,
        destinationPagePath = URI.create("/"))
  }

  @Test
  fun clickMenuItem_primaryPopupMenu_gallery_opensGalleryHighlights() {
    runPrimaryPopupMenuItemTest(
        startPagePath = URI.create("/"),
        menuItem = MenuItem.Gallery,
        destinationPagePath = URI.create("/gallery/highlights"))
  }

  @Test
  fun clickMenuItem_primaryPopupMenu_journal_opensJournalHighlights() {
    runPrimaryPopupMenuItemTest(
        startPagePath = URI.create("/"),
        menuItem = MenuItem.Journal,
        destinationPagePath = URI.create("/journal/highlights"))
  }

  @Test
  fun clickMenuItem_primaryPopupMenu_repository_opensRepositoryHighlights() {
    runPrimaryPopupMenuItemTest(
        startPagePath = URI.create("/"),
        menuItem = MenuItem.Repository,
        destinationPagePath = URI.create("/repository/highlights"))
  }

  @Test
  fun clickMenuItem_primaryPopupMenu_about_opensAbout() {
    runPrimaryPopupMenuItemTest(
        startPagePath = URI.create("/"),
        menuItem = MenuItem.About,
        destinationPagePath = URI.create("/about"))
  }

  @Test
  fun clickMenuItem_secondaryPopupMenu_galleryHighlights_opensGalleryHighlights() {
    runSecondaryPopupMenuItemTest(
        startPagePath = URI.create("/gallery/subjects"),
        menuItem = MenuItem.Gallery.Highlights,
        destinationPagePath = URI.create("/gallery/highlights"))
  }

  @Test
  fun clickMenuItem_secondaryPopupMenu_gallerySubjects_opensGallerySubjects() {
    runSecondaryPopupMenuItemTest(
        startPagePath = URI.create("/gallery/highlights"),
        menuItem = MenuItem.Gallery.Subjects,
        destinationPagePath = URI.create("/gallery/subjects"))
  }

  @Test
  fun clickMenuItem_secondaryPopupMenu_galleryPalettes_opensGalleryPalettes() {
    runSecondaryPopupMenuItemTest(
        startPagePath = URI.create("/gallery/highlights"),
        menuItem = MenuItem.Gallery.Palettes,
        destinationPagePath = URI.create("/gallery/palettes"))
  }

  @Test
  fun clickMenuItem_secondaryPopupMenu_galleryChronological_opensGalleryChronological() {
    runSecondaryPopupMenuItemTest(
        startPagePath = URI.create("/gallery/highlights"),
        menuItem = MenuItem.Gallery.Chronological,
        destinationPagePath = URI.create("/gallery/chronological"))
  }

  @Test
  fun clickMenuItem_secondaryPopupMenu_journalHighlights_opensJournalHighlights() {
    runSecondaryPopupMenuItemTest(
        startPagePath = URI.create("/journal/topics"),
        menuItem = MenuItem.Journal.Highlights,
        destinationPagePath = URI.create("/journal/highlights"))
  }

  @Test
  fun clickMenuItem_secondaryPopupMenu_journalTopics_opensJournalTopics() {
    runSecondaryPopupMenuItemTest(
        startPagePath = URI.create("/journal/highlights"),
        menuItem = MenuItem.Journal.Topics,
        destinationPagePath = URI.create("/journal/topics"))
  }

  @Test
  fun clickMenuItem_secondaryPopupMenu_journalSeries_opensJournalSeries() {
    runSecondaryPopupMenuItemTest(
        startPagePath = URI.create("/journal/highlights"),
        menuItem = MenuItem.Journal.Series,
        destinationPagePath = URI.create("/journal/series"))
  }

  @Test
  fun clickMenuItem_secondaryPopupMenu_journalGenres_opensJournalGenres() {
    runSecondaryPopupMenuItemTest(
        startPagePath = URI.create("/journal/highlights"),
        menuItem = MenuItem.Journal.Genres,
        destinationPagePath = URI.create("/journal/genres"))
  }

  @Test
  fun clickMenuItem_secondaryPopupMenu_journalChronological_opensJournalChronological() {
    runSecondaryPopupMenuItemTest(
        startPagePath = URI.create("/journal/highlights"),
        menuItem = MenuItem.Journal.Chronological,
        destinationPagePath = URI.create("/journal/chronological"))
  }

  @Test
  fun clickMenuItem_secondaryPopupMenu_repositoryHighlights_opensRepositoryHighlights() {
    runSecondaryPopupMenuItemTest(
        startPagePath = URI.create("/repository/locations"),
        menuItem = MenuItem.Repository.Highlights,
        destinationPagePath = URI.create("/repository/highlights"))
  }

  @Test
  fun clickMenuItem_secondaryPopupMenu_repositoryLocations_opensRepositoryLocations() {
    runSecondaryPopupMenuItemTest(
        startPagePath = URI.create("/repository/highlights"),
        menuItem = MenuItem.Repository.Locations,
        destinationPagePath = URI.create("/repository/locations"))
  }

  @Test
  fun clickMenuItem_secondaryPopupMenu_repositoryTechnologies_opensRepositoryTechnologies() {
    runSecondaryPopupMenuItemTest(
        startPagePath = URI.create("/repository/highlights"),
        menuItem = MenuItem.Repository.Technologies,
        destinationPagePath = URI.create("/repository/technologies"))
  }

  /**
   * Opens [startPagePath] on a medium screen, clicks [menuItem], and verifies the browser opened
   * [destinationPagePath].
   */
  private fun runExpandedMenuItemTest(
      startPagePath: URI = URI.create("/"),
      menuItem: MenuItem,
      destinationPagePath: URI
  ) {
    harness.setup(ScreenWidth.MEDIUM)
    val page = harness.openPage(startPagePath)

    page.findElement("text=${menuItem.uiLabel}").click()

    assertThat(page).hasUri(harness.getServerEndpoint().resolve(destinationPagePath))
  }

  /**
   * Opens [startPagePath] on a small screen, opens the primary navigation panel, clicks [menuItem],
   * and verifies the browser opened [destinationPagePath].
   */
  private fun runPrimaryPopupMenuItemTest(
      startPagePath: URI = URI.create("/"),
      menuItem: MenuItem,
      destinationPagePath: URI
  ) {
    harness.setup(ScreenWidth.SMALL)
    val page = harness.openPage(startPagePath)

    openPrimaryPopup(page)
    page.findElement("text=${menuItem.uiLabel}").click()

    assertThat(page).hasUri(harness.getServerEndpoint().resolve(destinationPagePath))
  }

  /**
   * Opens [startPagePath] on a small screen, opens the secondary navigation panel, clicks
   * [menuItem], and verifies the browser opened [destinationPagePath].
   */
  private fun runSecondaryPopupMenuItemTest(
      startPagePath: URI = URI.create("/"),
      menuItem: MenuItem,
      destinationPagePath: URI
  ) {
    harness.setup(ScreenWidth.SMALL)
    val page = harness.openPage(startPagePath)

    openSecondaryPopup(page)
    page.findElement("text=${menuItem.uiLabel}").click()

    assertThat(page).hasUri(harness.getServerEndpoint().resolve(destinationPagePath))
  }

  /**
   * Waits for the primary menu to close (starts open on mobile then closes itself via JS), then
   * clicks the menu button to open it, and waits until it opens.
   */
  private fun openPrimaryPopup(page: Page) {
    page.findElement("nav.primary summary").click()
  }

  /**
   * Waits for the secondary menu to close (starts open on mobile then closes itself via JS), then
   * clicks the menu button to open it, and waits until it opens.
   */
  private fun openSecondaryPopup(page: Page) {
    page.findElement("nav.secondary summary").click()
  }

  /** Generates the golden image file name for the current test. */
  private fun generateGoldenName(): String {
    return "${this::class.simpleName}_${testCaseName.methodName}.png"
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
