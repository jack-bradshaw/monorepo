package com.jackbradshaw.site.tests.content.other

import com.jackbradshaw.site.tests.ScreenWidth
import com.jackbradshaw.site.tests.content.BaseContentAppearanceTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OtherContentAppearanceTest :
    BaseContentAppearanceTest(OtherContentAppearanceTest.GOLDEN_BASE_PATH) {

  @Test fun index_smallScreen() = runCollapsedTest("/", ScreenWidth.SMALL)

  @Test fun index_mediumScreen() = runCollapsedTest("/", ScreenWidth.MEDIUM)

  @Test fun index_largeScreen() = runCollapsedTest("/", ScreenWidth.LARGE)

  @Test fun about_smallScreen_collapsed() = runCollapsedTest("/about", ScreenWidth.SMALL)

  @Test fun about_smallScreen_expanded() = runExpandedTest("/about", ScreenWidth.SMALL)

  @Test fun about_mediumScreen_collapsed() = runCollapsedTest("/about", ScreenWidth.MEDIUM)

  @Test fun about_mediumScreen_expanded() = runExpandedTest("/about", ScreenWidth.MEDIUM)

  @Test fun about_largeScreen_collapsed() = runCollapsedTest("/about", ScreenWidth.LARGE)

  @Test fun about_largeScreen_expanded() = runExpandedTest("/about", ScreenWidth.LARGE)

  @Test
  fun aboutCareerItem_smallScreen() =
      runNonCollapsibleTest("/about/career/item/waymo", ScreenWidth.SMALL)

  @Test
  fun aboutCareerItem_mediumScreen() =
      runNonCollapsibleTest("/about/career/item/waymo", ScreenWidth.MEDIUM)

  @Test
  fun aboutCareerItem_largeScreen() =
      runNonCollapsibleTest("/about/career/item/waymo", ScreenWidth.LARGE)

  companion object {
    /** The path to the golden files directory relative to the runfiles root. */
    private const val GOLDEN_BASE_PATH = "_main/first_party/site/tests/content/other/goldens"
  }
}
