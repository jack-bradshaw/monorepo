package com.jackbradshaw.backstab.oksp.application

import com.jackbradshaw.backstab.core.CoreScope
import com.jackbradshaw.backstab.core.DaggerCoreComponentImpl
import com.jackbradshaw.backstab.core.ObeliskServiceComponent
import com.jackbradshaw.backstab.core.main.Main
import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.oksp.adapters.BackstabInflowAdapter
import com.jackbradshaw.backstab.oksp.adapters.BackstabOutflowAdapter
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.obelisk.core.services.ObeliskControlService
import com.jackbradshaw.obelisk.core.services.ObeliskDataService
import com.jackbradshaw.obelisk.core.services.ObeliskErrorService
import com.jackbradshaw.obelisk.core.services.ObeliskLoggingService
import com.jackbradshaw.obelisk.oksp.DaggerObeliskOkspComponentImpl
import com.jackbradshaw.obelisk.oksp.ObeliskOkspComponent
import com.jackbradshaw.obelisk.oksp.service.Service
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.sealant.SealantComponent
import com.jackbradshaw.sealant.sealantComponent
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * The main entry point for the OKSP port of Backstab.
 *
 * Backstab is decoupled from the processor integration and operates asynchronously; therefore, this
 * class initializes the execution path by launching main as a side-car process.
 *
 * The [onCreate] function does this by executing the following sequence:
 * 1. Building the Dagger component containing the backend integration.
 * 2. Getting an instance of [Main] from Dagger.
 * 3. Starting [Main].
 * 4. Starting the [KspBackend] to observe processing rounds.
 */
class BackstabApplication
@JvmOverloads
constructor(
    private val coroutines: CoroutinesComponent = coroutinesComponent(),
    private val sealantComponent: SealantComponent = sealantComponent()
) : Application {

  private val mainScopeHandle = Job()

  private val mainScope = CoroutineScope(coroutines.ioContext() + mainScopeHandle)

  override suspend fun onCreate(component: Application.KspComponent) {
    val obeliskComponent =
        DaggerObeliskOkspComponentImpl.builder()
            .kspComponent(component)
            .coroutinesComponent(coroutines)
            .sealantComponent(sealantComponent)
            .build()

    val serviceComponent =
        DaggerObeliskServiceComponentImpl.builder().obeliskComponent(obeliskComponent).build()

    val coreComponent = DaggerCoreComponentImpl.builder().consuming(serviceComponent).build()

    mainScope.launch { coreComponent.main().run() }
  }

  override suspend fun onDestroy() {
    mainScopeHandle.cancel()
  }
}

@CoreScope
@Component(
    dependencies = [ObeliskOkspComponent::class],
    modules = [ObeliskModule::class],
)
interface ObeliskServiceComponentImpl : ObeliskServiceComponent {

  @Component.Builder
  interface Builder {
    fun obeliskComponent(obeliskComponent: ObeliskOkspComponent): Builder

    fun build(): ObeliskServiceComponentImpl
  }
}

/** Binds [Service] to the various interfaces it implements */
@Module
class ObeliskModule {

  @Provides
  @CoreScope
  fun provideService(
      factory: Service.Factory,
      inflowAdapter: BackstabInflowAdapter,
      outflowAdapter: BackstabOutflowAdapter
  ): Service<BackstabTarget, BackstabModule> = factory.create(inflowAdapter, outflowAdapter)

  @Provides
  fun provideControl(service: Service<BackstabTarget, BackstabModule>): ObeliskControlService =
      service

  @Provides
  fun provideData(
      service: Service<BackstabTarget, BackstabModule>
  ): ObeliskDataService<BackstabTarget, BackstabModule> = service

  @Provides
  fun provideError(
      service: Service<BackstabTarget, BackstabModule>
  ): ObeliskErrorService<BackstabTarget> = service

  @Provides
  fun provideLogging(
      service: Service<BackstabTarget, BackstabModule>
  ): ObeliskLoggingService<BackstabTarget> = service
}
