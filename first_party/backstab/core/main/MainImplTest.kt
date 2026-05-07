package com.jackbradshaw.backstab.core.main

import com.jackbradshaw.backstab.core.CoreScope
import com.jackbradshaw.backstab.core.generator.Generator
import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.obelisk.core.services.ObeliskControlService
import com.jackbradshaw.obelisk.core.services.ObeliskDataService
import com.jackbradshaw.obelisk.core.services.ObeliskErrorService
import com.jackbradshaw.sealant.hub.SealedHub
import com.jackbradshaw.sealant.flow.SealedFlow
import dagger.Component
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Concrete tests for [MainImpl]. */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class MainImplTest : MainTest() {

  private lateinit var main: MainImpl
  private lateinit var fakeDataService: FakeDataService
  private lateinit var fakeControlService: FakeControlService
  private lateinit var fakeErrorService: FakeErrorService
  private lateinit var fakeGenerator: FakeGenerator
  @Inject @Io lateinit var coroutineContext: CoroutineContext
  private lateinit var testScope: CoroutineScope

  @Before
  fun setup() {
    val coroutines = realisticCoroutinesTestingComponent()
    DaggerMainTestComponent.builder()
        .realisticCoroutinesTestingComponent(coroutines)
        .build()
        .inject(this)
    testScope = CoroutineScope(coroutineContext)
    fakeDataService = FakeDataService()
    fakeControlService = FakeControlService()
    fakeErrorService = FakeErrorService()
    fakeGenerator = FakeGenerator()
    main =
        MainImpl(
            dataService = fakeDataService,
            controlService = fakeControlService,
            errorService = fakeErrorService,
            generator = fakeGenerator)
  }

  override fun subject(): Main = main

  override suspend fun publishTarget(target: BackstabTarget) {
    fakeDataService.targets.emit(target)
  }

  override fun injectGeneratorError(target: BackstabTarget, throwable: Throwable) {
    fakeGenerator.injectedErrors[target] = throwable
  }

  override fun getPublishedModules(target: BackstabTarget): List<BackstabModule>? =
      fakeDataService.publishedModules[target]

  override fun getPublishedError(target: BackstabTarget): Throwable? =
      fakeErrorService.publishedErrors[target]

  override suspend fun awaitIdle() {
    kotlinx.coroutines.delay(100)
  }

  override suspend fun runSubject() {
    testScope.launch(start = CoroutineStart.UNDISPATCHED) { subject().run() }
  }

  private class FakeDataService : ObeliskDataService<BackstabTarget, BackstabModule> {
    val targets = MutableSharedFlow<BackstabTarget>(replay = 0)
    val publishedModules = mutableMapOf<BackstabTarget, List<BackstabModule>>()

    override fun observeTargets() =
        object : SealedHub<BackstabTarget> {
          override suspend fun createFlow() =
              object : SealedFlow<BackstabTarget> {
                override val flow = targets
                override val isConnectedToHub = kotlinx.coroutines.flow.MutableStateFlow(false)
                override val hasTerminalState = kotlinx.coroutines.flow.MutableStateFlow(false)
                override val hasTerminatedProcesses =
                    kotlinx.coroutines.flow.MutableStateFlow(false)

                override fun close() {}
              }

          override val hasTerminalState = kotlinx.coroutines.flow.MutableStateFlow(false)
          override val hasTerminatedProcesses = kotlinx.coroutines.flow.MutableStateFlow(false)

          override fun close() {}
        }

    override suspend fun publish(result: BackstabModule, anchors: Set<BackstabTarget>) {
      for (anchor in anchors) {
        publishedModules[anchor] = listOf(result)
      }
    }
  }

  private class FakeControlService : ObeliskControlService {
    override suspend fun allowStart() {}

    override suspend fun allowEnd() {}

    override suspend fun allowAdvance() {}

    override suspend fun forceAbort() {}
  }

  private class FakeErrorService : ObeliskErrorService<BackstabTarget> {
    val publishedErrors = mutableMapOf<BackstabTarget, Throwable>()

    override suspend fun fail(error: Throwable, anchor: BackstabTarget?) {
      if (anchor != null) {
        publishedErrors[anchor] = error
      }
    }

    override suspend fun fail(error: String, anchor: BackstabTarget?) {
      if (anchor != null) {
        publishedErrors[anchor] = RuntimeException(error)
      }
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
@Component(dependencies = [RealisticCoroutinesTestingComponent::class])
interface MainTestComponent {
  fun inject(target: MainImplTest)
}
