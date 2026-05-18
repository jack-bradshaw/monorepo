package com.jackbradshaw.closet.resourcemanager.set

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.ObservableClosableTest
import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Scope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceSetImplAsObservableClosableTest :
    ObservableClosableTest<ResourceSet<ObservableClosable>>() {

  private val coroutines = realisticCoroutinesTestingComponent()
  private lateinit var resourceSet: ResourceSet<ObservableClosable>

  @Before
  fun setUp() {
    resourceSet = runBlocking {
      DaggerResourceSetObservableTestsComponent.builder()
          .consuming(resourceSetComponent(coroutines, standardObservableClosableComponent()))
          .build()
          .factory()
          .createResourceSet()
    }
  }

  override fun subject() = resourceSet

  override fun testDispatcher(): CoroutineDispatcher = coroutines.ioDispatcher()
}

@Scope annotation class ResourceSetObservableTestsScope

@ResourceSetObservableTestsScope
@Component(dependencies = [ResourceSetComponent::class])
interface ResourceSetObservableTestsComponent {
  fun factory(): ResourceSet.Factory

  @Component.Builder
  interface Builder {

    fun consuming(manager: ResourceSetComponent): Builder

    fun build(): ResourceSetObservableTestsComponent
  }
}
