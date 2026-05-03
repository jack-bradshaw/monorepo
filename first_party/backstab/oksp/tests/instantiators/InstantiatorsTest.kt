package com.jackbradshaw.backstab.oksp.tests.instantiators

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import dagger.Component
import com.jackbradshaw.backstab.core.annotations.AggregateScope

@Component(modules = [BuilderA_BackstabModule::class])
@AggregateScope
interface BuilderAgg {
  fun target(): BuilderA
  @Component.Builder interface Builder { fun build(): BuilderAgg }
}

@Component(modules = [CustomBuildA_BackstabModule::class])
@AggregateScope
interface CustomBuildAgg {
  fun target(): CustomBuildA
  @Component.Builder interface Builder { fun execute(): CustomBuildAgg }
}

@Component(modules = [CustomFactoryA_BackstabModule::class])
@AggregateScope
interface CustomFactoryAgg {
  fun target(): CustomFactoryA
  @Component.Factory interface Factory { fun execute(): CustomFactoryAgg }
}

@Component(modules = [FactoryA_BackstabModule::class])
@AggregateScope
interface FactoryAgg {
  fun target(): FactoryA
  @Component.Factory interface Factory { fun create(): FactoryAgg }
}

@Component(modules = [ImplicitA_BackstabModule::class])
@AggregateScope
interface ImplicitAgg {
  fun target(): ImplicitA
}

@Component(modules = [CustomCreationA_BackstabModule::class])
@AggregateScope
interface CustomCreationAgg {
  fun target(): CustomCreationA
  @Component.Builder interface Builder { fun build(): CustomCreationAgg }
  companion object {
    fun execute(): CustomCreationAgg = DaggerCustomCreationAgg.builder().build()
  }
}

/**
 * Verifies that Backstab correctly integrates with various manual Dagger instantiation patterns.
 *
 * Verification is performed by building the aggregate component and checking that an object
 * constructed deep within the graph matches the object with the same qualified type pulled from the
 * aggregate. This approach ensures Dagger-provided objects are correctly shared and wired by
 * Backstab. If the components compile and if the retrieved instance is correct then the graph was
 * properly compiled and connected.
 */
@RunWith(JUnit4::class)
class InstantiatorsTest {

  @Test
  fun builderInterface_defaultName() {
    val aggregate = DaggerBuilderAgg.builder().build()
    assertThat(aggregate.target().foo()).isSameInstanceAs(BuilderModule.instance)
  }

  @Test
  fun builderInterface_customName() {
    val aggregate = DaggerCustomBuildAgg.builder().execute()
    assertThat(aggregate.target().foo()).isSameInstanceAs(CustomBuildModule.instance)
  }

  @Test
  fun factoryInterface_defaultName() {
    val aggregate = DaggerFactoryAgg.factory().create()
    assertThat(aggregate.target().foo()).isSameInstanceAs(FactoryModule.instance)
  }

  @Test
  fun factoryInterface_customName() {
    val aggregate = DaggerCustomFactoryAgg.factory().execute()
    assertThat(aggregate.target().foo()).isSameInstanceAs(CustomFactoryModule.instance)
  }

  @Test
  fun creationFunction_defaultName() {
    val aggregate = DaggerImplicitAgg.create()
    assertThat(aggregate.target().foo()).isSameInstanceAs(ImplicitModule.instance)
  }

  @Test
  fun creationFunction_customName() {
    val aggregate = CustomCreationAgg.execute()
    assertThat(aggregate.target().foo()).isSameInstanceAs(CustomCreationModule.instance)
  }
}
