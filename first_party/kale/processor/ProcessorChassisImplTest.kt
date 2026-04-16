package com.jackbradshaw.kale.processor

import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProcessorChassisImplTest : ProcessorChassisTest() {

  private lateinit var chassis: ProcessorChassis

  @Before
  fun setUp() {
    chassis = processorChassisComponent(realisticCoroutinesTestingComponent()).processorChassis()
  }

  override fun subject() = chassis
}
