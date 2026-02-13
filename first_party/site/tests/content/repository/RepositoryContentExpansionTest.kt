package com.jackbradshaw.site.tests.content.repository

import com.jackbradshaw.site.tests.content.BaseContentExpansionTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RepositoryContentExpansionTest : BaseContentExpansionTest() {

  @Test fun locations() = runExpansionTest("/repository/locations", isExpandedByDefault = true)

  @Test
  fun technologies() = runExpansionTest("/repository/technologies", isExpandedByDefault = false)
}
