package io.matthewbradshaw.merovingian.demo

import dagger.Component
import io.matthewbradshaw.merovingian.MerovingianComponent
import io.matthewbradshaw.merovingian.demo.ExternalModule
import io.matthewbradshaw.merovingian.demo.materials.MaterialsModule
import io.matthewbradshaw.merovingian.demo.items.ItemsModule
import io.matthewbradshaw.merovingian.demo.items.CubeWorld

@DemoScope
@Component(
  modules = [MaterialsModule::class, ExternalModule::class, ItemsModule::class],
  dependencies = [MerovingianComponent::class]
)
interface DemoComponent {

  fun world(): CubeWorld

  @Component.Builder
  interface Builder {
    fun setMerovingianComponent(merovingianComponent: MerovingianComponent): Builder
    fun build(): DemoComponent
  }
}

fun demo(merovingian: MerovingianComponent) =
  DaggerDemoComponent.builder().setMerovingianComponent(merovingian).build()