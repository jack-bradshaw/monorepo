package com.jackbradshaw.site.tests

import com.jackbradshaw.site.tests.PageSubject.Companion.assertThat
import java.net.URI
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests the navigation behaviours (routing) for the Primary, Secondary, and Contextual menus.
 *
 * The tests cover the following across a variety of screen sizes:
 *
 * - Primary Menu clicks (Home, Gallery, etc.).
 * - Secondary Menu clicks (Highlights, Topics, etc.).
 * - Contextual Menu clicks (Next, Previous, Full Series).
 *
 * Caveats:
 * 
 * - The tests for the popup menu implicitly use [ScreenWidth.SMALL] to enforce the mobile style menu.
 */
@RunWith(JUnit4::class)
class MenuBehaviourTest {

  @get:Rule val testCaseName = TestName()

  private val harness = TestHarness()

  @After
  fun tearDown() {
    harness.tearDown()
  }




  @Test fun primaryMenu_home_smallScreen() = verifyPopupNavigation("/gallery/highlights", MenuType.PRIMARY, "Home", "/")
  @Test fun primaryMenu_home_mediumScreen() = verifyStaticNavigation("/gallery/highlights", ScreenWidth.MEDIUM, MenuType.PRIMARY, "Home", "/")
  @Test fun primaryMenu_home_largeScreen() = verifyStaticNavigation("/gallery/highlights", ScreenWidth.LARGE, MenuType.PRIMARY, "Home", "/")


  @Test fun primaryMenu_gallery_smallScreen() = verifyPopupNavigation("/", MenuType.PRIMARY, "Gallery", "/gallery/highlights")
  @Test fun primaryMenu_gallery_mediumScreen() = verifyStaticNavigation("/", ScreenWidth.MEDIUM, MenuType.PRIMARY, "Gallery", "/gallery/highlights")
  @Test fun primaryMenu_gallery_largeScreen() = verifyStaticNavigation("/", ScreenWidth.LARGE, MenuType.PRIMARY, "Gallery", "/gallery/highlights")


  @Test fun primaryMenu_journal_smallScreen() = verifyPopupNavigation("/", MenuType.PRIMARY, "Journal", "/journal/highlights")
  @Test fun primaryMenu_journal_mediumScreen() = verifyStaticNavigation("/", ScreenWidth.MEDIUM, MenuType.PRIMARY, "Journal", "/journal/highlights")
  @Test fun primaryMenu_journal_largeScreen() = verifyStaticNavigation("/", ScreenWidth.LARGE, MenuType.PRIMARY, "Journal", "/journal/highlights")


  @Test fun primaryMenu_repository_smallScreen() = verifyPopupNavigation("/", MenuType.PRIMARY, "Repository", "/repository/highlights")
  @Test fun primaryMenu_repository_mediumScreen() = verifyStaticNavigation("/", ScreenWidth.MEDIUM, MenuType.PRIMARY, "Repository", "/repository/highlights")
  @Test fun primaryMenu_repository_largeScreen() = verifyStaticNavigation("/", ScreenWidth.LARGE, MenuType.PRIMARY, "Repository", "/repository/highlights")


  @Test fun primaryMenu_about_smallScreen() = verifyPopupNavigation("/", MenuType.PRIMARY, "About", "/about")
  @Test fun primaryMenu_about_mediumScreen() = verifyStaticNavigation("/", ScreenWidth.MEDIUM, MenuType.PRIMARY, "About", "/about")
  @Test fun primaryMenu_about_largeScreen() = verifyStaticNavigation("/", ScreenWidth.LARGE, MenuType.PRIMARY, "About", "/about")





  @Test fun secondaryMenu_galleryHighlights_smallScreen() = verifyPopupNavigation("/gallery/subjects", MenuType.SECONDARY, "Highlights", "/gallery/highlights")
  @Test fun secondaryMenu_galleryHighlights_mediumScreen() = verifyStaticNavigation("/gallery/subjects", ScreenWidth.MEDIUM, MenuType.SECONDARY, "Highlights", "/gallery/highlights")
  @Test fun secondaryMenu_galleryHighlights_largeScreen() = verifyStaticNavigation("/gallery/subjects", ScreenWidth.LARGE, MenuType.SECONDARY, "Highlights", "/gallery/highlights")

