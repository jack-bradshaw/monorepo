package com.jackbradshaw.oksp.entrypoint

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.ApplicationComponent
import com.jackbradshaw.oksp.application.loaded.loadedApplicationComponent
import com.jackbradshaw.oksp.processor.Processor
import com.jackbradshaw.oksp.processor.ProcessorImpl
import com.jackbradshaw.oksp.service.ProcessingService
import com.jackbradshaw.quinn.core.QuinnComponent
import com.jackbradshaw.quinn.core.quinnComponent
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Inject
import javax.inject.Scope
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Standard KSP entry point for an OKSP application.
 *
 * This provider intercepts the system's KSP initialization and directs the user to construct their
 * Dagger dependency graph. It creates the [Application] environment and maintains its asynchronous
 * lifecycle.
 */
class EntryPointImpl
@JvmOverloads
constructor(
    val applicationComponent: ApplicationComponent = loadedApplicationComponent(),
    val coroutineComponent: CoroutinesComponent = coroutinesComponent(),
    val quinnComponent: QuinnComponent = quinnComponent()
) : EntryPoint {

  @Inject lateinit var processor: Processor
  @Inject @Io lateinit var coroutineContext: CoroutineContext

  private val coroutineScope by lazy { CoroutineScope(coroutineContext) }

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    DaggerInternalComponent.factory()
        .create(environment, coroutineComponent, quinnComponent)
        .inject(this)

    require(Companion.app == null) {
      "EntryPointImpl.create was called more than once. Each instance can only be used once."
    }

    val app = applicationComponent.application().also { Companion.app = it }
    val context = DaggerContextComponentImpl.factory().create(environment, processor)

    coroutineScope.launch {
      processor.observeAllRoundsCompleteEvent().first()
      app.onDestroy()
    }

    coroutineScope.launch { app.onCreate(context) }

    return processor
  }

  companion object {
    /**
     * Statically holds the application reference to safeguard against repeated instantiation and
     * garbage collection.
     */
    private var app: Application? = null
  }
}

/* The Dagger component used internally in this class to source its own dependencies. */
@Scope annotation class InternalScope

@Module
internal interface InternalModule {
  @Binds fun bindProcessor(impl: ProcessorImpl): Processor
}

@InternalScope
@Component(
    dependencies = [CoroutinesComponent::class, QuinnComponent::class],
    modules = [InternalModule::class])
internal interface InternalComponent {

  fun inject(target: EntryPointImpl)

  @Component.Factory
  interface Factory {
    fun create(
        @BindsInstance environment: SymbolProcessorEnvironment,
        coroutines: CoroutinesComponent,
        quinn: QuinnComponent
    ): InternalComponent
  }
}

@Module
internal interface ContextModule {
  @Binds fun bindProcessingService(processor: Processor): ProcessingService
}

/** The Dagger component passed into the application to supply KSP-related dependencies. */
@Component(modules = [ContextModule::class])
internal interface ContextComponentImpl : Application.ContextComponent {
  @Component.Factory
  interface Factory {
    fun create(
        @BindsInstance environment: SymbolProcessorEnvironment,
        @BindsInstance processor: Processor
    ): ContextComponentImpl
  }
}
