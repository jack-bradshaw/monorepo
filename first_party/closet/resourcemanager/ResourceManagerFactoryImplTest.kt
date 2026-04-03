package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import dagger.Component
import javax.inject.Inject
import javax.inject.Scope
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceManagerFactoryImplTest : ResourceManagerFactoryTest() {

  @Inject internal lateinit var factory: ResourceManager.Factory

  @Before
  fun setUp() {
    DaggerTestComponent.builder().coroutines(coroutinesComponent()).build().inject(this)
  }

  override fun subject() = factory
}

@Scope annotation class TestScope

@TestScope
@Component(
    dependencies = [CoroutinesComponent::class], modules = [ResourceManagerImplModule::class])
interface TestComponent {
  fun inject(target: ResourceManagerFactoryImplTest)

  @Component.Builder
  interface Builder {
    fun coroutines(coroutines: CoroutinesComponent): Builder

    fun build(): TestComponent
  }
}
