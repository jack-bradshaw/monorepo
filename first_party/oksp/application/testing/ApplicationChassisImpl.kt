package com.jackbradshaw.oksp.testing.application.chassis

import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Source
import com.jackbradshaw.kale.provider.ProviderRunner
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.passed.DaggerPassedApplicationComponent
import com.jackbradshaw.oksp.host.HostImpl
import com.jackbradshaw.quinn.QuinnComponent
import com.jackbradshaw.quinn.quinnComponent
import javax.inject.Inject

class ApplicationChassisImpl
@Inject
constructor(
    private val runner: ProviderRunner,
    private val coroutineComponent: CoroutinesComponent = coroutinesComponent(),
    private val quinnComponent: QuinnComponent = quinnComponent(),
) : ApplicationChassis {

  override suspend fun run(application: Application, sources: Set<Source>): Result {
    val appComponent = DaggerPassedApplicationComponent.builder().binding(application).build()
    val host =
        HostImpl(
            applicationComponent = appComponent,
            coroutines = coroutineComponent,
            quinn = quinnComponent)
    return runner.runProvider(host, sources)
  }
}
