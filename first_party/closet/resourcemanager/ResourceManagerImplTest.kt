package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import java.util.UUID
import javax.inject.Scope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceManagerImplTest : ResourceManagerTest<String, TestResource>() {

  private lateinit var resourceManager: ResourceManager<String, TestResource>
  private lateinit var taskBarrier: TestingTaskBarrier

  @Before
  fun setUp() {
    val coroutines = realisticCoroutinesTestingComponent()
    val component =
        DaggerResourceManagerImplTest_TestComponent.builder()
            .consuming(coroutines)
            .consuming(resourceManagerComponent(coroutines))
            .build()

    resourceManager = component.factory().createResourceManager()
    taskBarrier = component.taskBarrier()
  }

  override fun subject() = resourceManager

  override fun createKeyValuePair(): Pair<String, TestResource> {
    val id = UUID.randomUUID().toString()
    return Pair(id, TestResource(id))
  }

  override suspend fun awaitTestIdle() {
    taskBarrier.awaitAllIdle()
  }

  @Scope annotation class TestScope

  @TestScope
  @Component(
      dependencies = [ResourceManagerComponent::class, RealisticCoroutinesTestingComponent::class])
  interface TestComponent {
    fun factory(): ResourceManager.Factory

    @Coroutines fun taskBarrier(): TestingTaskBarrier

    @Component.Builder
    interface Builder {
      fun consuming(manager: ResourceManagerComponent): Builder

      fun consuming(coroutines: RealisticCoroutinesTestingComponent): Builder

      fun build(): TestComponent
    }
  }
}

/**
 * Basic [ObservableClosable] for use in tests.
 *
 * The `isClosed` property is provided for test convenience. It will only be true when
 * `hasTerminalState` and `hasTerminatedProcesses` are both true.
 */
class TestResource(val id: String) : ObservableClosable {

  var isClosed = false
    private set

  private val _hasTerminalState = MutableStateFlow(false)
  override val hasTerminalState = _hasTerminalState.asStateFlow()

  private val _hasTerminatedProcesses = MutableStateFlow(false)
  override val hasTerminatedProcesses = _hasTerminatedProcesses.asStateFlow()

  override fun close() {
    runBlocking {
      _hasTerminalState.value = true
      _hasTerminatedProcesses.value = true
    }
    isClosed = true
  }
}
