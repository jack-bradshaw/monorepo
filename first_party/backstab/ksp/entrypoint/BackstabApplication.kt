package com.jackbradshaw.backstab.ksp.entrypoint

import com.jackbradshaw.backstab.core.main.Main
import com.jackbradshaw.backstab.ksp.repository.RepositoryImpl
import com.jackbradshaw.backstab.ksp.repository.RepositoryImplModule
import com.jackbradshaw.backstab.ksp.parser.ParserImplModule
import com.jackbradshaw.backstab.core.host.HostComponent
import com.jackbradshaw.backstab.core.DaggerCoreComponentImpl
import com.jackbradshaw.coroutines.Coroutines
import com.jackbradshaw.coroutines.coroutines
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.ApplicationComponent
import kotlinx.coroutines.launch
import com.jackbradshaw.backstab.core.CoreScope
import dagger.Component

/**
 * The main entry point for the OKSP port of Backstab.
 *
 * Backstab is decoupled from the processor integration and operates asynchronously; therefore,
 * this class initializes the execution path by launching main as a side-car process.
 *
 * The [onCreate] function does this by executing the following sequence:
 * 1. Building the Dagger component containing the backend integration.
 * 2. Getting an instance of [Main] from Dagger.
 * 3. Starting [Main].
 * 4. Starting the [KspBackend] to observe processing rounds.
 *
 * The [Main] instance is stored in static memory (companion object) to ensure only one instance of
 * [Main] exists per process.
 */
class BackstabApplication : Application {

  override suspend fun onCreate(component: ApplicationComponent) {
    check(mainJob == null) {
      """
      Main cannot be created again. Every instance of BackstabApplication must be associated with exactly one
      Main instance.
      """.trimIndent()
    }

    val coroutines = coroutines()
    
    mainJob = coroutines.ioCoroutineScope().launch {
      val hostComponent = DaggerHostComponentImpl.builder()
          .consuming(component)
          .consuming(coroutines)
          .build()

      launch {
        hostComponent.kspRepository().run()
      }

      DaggerCoreComponentImpl.builder()
        .consuming(hostComponent)
        .build()
        .main()
        .run()
    }
  }

  override suspend fun onDestroy() {
    mainJob?.cancel()
    mainJob = null
  }

  companion object {
    /**
     * The main logic of the program. Hoisted into memory to prevent garbage collection and ensure
     * only one instance of [Main] exists per process.
     */
    private var mainJob: kotlinx.coroutines.Job? = null
  }
}

@CoreScope
@Component(
  dependencies = [ApplicationComponent::class, Coroutines::class],
  modules = [RepositoryImplModule::class, ParserImplModule::class],
)
interface HostComponentImpl : HostComponent {
  fun kspRepository(): com.jackbradshaw.backstab.ksp.repository.Repository

  @Component.Builder
  interface Builder {
    fun consuming(applicationComponent: ApplicationComponent): Builder
    fun consuming(coroutines: Coroutines): Builder
    fun build(): HostComponentImpl
  }
}