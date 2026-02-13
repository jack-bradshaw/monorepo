package com.jackbradshaw.site.tests.content.journal

import com.jackbradshaw.site.tests.content.BaseContentBehaviourTest
import java.net.URI
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class JournalContentBehaviourTest : BaseContentBehaviourTest() {

  @Test
  fun highlights() {
    runInternalLinkTest(
        startPagePath = URI.create("/journal/highlights"),
        itemLabel = "Into the Subverse",
        expectedDestinationPagePath = URI.create("/journal/item/into-the-subverse"))
  }

  @Test
  fun topics() {
    runInternalLinkTest(
        startPagePath = URI.create("/journal/topics"),
        itemLabel = "Into the Subverse",
        expectedDestinationPagePath = URI.create("/journal/item/into-the-subverse"))
  }

  @Test
  fun series() {
    runInternalLinkTest(
        startPagePath = URI.create("/journal/serieslist"),
        itemLabel = "Death of a Critic",
        expectedDestinationPagePath = URI.create("/journal/item/death-of-a-critic"))
  }

  @Test
  fun genres() {
    runInternalLinkTest(
        startPagePath = URI.create("/journal/genres"),
        itemLabel = "Sports Bar",
        expectedDestinationPagePath = URI.create("/journal/item/sports-bar"))
  }

  @Test
  fun chronological() {
    runInternalLinkTest(
        startPagePath = URI.create("/journal/chronological"),
        itemLabel = "Death of a Critic",
        expectedDestinationPagePath = URI.create("/journal/item/death-of-a-critic"))
  }
}
