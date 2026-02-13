package com.jackbradshaw.site.tests.content.gallery

import com.jackbradshaw.site.tests.content.BaseContentExpansionTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GalleryContentExpansionTest : BaseContentExpansionTest() {

  @Test fun subjects() = runExpansionTest("/gallery/subjects", isExpandedByDefault = true)

  @Test fun palettes() = runExpansionTest("/gallery/palettes", isExpandedByDefault = false)

  @Test fun chronological() = runExpansionTest("/gallery/chronological", isExpandedByDefault = true)
}
