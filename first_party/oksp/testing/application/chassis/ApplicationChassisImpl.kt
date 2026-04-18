package com.jackbradshaw.oksp.testing.application.chassis

import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Source
import com.jackbradshaw.kale.provider.ProviderRunner
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.passed.DaggerPassedApplicationComponent
import com.jackbradshaw.oksp.entrypoint.EntryPointImpl
import javax.inject.Inject

class ApplicationChassisImpl @Inject constructor(private val runner: ProviderRunner) :
    ApplicationChassis {

  override suspend fun run(application: Application, sources: Set<Source>): Result {
    val component = DaggerPassedApplicationComponent.builder().binding(application).build()
    val entryPoint = EntryPointImpl(applicationComponent = component)
    return runner.runProvider(entryPoint, sources)
  }
}
