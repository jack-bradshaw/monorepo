package com.jackbradshaw.kale.resolver.chassis

import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResolverChassisImplTest : ResolverChassisTest() {

  private lateinit var chassis: ResolverChassis

  @Before
  fun setUp() {
    chassis = resolverChassisComponent(realisticCoroutinesTestingComponent()).resolverChassis()
  }

  override fun subject() = chassis
}
