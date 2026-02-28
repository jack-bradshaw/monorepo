package com.jackbradshaw.site.tests.menu.primary

import com.jackbradshaw.site.tests.MenuType
import com.jackbradshaw.site.tests.menu.BaseMenuBehaviourTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PrimaryMenuBehaviourTest : BaseMenuBehaviourTest(MenuType.PRIMARY) {

  @Test
  fun startingCollapsed_home() =
      runStartingCollapsedTest(
          pagePath = "/gallery/highlights", itemSelector = "Home", expectedDestinationPath = "/")

  @Test
  fun startingCollapsed_gallery() =
      runStartingCollapsedTest(
          pagePath = "/", itemSelector = "Gallery", expectedDestinationPath = "/gallery/highlights")

  @Test
  fun startingCollapsed_journal() =
      runStartingCollapsedTest(
          pagePath = "/", itemSelector = "Journal", expectedDestinationPath = "/journal/highlights")

  @Test
  fun startingCollapsed_repository() =
      runStartingCollapsedTest(
          pagePath = "/",
          itemSelector = "Repository",
          expectedDestinationPath = "/repository/highlights")

  @Test
  fun startingCollapsed_about() =
      runStartingCollapsedTest(
          pagePath = "/", itemSelector = "About", expectedDestinationPath = "/about")

  @Test
  fun alwaysExpanded_home() =
      runAlwaysExpandedTest(
          pagePath = "/gallery/highlights", itemSelector = "Home", expectedDestinationPath = "/")

  @Test
  fun alwaysExpanded_gallery() =
      runAlwaysExpandedTest(
          pagePath = "/", itemSelector = "Gallery", expectedDestinationPath = "/gallery/highlights")

  @Test
  fun alwaysExpanded_journal() =
      runAlwaysExpandedTest(
          pagePath = "/", itemSelector = "Journal", expectedDestinationPath = "/journal/highlights")

  @Test
  fun alwaysExpanded_repository() =
      runAlwaysExpandedTest(
          pagePath = "/",
          itemSelector = "Repository",
          expectedDestinationPath = "/repository/highlights")

  @Test
  fun alwaysExpanded_about() =
      runAlwaysExpandedTest(
          pagePath = "/", itemSelector = "About", expectedDestinationPath = "/about")
}
