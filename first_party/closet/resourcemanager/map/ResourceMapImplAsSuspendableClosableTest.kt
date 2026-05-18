package com.jackbradshaw.closet.resourcemanager.map

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.suspending.SuspendableClosableTest
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Scope
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceMapImplAsSuspendableClosableTest :
    SuspendableClosableTest<ResourceMap<String, ObservableClosable>>() {

  private val coroutines = realisticCoroutinesTestingComponent()
  private lateinit var resourceMap: ResourceMap<String, ObservableClosable>

  @Before
  fun setUp() {
    resourceMap = runBlocking {
      DaggerResourceMapSuspendableTestsComponent.builder()
          .consuming(resourceMapComponent(coroutines))
          .build()
          .factory()
          .createResourceMap()
    }
  }

  override fun subject() = resourceMap

  override fun testDispatcher() = coroutines.ioDispatcher()
}

@Scope annotation class ResourceMapSuspendableTestsScope

@ResourceMapSuspendableTestsScope
@Component(dependencies = [ResourceMapComponent::class])
interface ResourceMapSuspendableTestsComponent {
  fun factory(): ResourceMap.Factory

  @Component.Builder
  interface Builder {

    fun consuming(manager: ResourceMapComponent): Builder

    fun build(): ResourceMapSuspendableTestsComponent
  }
}
