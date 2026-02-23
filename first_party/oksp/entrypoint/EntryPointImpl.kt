package com.jackbradshaw.oksp.entrypoint

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.component.OkspComponent
import com.jackbradshaw.oksp.processor.Processor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import com.jackbradshaw.coroutines.Coroutines
import com.jackbradshaw.coroutines.CoroutinesScope
import com.jackbradshaw.coroutines.coroutines
import com.jackbradshaw.coroutines.io.Io
import com.jackbradshaw.oksp.application.ApplicationLoader
import com.jackbradshaw.oksp.service.ProcessingService
import dagger.Component
import dagger.BindsInstance
import dagger.Binds
import dagger.Module
import javax.inject.Scope
import com.jackbradshaw.oksp.processor.ProcessorImpl
import com.jackbradshaw.oksp.application.ApplicationLoaderImpl

/**
 * Standard KSP entry point for an OKSP application.
 *
 * This provider intercepts the system's KSP initialization and directs the user to 
 * construct their Dagger dependency graph. It creates the [Application] environment
 * and maintains its asynchronous lifecycle.
 */
open class EntryPointImpl @JvmOverloads constructor(
  val coroutineComponent: Coroutines = coroutines()
) : SymbolProcessorProvider {

  @Inject lateinit var applicationLoader: ApplicationLoader
  @Inject lateinit var processor: Processor
  @Inject @Io lateinit var coroutineScope: CoroutineScope

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    DaggerInternalComponent.factory().create(environment, coroutineComponent).inject(this)

    require(Companion.app == null) {
      "EntryPointImpl.create was called more than once. This is a violation of the core KSP " +
      "contract."
    }

    val app = applicationLoader.load().also { Companion.app = it }
    val appComponent = DaggerApplicationComponent.factory().create(environment, processor)

    runBlocking {
      app.onCreate(appComponent)
    }

    coroutineScope.launch {
      processor.observeTermination().first()
      app.onDestroy()
    }

    return processor
  }

  

  companion object {
    /** Statically holds the application reference to safeguard against repeated instantiation
     * and garbage collection. */
    private var app: Application? = null
  }
}

/* The Dagger component used internally in this class to source its own dependencies. */
  @Scope
  annotation class InternalScope

  @Module
  internal interface InternalModule {
    @Binds
    fun bindProcessor(impl: ProcessorImpl): Processor

    @Binds
    fun bindApplicationLoader(impl: ApplicationLoaderImpl): ApplicationLoader
  }

  @InternalScope
  @Component(dependencies = [Coroutines::class], modules = [InternalModule::class])
  internal interface InternalComponent {

    fun inject(target: EntryPointImpl)

    @Component.Factory
    interface Factory {
      fun create(
        @BindsInstance environment: SymbolProcessorEnvironment,
        coroutines: Coroutines
      ): InternalComponent
    }
  }

  @Module
  internal interface ApplicationModule {
    @Binds
    fun bindProcessingService(processor: Processor): ProcessingService
  }

  /** The Dagger component passed into the application to supply KSP-related dependencies. */
  @Component(modules = [ApplicationModule::class])
  internal interface ApplicationComponent : OkspComponent {
    @Component.Factory
    interface Factory {
      fun create(
        @BindsInstance environment: SymbolProcessorEnvironment, 
        @BindsInstance processor: Processor
      ): ApplicationComponent
    }
  }