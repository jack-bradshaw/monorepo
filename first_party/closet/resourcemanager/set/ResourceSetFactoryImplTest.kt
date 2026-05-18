package com.jackbradshaw.closet.resourcemanager.set

import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import dagger.Component
import javax.inject.Inject
import javax.inject.Scope
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourceSetFactoryImplTest : ResourceSetFactoryTest() {

  @Inject internal lateinit var factory: ResourceSet.Factory

  @Before
  fun setUp() {
    DaggerTestComponent.builder()
        .consuming(
            resourceSetComponent(coroutinesComponent(), standardObservableClosableComponent()))
        .build()
        .inject(this)
  }

  override fun subject() = factory
}

@Scope annotation class TestScope

@TestScope
@Component(dependencies = [ResourceSetComponent::class])
interface TestComponent {
  fun inject(target: ResourceSetFactoryImplTest)

  @Component.Builder
  interface Builder {
    fun consuming(manager: ResourceSetComponent): Builder

    fun build(): TestComponent
  }
}
