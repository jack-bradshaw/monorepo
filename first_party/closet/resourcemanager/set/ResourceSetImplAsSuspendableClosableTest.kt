package com.jackbradshaw.closet.resourcemanager.set

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import com.jackbradshaw.closet.suspending.SuspendableClosableTest
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Scope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceSetImplAsSuspendableClosableTest :
    SuspendableClosableTest<ResourceSet<ObservableClosable>>() {

  private val coroutines = realisticCoroutinesTestingComponent()
  private lateinit var resourceSet: ResourceSet<ObservableClosable>

  @Before
  fun setUp() {
    resourceSet = runBlocking {
      DaggerResourceSetSuspendableTestsComponent.builder()
          .consuming(resourceSetComponent(coroutines, standardObservableClosableComponent()))
          .build()
          .factory()
          .createResourceSet()
    }
  }

  override fun subject() = resourceSet

  override fun testDispatcher(): CoroutineDispatcher = coroutines.ioDispatcher()
}

@Scope annotation class ResourceSetSuspendableTestsScope

@ResourceSetSuspendableTestsScope
@Component(dependencies = [ResourceSetComponent::class])
interface ResourceSetSuspendableTestsComponent {
  fun factory(): ResourceSet.Factory

  @Component.Builder
  interface Builder {

    fun consuming(manager: ResourceSetComponent): Builder

    fun build(): ResourceSetSuspendableTestsComponent
  }
}
