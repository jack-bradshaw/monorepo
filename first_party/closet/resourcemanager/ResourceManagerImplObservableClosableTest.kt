package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.ObservableClosableTest
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Scope
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceManagerImplObservableClosableTest :
    ObservableClosableTest<ResourceManager<String, ObservableClosable>>() {

  private lateinit var resourceManager: ResourceManager<String, ObservableClosable>

  @Before
  fun setUp() {
    resourceManager =
        DaggerResourceManagerObservableTestsComponent.builder()
            .consuming(resourceManagerComponent(realisticCoroutinesTestingComponent()))
            .build()
            .factory()
            .createResourceManager()
  }

  override fun subject() = resourceManager
}

@Scope annotation class ResourceManagerObservableTestsScope

@ResourceManagerObservableTestsScope
@Component(dependencies = [ResourceManagerComponent::class])
interface ResourceManagerObservableTestsComponent {
  fun factory(): ResourceManager.Factory

  @Component.Builder
  interface Builder {

    fun consuming(manager: ResourceManagerComponent): Builder

    fun build(): ResourceManagerObservableTestsComponent
  }
}
