package com.jackbradshaw.site.tests.content.repository

import com.jackbradshaw.site.tests.content.BaseContentBehaviourTest
import java.net.URI
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RepositoryContentBehaviourTest : BaseContentBehaviourTest() {

  @Test
  fun highlights() {
    runExternalLinkTest(
        startPagePath = URI.create("/repository/highlights"),
        itemLabel = "Spyglass",
        destinationUri = URI.create("https://github.com/jack-bradshaw/Spyglass"))
  }

  @Test
  fun locations() {
    runExternalLinkTest(
        startPagePath = URI.create("/repository/locations"),
        itemLabel = "AutoFactory",
        destinationUri =
            URI.create(
                "https://github.com/jack-bradshaw/monorepo/tree/main/first_party/autofactory"))
  }

  @Test
  fun technologies() {
    runExternalLinkTest(
        startPagePath = URI.create("/repository/technologies"),
        itemLabel = "AutoFactory",
        destinationUri =
            URI.create(
                "https://github.com/jack-bradshaw/monorepo/tree/main/first_party/autofactory"))
  }
}