  @Test fun secondaryMenu_gallerySubjects_smallScreen() = verifyPopupNavigation("/gallery/highlights", MenuType.SECONDARY, "Subjects", "/gallery/subjects")
  @Test fun secondaryMenu_gallerySubjects_mediumScreen() = verifyStaticNavigation("/gallery/highlights", ScreenWidth.MEDIUM, MenuType.SECONDARY, "Subjects", "/gallery/subjects")
  @Test fun secondaryMenu_gallerySubjects_largeScreen() = verifyStaticNavigation("/gallery/highlights", ScreenWidth.LARGE, MenuType.SECONDARY, "Subjects", "/gallery/subjects")

  @Test fun secondaryMenu_galleryPalettes_smallScreen() = verifyPopupNavigation("/gallery/highlights", MenuType.SECONDARY, "Palettes", "/gallery/palettes")
  @Test fun secondaryMenu_galleryPalettes_mediumScreen() = verifyStaticNavigation("/gallery/highlights", ScreenWidth.MEDIUM, MenuType.SECONDARY, "Palettes", "/gallery/palettes")
  @Test fun secondaryMenu_galleryPalettes_largeScreen() = verifyStaticNavigation("/gallery/highlights", ScreenWidth.LARGE, MenuType.SECONDARY, "Palettes", "/gallery/palettes")

  @Test fun secondaryMenu_galleryChronological_smallScreen() = verifyPopupNavigation("/gallery/highlights", MenuType.SECONDARY, "Chronological", "/gallery/chronological")
  @Test fun secondaryMenu_galleryChronological_mediumScreen() = verifyStaticNavigation("/gallery/highlights", ScreenWidth.MEDIUM, MenuType.SECONDARY, "Chronological", "/gallery/chronological")
  @Test fun secondaryMenu_galleryChronological_largeScreen() = verifyStaticNavigation("/gallery/highlights", ScreenWidth.LARGE, MenuType.SECONDARY, "Chronological", "/gallery/chronological")


  @Test fun secondaryMenu_journalHighlights_smallScreen() = verifyPopupNavigation("/journal/topics", MenuType.SECONDARY, "Highlights", "/journal/highlights")
  @Test fun secondaryMenu_journalHighlights_mediumScreen() = verifyStaticNavigation("/journal/topics", ScreenWidth.MEDIUM, MenuType.SECONDARY, "Highlights", "/journal/highlights")
  @Test fun secondaryMenu_journalHighlights_largeScreen() = verifyStaticNavigation("/journal/topics", ScreenWidth.LARGE, MenuType.SECONDARY, "Highlights", "/journal/highlights")

  @Test fun secondaryMenu_journalTopics_smallScreen() = verifyPopupNavigation("/journal/highlights", MenuType.SECONDARY, "Topics", "/journal/topics")
  @Test fun secondaryMenu_journalTopics_mediumScreen() = verifyStaticNavigation("/journal/highlights", ScreenWidth.MEDIUM, MenuType.SECONDARY, "Topics", "/journal/topics")
  @Test fun secondaryMenu_journalTopics_largeScreen() = verifyStaticNavigation("/journal/highlights", ScreenWidth.LARGE, MenuType.SECONDARY, "Topics", "/journal/topics")

  @Test fun secondaryMenu_journalSeries_smallScreen() = verifyPopupNavigation("/journal/highlights", MenuType.SECONDARY, "Series", "/journal/series")
  @Test fun secondaryMenu_journalSeries_mediumScreen() = verifyStaticNavigation("/journal/highlights", ScreenWidth.MEDIUM, MenuType.SECONDARY, "Series", "/journal/series")
  @Test fun secondaryMenu_journalSeries_largeScreen() = verifyStaticNavigation("/journal/highlights", ScreenWidth.LARGE, MenuType.SECONDARY, "Series", "/journal/series")

