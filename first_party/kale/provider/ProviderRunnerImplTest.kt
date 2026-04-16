package com.jackbradshaw.kale.provider

import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProviderRunnerImplTest : ProviderRunnerTest() {

  private lateinit var chassis: ProviderRunner

  @Before
  fun setUp() {
    chassis = providerRunnerComponent().providerRunner()
  }

  override fun subject() = chassis
}
