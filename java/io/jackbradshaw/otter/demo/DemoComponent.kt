package io.jackbradshaw.otter.demo

import dagger.Component
import io.jackbradshaw.otter.demo.items.CubeLevel
import io.jackbradshaw.otter.demo.items.ItemsModule
import io.jackbradshaw.otter.demo.materials.MaterialsModule
import io.jackbradshaw.otter.demo.support.SupportModule
import java.io.jackbradshaw.otter.MerovingianComponent

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