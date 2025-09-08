package com.jackbradshaw.otter.demo

import com.jackbradshaw.otter.OtterComponent
import com.jackbradshaw.otter.demo.items.CubeLevel
import com.jackbradshaw.otter.demo.items.ItemsModule
import com.jackbradshaw.otter.demo.materials.MaterialsModule
import com.jackbradshaw.otter.demo.support.SupportModule
import dagger.Component

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
