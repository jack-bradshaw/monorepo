package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.coroutines.testing.testCoroutinesComponent
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Scope
import dagger.Component
import javax.inject.Inject
import com.jackbradshaw.coroutines.CoroutinesComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.ObservableClosableTest
import kotlinx.coroutines.runBlocking
import org.junit.Test

@RunWith(JUnit4::class)
class ResourceManagerImplObservableClosableTests {

  @Inject internal lateinit var factory: ResourceManager.Factory
  private lateinit var resourceManager: ResourceManager<String, TestResource>
  private lateinit var testScope: kotlinx.coroutines.test.TestScope

  @Before
  fun setUp() {
    val coroutinesComponent = testCoroutinesComponent()
    testScope = coroutinesComponent.testScope()
    
    DaggerObservableTestsComponent.builder()
      .coroutines(coroutinesComponent)
      .build()
      .inject(this)

    resourceManager = runBlocking { factory.createResourceManager() }
  }

  class TestResource(val id: String) : ObservableClosable {
    var isClosed = false

    private val _hasTerminalState = MutableStateFlow(false)
    override val hasTerminalState = _hasTerminalState.asStateFlow()

    private val _hasTerminatedProcesses = MutableStateFlow(false)
    override val hasTerminatedProcesses = _hasTerminatedProcesses.asStateFlow()

    override fun close() { 
      isClosed = true 
      _hasTerminalState.value = true
      _hasTerminatedProcesses.value = true
    }
  }

  private val observableManagerTests = object : ObservableClosableTest<ResourceManager<String, TestResource>>() {
    override fun subject() = resourceManager
  }

  @Test fun manager_afterClose_hasTerminalState() = observableManagerTests.afterClose_hasTerminalState()
  @Test fun manager_afterClose_hasTerminatedProcesses() = observableManagerTests.afterClose_hasTerminatedProcesses()

  private val observableResourceTests = object : ObservableClosableTest<TestResource>() {
    override fun subject() = TestResource("managed-resource")
  }
  
  @Test fun resource_afterClose_hasTerminalState() = observableResourceTests.afterClose_hasTerminalState()
  @Test fun resource_afterClose_hasTerminatedProcesses() = observableResourceTests.afterClose_hasTerminatedProcesses()
}

@Scope
annotation class ObservableTestsScope

@ObservableTestsScope
@Component(
  dependencies = [CoroutinesComponent::class],
  modules = [ResourceManagerImplModule::class]
)
interface ObservableTestsComponent {
  fun inject(target: ResourceManagerImplObservableClosableTests)

  @Component.Builder
  interface Builder {
    fun coroutines(coroutines: CoroutinesComponent): Builder
    fun build(): ObservableTestsComponent
  }
}
