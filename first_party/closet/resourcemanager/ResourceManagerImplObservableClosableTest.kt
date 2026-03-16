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
import com.jackbradshaw.closet.observable.ObservableClosableTest
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class ResourceManagerImplObservableClosableTest : ObservableClosableTest<ResourceManager<String, ResourceManagerImplTest.TestResource>>() {

  @Inject internal lateinit var factory: ResourceManager.Factory
  
  private lateinit var resourceManager: ResourceManager<String, ResourceManagerImplTest.TestResource>
  
  private lateinit var testScope: kotlinx.coroutines.test.TestScope

  @Before
  fun setUp() {
    val coroutinesComponent = testCoroutinesComponent()
    testScope = coroutinesComponent.testScope()
    
    DaggerResourceManagerObservableTestsComponent.builder()
      .coroutines(coroutinesComponent)
      .build()
      .inject(this)

    resourceManager = runBlocking { factory.createResourceManager() }
  }

  override fun subject() = resourceManager
}

@Scope
annotation class ResourceManagerObservableTestsScope

@ResourceManagerObservableTestsScope
@Component(
  dependencies = [CoroutinesComponent::class],
  modules = [ResourceManagerImplModule::class]
)
interface ResourceManagerObservableTestsComponent {
  fun inject(target: ResourceManagerImplObservableClosableTest)

  @Component.Builder
  interface Builder {
    fun coroutines(coroutines: CoroutinesComponent): Builder
    fun build(): ResourceManagerObservableTestsComponent
  }
}
