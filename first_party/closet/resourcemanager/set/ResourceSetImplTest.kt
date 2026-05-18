package com.jackbradshaw.closet.resourcemanager.set

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.ObservableClosable.Status
import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import java.util.UUID
import javax.inject.Scope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceSetImplTest : ResourceSetTest<TestResource>() {

  private lateinit var subject: ResourceSet<TestResource>

  private lateinit var taskBarrier: TestingTaskBarrier

  @Before
  fun setUp() {
    val coroutines = realisticCoroutinesTestingComponent()
    val component =
        DaggerResourceSetImplTest_TestComponent.builder()
            .consuming(coroutines)
            .consuming(resourceSetComponent(coroutines, standardObservableClosableComponent()))
            .build()

    subject = runBlocking { component.factory().createResourceSet() }
    taskBarrier = component.taskBarrier()
  }

  override fun subject() = subject

  override fun createResource(): TestResource {
    val id = UUID.randomUUID().toString()
    return TestResource(id)
  }

  override suspend fun awaitTestIdle() {
    taskBarrier.awaitAllIdle()
  }

  @Scope annotation class TestScope

  @TestScope
  @Component(
      dependencies = [ResourceSetComponent::class, RealisticCoroutinesTestingComponent::class])
  interface TestComponent {
    fun factory(): ResourceSet.Factory

    @Coroutines fun taskBarrier(): TestingTaskBarrier

    @Component.Builder
    interface Builder {
      fun consuming(manager: ResourceSetComponent): Builder

      fun consuming(coroutines: RealisticCoroutinesTestingComponent): Builder

      fun build(): TestComponent
    }
  }
}

/**
 * Basic [ObservableClosable] for use in tests.
 *
 * The `isClosed` property is provided for test convenience. It will only be true when
 * `closureStatus` is `CLOSED`.
 */
class TestResource(val id: String) : ObservableClosable {

  var isClosed = false
    private set

  private val _closureStatus = MutableStateFlow(Status.OPEN)
  override val closureStatus = _closureStatus.asStateFlow()

  override suspend fun close() {
    _closureStatus.value = Status.CLOSING
    _closureStatus.value = Status.CLOSED
    isClosed = true
  }
}