  @Test fun secondaryMenu_journalGenres_smallScreen() = verifyPopupNavigation("/journal/highlights", MenuType.SECONDARY, "Genres", "/journal/genres")
  @Test fun secondaryMenu_journalGenres_mediumScreen() = verifyStaticNavigation("/journal/highlights", ScreenWidth.MEDIUM, MenuType.SECONDARY, "Genres", "/journal/genres")
  @Test fun secondaryMenu_journalGenres_largeScreen() = verifyStaticNavigation("/journal/highlights", ScreenWidth.LARGE, MenuType.SECONDARY, "Genres", "/journal/genres")

  @Test fun secondaryMenu_journalChronological_smallScreen() = verifyPopupNavigation("/journal/highlights", MenuType.SECONDARY, "Chronological", "/journal/chronological")
  @Test fun secondaryMenu_journalChronological_mediumScreen() = verifyStaticNavigation("/journal/highlights", ScreenWidth.MEDIUM, MenuType.SECONDARY, "Chronological", "/journal/chronological")
  @Test fun secondaryMenu_journalChronological_largeScreen() = verifyStaticNavigation("/journal/highlights", ScreenWidth.LARGE, MenuType.SECONDARY, "Chronological", "/journal/chronological")


  @Test fun secondaryMenu_repositoryHighlights_smallScreen() = verifyPopupNavigation("/repository/locations", MenuType.SECONDARY, "Highlights", "/repository/highlights")
  @Test fun secondaryMenu_repositoryHighlights_mediumScreen() = verifyStaticNavigation("/repository/locations", ScreenWidth.MEDIUM, MenuType.SECONDARY, "Highlights", "/repository/highlights")
  @Test fun secondaryMenu_repositoryHighlights_largeScreen() = verifyStaticNavigation("/repository/locations", ScreenWidth.LARGE, MenuType.SECONDARY, "Highlights", "/repository/highlights")

  @Test fun secondaryMenu_repositoryLocations_smallScreen() = verifyPopupNavigation("/repository/highlights", MenuType.SECONDARY, "Locations", "/repository/locations")
  @Test fun secondaryMenu_repositoryLocations_mediumScreen() = verifyStaticNavigation("/repository/highlights", ScreenWidth.MEDIUM, MenuType.SECONDARY, "Locations", "/repository/locations")
  @Test fun secondaryMenu_repositoryLocations_largeScreen() = verifyStaticNavigation("/repository/highlights", ScreenWidth.LARGE, MenuType.SECONDARY, "Locations", "/repository/locations")

  @Test fun secondaryMenu_repositoryTechnologies_smallScreen() = verifyPopupNavigation("/repository/highlights", MenuType.SECONDARY, "Technologies", "/repository/technologies")
  @Test fun secondaryMenu_repositoryTechnologies_mediumScreen() = verifyStaticNavigation("/repository/highlights", ScreenWidth.MEDIUM, MenuType.SECONDARY, "Technologies", "/repository/technologies")
  @Test fun secondaryMenu_repositoryTechnologies_largeScreen() = verifyStaticNavigation("/repository/highlights", ScreenWidth.LARGE, MenuType.SECONDARY, "Technologies", "/repository/technologies")





  @Test fun contextualMenu_galleryItem_next_smallScreen() = verifyPopupNavigation("/gallery/item/nothing_2022", MenuType.CONTEXTUAL, "Next >", "/gallery/item/singularity_2025")
  @Test fun contextualMenu_galleryItem_next_mediumScreen() = verifyStaticNavigation("/gallery/item/nothing_2022", ScreenWidth.MEDIUM, MenuType.CONTEXTUAL, "Next >", "/gallery/item/singularity_2025")
  @Test fun contextualMenu_galleryItem_next_largeScreen() = verifyStaticNavigation("/gallery/item/nothing_2022", ScreenWidth.LARGE, MenuType.CONTEXTUAL, "Next >", "/gallery/item/singularity_2025")


