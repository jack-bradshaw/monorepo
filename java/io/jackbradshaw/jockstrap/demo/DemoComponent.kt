package io.jackbradshaw.jockstrap.demo

import dagger.Component
import java.io.jackbradshaw.jockstrap.MerovingianComponent
import io.jackbradshaw.jockstrap.demo.items.CubeLevel
import io.jackbradshaw.jockstrap.demo.items.ItemsModule
import io.jackbradshaw.jockstrap.demo.materials.MaterialsModule
import io.jackbradshaw.jockstrap.demo.support.SupportModule

@DemoScope
@Component(
  modules = [MaterialsModule::class, SupportModule::class, ItemsModule::class],
  dependencies = [MerovingianComponent::class]
)
interface DemoComponent {

  fun world(): CubeLevel

  @Component.Builder
  interface Builder {
    fun setMerovingianComponent(merovingianComponent: MerovingianComponent): Builder
    fun build(): DemoComponent
  }
}

fun demo(merovingian: MerovingianComponent) =
  DaggerDemoComponent.builder().setMerovingianComponent(merovingian).build()