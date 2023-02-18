package io.jackbradshaw.otter.demo

import dagger.Component
import io.jackbradshaw.otter.OtterComponent
import io.jackbradshaw.otter.demo.items.CubeLevel
import io.jackbradshaw.otter.demo.items.ItemsModule
import io.jackbradshaw.otter.demo.materials.MaterialsModule
import io.jackbradshaw.otter.demo.support.SupportModule
<<<<<<< HEAD
import io.jackbradshaw.otter.OtterComponent
=======
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b

@DemoScope
@Component(
    modules = [MaterialsModule::class, SupportModule::class, ItemsModule::class],
    dependencies = [OtterComponent::class])
interface DemoComponent {

  fun world(): CubeLevel

  @Component.Builder
  interface Builder {
    fun setOtterComponent(otterComponent: OtterComponent): Builder
    fun build(): DemoComponent
  }
}

fun demo(otter: OtterComponent) = DaggerDemoComponent.builder().setOtterComponent(otter).build()
