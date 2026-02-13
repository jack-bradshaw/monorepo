package com.jackbradshaw.site.tests.content.gallery

import com.jackbradshaw.site.tests.content.BaseContentBehaviourTest
import java.net.URI
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Content behaviour tests for gallery-related pages. */
@RunWith(JUnit4::class)
class GalleryContentBehaviourTest : BaseContentBehaviourTest() {

  @Test
  fun highlights() {
    runInternalLinkTest(
        startPagePath = URI.create("/gallery/highlights"),
        itemLabel = "Restructuring",
        expectedDestinationPagePath = URI.create("/gallery/item/restructuring_2024"))
  }

  @Test
  fun subjects() {
    runInternalLinkTest(
        startPagePath = URI.create("/gallery/subjects"),
        itemLabel = "Genesis Ex Nihilo",
        expectedDestinationPagePath = URI.create("/gallery/series/genesis_ex_nihilo/"))
  }

  @Test
  fun chronological() {
    runInternalLinkTest(
        startPagePath = URI.create("/gallery/chronological"),
        itemLabel = "Singularity",
        expectedDestinationPagePath = URI.create("/gallery/item/singularity_2025"))
  }

  @Test
  fun palettes() {
    runInternalLinkTest(
        startPagePath = URI.create("/gallery/palettes"),
        itemLabel = "Singularity",
        expectedDestinationPagePath = URI.create("/gallery/item/singularity_2025"))
  }

  @Test
  fun series() {
    runInternalLinkTest(
        startPagePath = URI.create("/gallery/series/genesis_ex_nihilo"),
        itemLabel = "Singularity",
        expectedDestinationPagePath = URI.create("/gallery/item/singularity_2025"))
  }
}
