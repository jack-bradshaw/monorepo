package com.jackbradshaw.backstab.core.main

import com.jackbradshaw.backstab.core.CoreScope
import com.jackbradshaw.backstab.core.generator.Generator
import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.repository.Repository
import com.jackbradshaw.coroutines.testing.TestCoroutines
import com.jackbradshaw.coroutines.testing.launcher.Launcher
import dagger.Component
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Concrete tests for [MainImpl]. */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class MainImplTest : MainTest() {

  private lateinit var main: MainImpl
  private lateinit var fakeRepository: FakeRepository
  private lateinit var fakeGenerator: FakeGenerator
  @Inject lateinit var launcher: Launcher

  @Before
  fun setup() {
    val coroutines = com.jackbradshaw.coroutines.testing.DaggerTestCoroutines.create()
    DaggerMainTestComponent.builder().testCoroutines(coroutines).build().inject(this)
    fakeRepository = FakeRepository()
    fakeGenerator = FakeGenerator()
    main = MainImpl(repository = fakeRepository, generator = fakeGenerator)
  }

  override fun subject(): Main = main

  override suspend fun publishTarget(target: BackstabTarget) {
    fakeRepository.targets.emit(target)
  }

  override fun injectGeneratorError(target: BackstabTarget, throwable: Throwable) {
    fakeGenerator.injectedErrors[target] = throwable
  }

  override fun getPublishedModules(target: BackstabTarget): List<BackstabModule>? {
    return fakeRepository.publishedModules[target]
  }

  override fun getPublishedError(target: BackstabTarget): Throwable? {
    return fakeRepository.publishedErrors[target]
  }

  override suspend fun awaitIdle() {
    // No-op for direct run()
  }

  override suspend fun runSubject() {
    launcher.launchEagerly { subject().run() }
  }

  private class FakeRepository : Repository {
    val targets = MutableSharedFlow<BackstabTarget>(replay = 0)
    val publishedModules = mutableMapOf<BackstabTarget, List<BackstabModule>>()
    val publishedErrors = mutableMapOf<BackstabTarget, Throwable>()

    override fun observeTargets(): Flow<BackstabTarget> = targets

    override suspend fun publishModule(target: BackstabTarget, module: BackstabModule) {
      publishedModules[target] = listOf(module)
    }

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

@CoreScope
@Component(dependencies = [TestCoroutines::class])
interface MainTestComponent {
  fun inject(target: MainImplTest)
}