  @Test fun contextualMenu_galleryItem_previous_smallScreen() = verifyPopupNavigation("/gallery/item/intelligence_2023", MenuType.CONTEXTUAL, "< Previous", "/gallery/item/cohesion_2023")
  @Test fun contextualMenu_galleryItem_previous_mediumScreen() = verifyStaticNavigation("/gallery/item/intelligence_2023", ScreenWidth.MEDIUM, MenuType.CONTEXTUAL, "< Previous", "/gallery/item/cohesion_2023")
  @Test fun contextualMenu_galleryItem_previous_largeScreen() = verifyStaticNavigation("/gallery/item/intelligence_2023", ScreenWidth.LARGE, MenuType.CONTEXTUAL, "< Previous", "/gallery/item/cohesion_2023")


  @Test fun contextualMenu_galleryItem_fullSeries_smallScreen() = verifyPopupNavigation("/gallery/item/anything_2025", MenuType.CONTEXTUAL, "Full Series", "/gallery/series/genesis_ex_nihilo/")
  @Test fun contextualMenu_galleryItem_fullSeries_mediumScreen() = verifyStaticNavigation("/gallery/item/anything_2025", ScreenWidth.MEDIUM, MenuType.CONTEXTUAL, "Full Series", "/gallery/series/genesis_ex_nihilo/")
  @Test fun contextualMenu_galleryItem_fullSeries_largeScreen() = verifyStaticNavigation("/gallery/item/anything_2025", ScreenWidth.LARGE, MenuType.CONTEXTUAL, "Full Series", "/gallery/series/genesis_ex_nihilo/")

  // More Topics (Journal only)
  @Test fun contextualMenu_journalItem_moreTopics_smallScreen() = verifyPopupNavigation("/journal/item/logs", MenuType.CONTEXTUAL, "More Technology", "/journal/topics#technology")
  @Test fun contextualMenu_journalItem_moreTopics_mediumScreen() = verifyStaticNavigation("/journal/item/logs", ScreenWidth.MEDIUM, MenuType.CONTEXTUAL, "More Technology", "/journal/topics#technology")
  @Test fun contextualMenu_journalItem_moreTopics_largeScreen() = verifyStaticNavigation("/journal/item/logs", ScreenWidth.LARGE, MenuType.CONTEXTUAL, "More Technology", "/journal/topics#technology")


  /**
   * Verifies navigation for a menu item that is statically visible (Desktop).
   *
   * @param startPath The path to open.
   * @param size The screen width (Medium or Large).
   * @param type The menu specific.
   * @param label The text of the link to click.
   * @param destination The expected destination path.
   */
  private fun verifyStaticNavigation(
      startPath: String,
      size: ScreenWidth,
      type: MenuType,
      label: String,
      destination: String
  ) {
    harness.setup(size)
    val page = harness.openPage(URI.create(startPath))
    
    page.locator("${type.selector} :text-is('$label')").click()
    assertThat(page).hasUri(harness.getServerEndpoint().resolve(destination))
  }

  /**
   * Verifies navigation for a menu item that is inside a popup (Mobile).
   *
   * Implicitly sets [ScreenWidth.SMALL].
   *
   * @param startPath The path to open.
   * @param type The menu specific.
   * @param label The text of the link to click.
   * @param destination The expected destination path.
   */
  private fun verifyPopupNavigation(
      startPath: String,
      type: MenuType,
      label: String,
      destination: String
  ) {
    harness.setup(ScreenWidth.SMALL)
    val page = harness.openPage(URI.create(startPath))
    // Expand the popup
    page.expandPopupMenu(type)
    // Click the link
    page.locator("${type.selector} :text-is('$label')").click()
    assertThat(page).hasUri(harness.getServerEndpoint().resolve(destination))
  }

  /** Defines the specific menu component to target. */
}
