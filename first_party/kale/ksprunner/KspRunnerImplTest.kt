package com.jackbradshaw.kale.ksprunner

import com.jackbradshaw.kale.KaleScope
import dagger.Component
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class KspRunnerImplTest : KspRunnerTest() {

  private lateinit var component: TestComponent

  @Before
  fun setUp() {
    component = DaggerKspRunnerImplTest_TestComponent.create()
  }

  override fun subject(): KspRunner = component.kspRunner()

  @KaleScope
  @Component(modules = [KspRunnerModule::class])
  interface TestComponent {
    fun kspRunner(): KspRunner
  }
}
