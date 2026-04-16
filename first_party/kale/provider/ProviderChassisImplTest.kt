package com.jackbradshaw.kale.provider

import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProviderChassisImplTest : ProviderChassisTest() {

  private lateinit var chassis: ProviderChassis

  @Before
  fun setUp() {
    chassis = providerChassisComponent(realisticCoroutinesTestingComponent()).providerChassis()
  }

  override fun subject() = chassis
}
