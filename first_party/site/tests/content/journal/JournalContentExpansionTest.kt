package com.jackbradshaw.site.tests.content.journal

import com.jackbradshaw.site.tests.content.BaseContentExpansionTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class JournalContentExpansionTest : BaseContentExpansionTest() {

  @Test fun themes() = runExpansionTest("/journal/themes", isExpandedByDefault = false)

  @Test fun series() = runExpansionTest("/journal/serieslist", isExpandedByDefault = true)

  @Test fun genres() = runExpansionTest("/journal/genres", isExpandedByDefault = false)

  @Test fun formats() = runExpansionTest("/journal/formats", isExpandedByDefault = false)

  @Test fun chronological() = runExpansionTest("/journal/chronological", isExpandedByDefault = true)
}
