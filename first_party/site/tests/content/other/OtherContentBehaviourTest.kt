package com.jackbradshaw.site.tests.content.other

import com.jackbradshaw.site.tests.content.BaseContentBehaviourTest
import java.net.URI
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OtherContentBehaviourTest : BaseContentBehaviourTest() {

  @Test
  fun index_clickGalleryLink() {
    runInternalLinkTest(
        startPagePath = URI.create("/"),
        itemLabel = "gallery",
        expectedDestinationPagePath = URI.create("/gallery/highlights/"))
  }

  @Test
  fun index_clickJournalLink() {
    runInternalLinkTest(
        startPagePath = URI.create("/"),
        itemLabel = "journal",
        expectedDestinationPagePath = URI.create("/journal/highlights/"))
  }

  @Test
  fun index_clickRepositoriesLink() {
    runInternalLinkTest(
        startPagePath = URI.create("/"),
        itemLabel = "repository",
        expectedDestinationPagePath = URI.create("/repository/highlights/"))
  }

  @Test
  fun about_clickNetworkLink() {
    runExternalLinkTest(
        startPagePath = URI.create("/about"),
        itemLabel = "Bluesky",
        destinationUri = URI.create("https://bsky.app/profile/jack-bradshaw.com"))
  }

  @Test
  fun about_clickCareerItem() {
    runExternalLinkTest(
        startPagePath = URI.create("/about"),
        itemLabel = "Waymo",
        destinationUri = URI.create("/about/career/item/waymo"))
  }

  @Test
  fun aboutCareerItem_clickContentItemLink() {
    runExternalLinkTest(
        startPagePath = URI.create("/about/career/item/waymo"),
        itemLabel = "Waymo",
        destinationUri =
            URI.create("https://play.google.com/store/apps/details?id=com.waymo.carapp"))
  }
}
