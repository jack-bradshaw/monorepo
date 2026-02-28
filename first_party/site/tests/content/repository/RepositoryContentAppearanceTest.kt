package com.jackbradshaw.site.tests.content.repository

import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.content.BaseContentAppearanceTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RepositoryContentAppearanceTest :
    BaseContentAppearanceTest(RepositoryContentAppearanceTest.GOLDEN_BASE_PATH) {

  @Test
  fun highlights_smallScreen() = runNonCollapsibleTest("/repository/highlights", ScreenWidth.SMALL)

  @Test
  fun highlights_mediumScreen() =
      runNonCollapsibleTest("/repository/highlights", ScreenWidth.MEDIUM)

  @Test
  fun highlights_largeScreen() = runNonCollapsibleTest("/repository/highlights", ScreenWidth.LARGE)

  @Test
  fun locations_smallScreen_collapsed() =
      runCollapsedTest("/repository/locations", ScreenWidth.SMALL)

  @Test
  fun locations_smallScreen_expanded() = runExpandedTest("/repository/locations", ScreenWidth.SMALL)

  @Test
  fun locations_mediumScreen_collapsed() =
      runCollapsedTest("/repository/locations", ScreenWidth.MEDIUM)

  @Test
  fun locations_mediumScreen_expanded() =
      runExpandedTest("/repository/locations", ScreenWidth.MEDIUM)

  @Test
  fun locations_largeScreen_collapsed() =
      runCollapsedTest("/repository/locations", ScreenWidth.LARGE)

  @Test
  fun locations_largeScreen_expanded() = runExpandedTest("/repository/locations", ScreenWidth.LARGE)

  @Test
  fun technologies_smallScreen_collapsed() =
      runCollapsedTest("/repository/technologies", ScreenWidth.SMALL)

  @Test
  fun technologies_smallScreen_expanded() =
      runExpandedTest("/repository/technologies", ScreenWidth.SMALL)

  @Test
  fun technologies_mediumScreen_collapsed() =
      runCollapsedTest("/repository/technologies", ScreenWidth.MEDIUM)

  @Test
  fun technologies_mediumScreen_expanded() =
      runExpandedTest("/repository/technologies", ScreenWidth.MEDIUM)

  @Test
  fun technologies_largeScreen_collapsed() =
      runCollapsedTest("/repository/technologies", ScreenWidth.LARGE)

  @Test
  fun technologies_largeScreen_expanded() =
      runExpandedTest("/repository/technologies", ScreenWidth.LARGE)

  companion object {
    /** The path to the golden files directory relative to the runfiles root. */
    private const val GOLDEN_BASE_PATH = "_main/first_party/site/tests/content/repository/goldens"
  }
}
