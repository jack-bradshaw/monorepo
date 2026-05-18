package com.jackbradshaw.closet.resourcemanager.map

import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import dagger.Component
import javax.inject.Inject
import javax.inject.Scope
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceMapFactoryImplTest : ResourceMapFactoryTest() {

  @Inject internal lateinit var factory: ResourceMap.Factory

  @Before
  fun setUp() {
    DaggerTestComponent.builder()
        .consuming(
            resourceMapComponent(coroutinesComponent(), standardObservableClosableComponent()))
        .build()
        .inject(this)
  }

  override fun subject() = factory
}

@Scope annotation class TestScope

@TestScope
@Component(dependencies = [ResourceMapComponent::class])
interface TestComponent {
  fun inject(target: ResourceMapFactoryImplTest)

  @Component.Builder
  interface Builder {
    fun consuming(manager: ResourceMapComponent): Builder

    fun build(): TestComponent
  }
}
