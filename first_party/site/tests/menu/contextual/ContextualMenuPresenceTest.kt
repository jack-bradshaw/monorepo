package com.jackbradshaw.site.tests.menu.contextual

import com.jackbradshaw.site.tests.MenuType
import com.jackbradshaw.site.tests.menu.BaseMenuPresenceTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ContextualMenuPresenceTest : BaseMenuPresenceTest(MenuType.CONTEXTUAL) {

  @Test fun index_absent() = runTest("/", present = false)

  @Test fun about_absent() = runTest("/about", present = false)

  @Test fun aboutCareerItem_absent() = runTest("/about/career/item/waymo", present = false)

  @Test fun galleryHighlights_absent() = runTest("/gallery/highlights", present = false)

  @Test fun gallerySubjects_absent() = runTest("/gallery/subjects", present = false)

  @Test fun galleryPalettes_absent() = runTest("/gallery/palettes", present = false)

  @Test fun galleryChronological_absent() = runTest("/gallery/chronological", present = false)

  @Test fun gallerySeries_absent() = runTest("/gallery/series/genesis_ex_nihilo", present = false)

  @Test fun galleryItem_present() = runTest("/gallery/item/singularity_2025", present = true)

  @Test fun journalHighlights_absent() = runTest("/journal/highlights", present = false)

  @Test fun journalTopics_absent() = runTest("/journal/topics", present = false)

  @Test fun journalGenres_absent() = runTest("/journal/genres", present = false)

  @Test fun journalChronological_absent() = runTest("/journal/chronological", present = false)

  @Test
  fun journalSeries_absent() {
    // There are no journal series in site data yet, so this scenario cannot be tested.
  }

  @Test fun journalItem_present() = runTest("/journal/item/into-the-subverse", present = true)

  @Test fun repositoryHighlights_absent() = runTest("/repository/highlights", present = false)

  @Test fun repositoryLocations_absent() = runTest("/repository/locations", present = false)

  @Test fun repositoryTechnologies_absent() = runTest("/repository/technologies", present = false)
}
