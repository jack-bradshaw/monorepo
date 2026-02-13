package com.jackbradshaw.site.tests.menu.secondary

import com.jackbradshaw.site.tests.MenuType
import com.jackbradshaw.site.tests.menu.BaseMenuPresenceTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SecondaryMenuPresenceTest : BaseMenuPresenceTest(MenuType.SECONDARY) {

  @Test fun index_absent() = runTest("/", present = false)

  @Test fun about_absent() = runTest("/about", present = false)

  @Test fun aboutCareerItem_absent() = runTest("/about/career/item/waymo", present = false)

  @Test fun galleryHighlights_present() = runTest("/gallery/highlights", present = true)

  @Test fun gallerySubjects_present() = runTest("/gallery/subjects", present = true)

  @Test fun galleryPalettes_present() = runTest("/gallery/palettes", present = true)

  @Test fun galleryChronological_present() = runTest("/gallery/chronological", present = true)

  @Test fun gallerySeries_absent() = runTest("/gallery/series/genesis_ex_nihilo", present = false)

  @Test fun galleryItem_absent() = runTest("/gallery/item/singularity_2025", present = false)

  @Test fun journalHighlights_present() = runTest("/journal/highlights", present = true)

  @Test fun journalTopics_present() = runTest("/journal/topics", present = true)

  @Test fun journalGenres_present() = runTest("/journal/genres", present = true)

  @Test fun journalChronological_present() = runTest("/journal/chronological", present = true)

  @Test
  fun journalSeries_absent() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test fun journalItem_absent() = runTest("/journal/item/into-the-subverse", present = false)

  @Test fun repositoryHighlights_present() = runTest("/repository/highlights", present = true)

  @Test fun repositoryLocations_present() = runTest("/repository/locations", present = true)

  @Test fun repositoryTechnologies_present() = runTest("/repository/technologies", present = true)
}
