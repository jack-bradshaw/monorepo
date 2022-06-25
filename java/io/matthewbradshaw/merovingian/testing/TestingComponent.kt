package io.matthewbradshaw.merovingian.testing

import io.matthewbradshaw.merovingian.MerovingianComponent
import dagger.Component

@TestingScope
@Component(modules = [MaterialsModule::class, ExternalModule::class], dependencies = [MerovingianComponent::class])
interface TestingComponent {

  fun game(): CubeGameFactory

  @Component.Builder
  interface Builder {
    fun setMerovingianComponent(merovingianComponent: MerovingianComponent): Builder
    fun build(): TestingComponent
  }
}

fun testing(merovingian: MerovingianComponent) =
  DaggerTestingComponent.builder().setMerovingianComponent(merovingian).build()