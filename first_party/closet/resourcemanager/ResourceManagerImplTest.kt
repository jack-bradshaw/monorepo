package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.coroutines.Cpu
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Inject
import javax.inject.Scope
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceManagerImplTest :
    ResourceManagerTest<String, ResourceManagerImplTest.TestResource>() {

  @Inject internal lateinit var factory: ResourceManager.Factory

  @Inject @Cpu lateinit var cpuContext: CoroutineContext

  @Inject @Coroutines lateinit var taskBarrier: TestingTaskBarrier

  private lateinit var resourceManager: ResourceManager<String, TestResource>

  @Before
  fun setUp() {
    DaggerResourceManagerImplTest_TestComponent.builder()
        .coroutines(realisticCoroutinesTestingComponent())
        .build()
        .inject(this)

    resourceManager = factory.createResourceManager()
  }

  override fun subject() = resourceManager

  override fun createKeyValuePair(id: String) = Pair(id, TestResource(id, cpuContext))

  override suspend fun awaitTestIdle() {
    taskBarrier.awaitAllIdle()
  }

  class TestResource(val id: String, private val cpuContext: CoroutineContext) :
      ObservableClosable {
    var isClosed = false

    private val _hasTerminalState = MutableStateFlow(false)
    override val hasTerminalState = _hasTerminalState.asStateFlow()

    private val _hasTerminatedProcesses = MutableStateFlow(false)
    override val hasTerminatedProcesses = _hasTerminatedProcesses.asStateFlow()

    override fun close() {
      isClosed = true
      runBlocking {
        launch(cpuContext) {
          _hasTerminalState.value = true
          _hasTerminatedProcesses.value = true
        }
      }
    }
  }

  @Scope annotation class TestScope

  @TestScope
  @Component(
      dependencies = [RealisticCoroutinesTestingComponent::class],
      modules = [ResourceManagerImplModule::class])
  interface TestComponent {
    fun inject(target: ResourceManagerImplTest)

    @Component.Builder
    interface Builder {
      fun coroutines(coroutines: RealisticCoroutinesTestingComponent): Builder

      fun build(): TestComponent
    }
  }
}
