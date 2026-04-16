package com.jackbradshaw.kale.processor

import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProcessorRunnerImplTest : ProcessorRunnerTest() {

  private lateinit var chassis: ProcessorRunner

  @Before
  fun setUp() {
    chassis = processorRunnerComponent().processorRunner()
  }

  override fun subject() = chassis
}
