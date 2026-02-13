package com.jackbradshaw.site.tests.content.other

import com.jackbradshaw.site.tests.content.BaseContentExpansionTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OtherContentExpansionTest : BaseContentExpansionTest() {

  @Test fun about() = runExpansionTest("/about", isExpandedByDefault = true)
}
