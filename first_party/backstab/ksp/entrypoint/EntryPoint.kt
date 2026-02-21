package com.jackbradshaw.backstab.ksp.entrypoint

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.jackbradshaw.backstab.core.CoreScope
import com.jackbradshaw.backstab.core.generator.GeneratorModule
import com.jackbradshaw.backstab.core.main.Main
import com.jackbradshaw.backstab.core.main.MainModule
import com.jackbradshaw.backstab.ksp.parser.ParserModule
import com.jackbradshaw.backstab.ksp.processor.KspBackend
import com.jackbradshaw.backstab.ksp.processor.KspProcessorModule
import com.jackbradshaw.backstab.ksp.processor.Processor
import com.jackbradshaw.coroutines.Coroutines
import com.jackbradshaw.coroutines.coroutines
import com.jackbradshaw.coroutines.io.Io
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * The main entry point for the KSP port of Backstab.
 *
 * The KSP contract requires an implementation of [SymbolProcessorProvider] to provide the
 * [SymbolProcessor] and act as the root of the custom processor, but the Backstab processor is
 * decoupled from KSP and operates asynchronously; therefore, this class effectively forks the
 * execution path by launching main as a side-car process. When create is called, it effectively
 * returns a processor so KSP can feed symbols into the process, but before it does, it creates and
 * launches the main Backstab program on a background coroutine scope. This allows the decoupled
 * Backstab program to operate independently while the respecting the KSP contract and using KSP as
 * the program execution root.
 *
 * The [create] function does this by executing the following sequence:
 * 1. Getting an instance of [Main] from Dagger.
 * 2. Getting an instance of [Processor] from Dagger.
 * 3. Starting [Main] on a background coroutine scope.
 * 4. Launching an asynchronous listener that waits for a shutdown signal from KSP to stop [Main]
 *    when KSP is exiting.
 * 5. Returning the processor to KSP for general execution.
 *
 * The [Main] instance is stored in static memory (companion object) to ensure only one instance of
 * [Main] exists per process, regardless of how many times KSP invokes the [create] function (not
 * assumed to be once per process).
 *
 * This bootstrap process allows Main to contain the custom elements of the program in a way that is
 * decoupled from KSP, while satisfying the KSP contract and using KSP as the execution root.
 */
class EntryPoint : SymbolProcessorProvider {

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    environment.logger.warn("Backstab: EntryPoint.create starting.")
    val component = DaggerKspComponent.factory().create(environment, coroutines())

    check(main == null) {
      """
      Main cannot be created again. Every instance of EntryPoint must be associated with exactly one
      Main instance. If this has happened, either KSP invoked `create` again (unexpected violation
      of the KSP contract), or another process invoked `create` manually.
      """
          .trimIndent()
    }

    main = component.main().also { it.start() }
    val processor = component.processor()

    component.scope().launch {
      suspendUntilProcessingCompletes(processor)
      main?.stop()
    }

    return processor
  }

  private suspend fun suspendUntilProcessingCompletes(processor: Processor) {
    processor.onProcessingComplete.first()
  }

  companion object {
    /**
     * The main logic of the program. Hoisted into memory to prevent garbage collection and ensure
     * only one instance of [Main] exists per process.
     */
    private var main: Main? = null
  }
}

@CoreScope
@Component(
    modules =
        [MainModule::class, GeneratorModule::class, ParserModule::class, KspProcessorModule::class],
    dependencies = [Coroutines::class])
internal interface KspComponent {
  fun processor(): KspBackend

  fun main(): Main

  @Io fun scope(): CoroutineScope

  @Component.Factory
  interface Factory {
    fun create(
        @BindsInstance environment: SymbolProcessorEnvironment,
        coroutines: Coroutines
    ): KspComponent
  }
}
