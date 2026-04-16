package com.jackbradshaw.kale.resolver.chassis

import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.kale.model.Source
import org.junit.After
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResolverHarnessImplTest : ResolverHarnessTest() {

  private lateinit var chassis: ResolverChassis

  private lateinit var underTest: ResolverChassis.ResolverHarness

  @After
  override fun tearDown() {
    chassis.close()
  }

  override suspend fun setupSubject(sources: Set<Source>) {
    chassis = resolverChassisComponent(realisticCoroutinesTestingComponent()).resolverChassis()
    underTest = chassis.open(sources)
  }

  override suspend fun subject() = underTest
}
