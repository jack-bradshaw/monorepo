package io.matthewbradshaw.frankl.demo

import dagger.Component
import io.matthewbradshaw.frankl.MerovingianComponent
import io.matthewbradshaw.frankl.demo.items.CubeLevel
import io.matthewbradshaw.frankl.demo.items.ItemsModule
import io.matthewbradshaw.frankl.demo.materials.MaterialsModule
import io.matthewbradshaw.frankl.demo.support.SupportModule

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