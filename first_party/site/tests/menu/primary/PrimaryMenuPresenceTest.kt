package com.jackbradshaw.site.tests.menu.primary

import com.jackbradshaw.site.tests.MenuType
import com.jackbradshaw.site.tests.menu.BaseMenuPresenceTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PrimaryMenuPresenceTest : BaseMenuPresenceTest(MenuType.PRIMARY) {

  @Test fun index_present() = runTest("/", present = true)

  @Test fun about_present() = runTest("/about", present = true)

  @Test fun aboutCareerItem_present() = runTest("/about/career/item/waymo", present = true)

  @Test fun galleryHighlights_present() = runTest("/gallery/highlights", present = true)

  @Test fun gallerySubjects_present() = runTest("/gallery/subjects", present = true)

  @Test fun galleryPalettes_present() = runTest("/gallery/palettes", present = true)

  @Test fun galleryChronological_present() = runTest("/gallery/chronological", present = true)

  @Test fun galleryItem_present() = runTest("/gallery/item/singularity_2025", present = true)

  @Test fun gallerySeries_present() = runTest("/gallery/series/genesis_ex_nihilo", present = true)

  @Test fun journalHighlights_present() = runTest("/journal/highlights", present = true)

  @Test fun journalTopics_present() = runTest("/journal/topics", present = true)

  @Test fun journalGenres_present() = runTest("/journal/genres", present = true)

  @Test fun journalChronological_present() = runTest("/journal/chronological", present = true)

  @Test fun journalItem_present() = runTest("/journal/item/into-the-subverse", present = true)

  @Test
  fun journalSeries_present() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test fun repositoryHighlights_present() = runTest("/repository/highlights", present = true)

  @Test fun repositoryLocations_present() = runTest("/repository/locations", present = true)

  @Test fun repositoryTechnologies_present() = runTest("/repository/technologies", present = true)
}
