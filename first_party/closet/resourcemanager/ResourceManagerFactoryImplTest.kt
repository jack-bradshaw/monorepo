package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.coroutines.coroutinesComponent
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Scope
import dagger.Component
import javax.inject.Inject

@RunWith(JUnit4::class)
class ResourceManagerFactoryImplTest : ResourceManagerFactoryTest() {

  @Inject internal lateinit var factory: ResourceManagerFactory

  @Before
  fun setUp() {
    val coroutinesComponent = coroutinesComponent()
    
    DaggerResourceManagerFactoryImplTest_TestComponent.builder()
      .coroutines(coroutinesComponent)
      .build()
      .inject(this)
  }

  override fun subject() = factory

  @Scope
  annotation class TestScope

  @TestScope
  @Component(
    dependencies = [com.jackbradshaw.coroutines.CoroutinesComponent::class],
    modules = [ResourceManagerModule::class]
  )
  interface TestComponent {
    fun inject(target: ResourceManagerFactoryImplTest)

    @Component.Builder
    interface Builder {
      fun coroutines(coroutines: com.jackbradshaw.coroutines.CoroutinesComponent): Builder
      fun build(): TestComponent
    }
  }
}
