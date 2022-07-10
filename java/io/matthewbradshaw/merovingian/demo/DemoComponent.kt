package io.matthewbradshaw.merovingian.demo

import dagger.Component
import io.matthewbradshaw.merovingian.MerovingianComponent
import io.matthewbradshaw.merovingian.demo.items.CubeLevel
import io.matthewbradshaw.merovingian.demo.items.ItemsModule
import io.matthewbradshaw.merovingian.demo.materials.MaterialsModule
import io.matthewbradshaw.merovingian.demo.support.SupportModule

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