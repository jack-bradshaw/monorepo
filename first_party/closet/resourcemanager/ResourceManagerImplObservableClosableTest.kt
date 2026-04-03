package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.closet.observable.ObservableClosableTest
import com.jackbradshaw.coroutines.CoroutinesComponent
import dagger.Component
import javax.inject.Inject
import javax.inject.Scope
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceManagerImplObservableClosableTest :
    ObservableClosableTest<ResourceManager<String, ResourceManagerImplTest.TestResource>>() {

  @Inject internal lateinit var factory: ResourceManager.Factory

  private lateinit var resourceManager:
      ResourceManager<String, ResourceManagerImplTest.TestResource>

  @Before
  fun setUp() {
    val coroutinesComponent =
        com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent()

    DaggerResourceManagerObservableTestsComponent.builder()
        .coroutines(coroutinesComponent)
        .build()
        .inject(this)

    resourceManager = factory.createResourceManager()
  }

  override fun subject() = resourceManager
}

@Scope annotation class ResourceManagerObservableTestsScope

@ResourceManagerObservableTestsScope
@Component(
    dependencies = [CoroutinesComponent::class], modules = [ResourceManagerImplModule::class])
interface ResourceManagerObservableTestsComponent {
  fun inject(target: ResourceManagerImplObservableClosableTest)

  @Component.Builder
  interface Builder {
    fun coroutines(coroutines: CoroutinesComponent): Builder

    fun build(): ResourceManagerObservableTestsComponent
  }
}
