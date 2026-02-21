package com.jackbradshaw.backstab.core.main

import com.jackbradshaw.backstab.core.generator.Generator
import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.ports.errorsink.ErrorSink
import com.jackbradshaw.backstab.core.ports.modulesink.ModuleSink
import com.jackbradshaw.backstab.core.ports.targetsource.TargetSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Concrete tests for [MainImpl]. */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class MainImplTest : MainTest() {

  private lateinit var main: MainImpl
  private lateinit var fakeTargetSource: FakeTargetSource
  private lateinit var fakeModuleSink: FakeModuleSink
  private lateinit var fakeErrorSink: FakeErrorSink
  private lateinit var fakeGenerator: FakeGenerator
  private lateinit var testScope: TestScope

  @Before
  fun setup() {
    val dispatcher = UnconfinedTestDispatcher()
    testScope = TestScope(dispatcher)

    fakeTargetSource = FakeTargetSource()
    fakeModuleSink = FakeModuleSink()
    fakeErrorSink = FakeErrorSink()
    fakeGenerator = FakeGenerator()
    main =
        MainImpl(
            targetSource = fakeTargetSource,
            moduleSink = fakeModuleSink,
            errorSink = fakeErrorSink,
            generator = fakeGenerator,
            coroutineScope = testScope)
  }

  override fun subject(): Main = main

  override suspend fun publishTarget(target: BackstabTarget) {
    fakeTargetSource.targets.emit(target)
  }

  override fun injectGeneratorError(target: BackstabTarget, throwable: Throwable) {
    fakeGenerator.injectedErrors[target] = throwable
  }

  override fun getPublishedModules(target: BackstabTarget): List<BackstabModule>? {
    return fakeModuleSink.publishedModules[target]
  }

  override fun getPublishedError(target: BackstabTarget): Throwable? {
    return fakeErrorSink.publishedErrors[target]
  }

  override suspend fun awaitIdle() {
    testScope.advanceUntilIdle()
  }

  private class FakeTargetSource : TargetSource {
    val targets = MutableSharedFlow<BackstabTarget>(replay = 0)

    override fun observeTargets(): Flow<BackstabTarget> = targets
  }

  private class FakeModuleSink : ModuleSink {
    val publishedModules = mutableMapOf<BackstabTarget, List<BackstabModule>>()

    override suspend fun publishModules(target: BackstabTarget, modules: List<BackstabModule>) {
      publishedModules[target] = modules
    }
  }

  private class FakeErrorSink : ErrorSink {
    val publishedErrors = mutableMapOf<BackstabTarget, Throwable>()

    override suspend fun publishError(target: BackstabTarget, error: Throwable) {
      publishedErrors[target] = error
    }
  }

  private inner class FakeGenerator : Generator {
    val injectedErrors = mutableMapOf<BackstabTarget, Throwable>()

    override suspend fun generateModuleFor(target: BackstabTarget): BackstabModule {
      val error = injectedErrors[target]
      if (error != null) throw error
      return createModule(target.component.nameChain.joinToString("_") + "_BackstabModule")
    }
  }
}
