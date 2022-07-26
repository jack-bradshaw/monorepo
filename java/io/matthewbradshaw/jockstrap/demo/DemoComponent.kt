package io.matthewbradshaw.jockstrap.demo

import dagger.Component
import java.io.matthewbradshaw.jockstrap.MerovingianComponent
import io.matthewbradshaw.jockstrap.demo.items.CubeLevel
import io.matthewbradshaw.jockstrap.demo.items.ItemsModule
import io.matthewbradshaw.jockstrap.demo.materials.MaterialsModule
import io.matthewbradshaw.jockstrap.demo.support.SupportModule

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