package com.jackbradshaw.oksp.testing.application.chassis

import com.jackbradshaw.kale.provider.providerRunnerComponent
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent

class ApplicationChassisImplTest : ApplicationChassisTest() {
  private val coroutinesInstance = realisticCoroutinesTestingComponent()

  override fun subject(): ApplicationChassis =
      applicationChassisComponent(
          providerRunnerComponent = providerRunnerComponent(),
          coroutineComponent = coroutinesInstance
      ).chassis()

  override fun coroutines(): RealisticCoroutinesTestingComponent = coroutinesInstance
}
