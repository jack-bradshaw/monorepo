package com.jackbradshaw.closet.resourcemanager.map

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.ObservableClosableTest
import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Scope
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceMapImplAsObservableClosableTest :
    ObservableClosableTest<ResourceMap<String, ObservableClosable>>() {

  private val coroutines = realisticCoroutinesTestingComponent()
  private lateinit var resourceMap: ResourceMap<String, ObservableClosable>

  @Before
  fun setUp() {
    resourceMap = runBlocking {
      DaggerResourceMapObservableTestsComponent.builder()
          .consuming(resourceMapComponent(coroutines, standardObservableClosableComponent()))
          .build()
          .factory()
          .createResourceMap()
    }
  }

  override fun subject() = resourceMap

  override fun testDispatcher() = coroutines.ioDispatcher()
}

@Scope annotation class ResourceMapObservableTestsScope

@ResourceMapObservableTestsScope
@Component(dependencies = [ResourceMapComponent::class])
interface ResourceMapObservableTestsComponent {
  fun factory(): ResourceMap.Factory

  @Component.Builder
  interface Builder {

    fun consuming(manager: ResourceMapComponent): Builder

    fun build(): ResourceMapObservableTestsComponent
  }
}
