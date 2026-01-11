package com.jackbradshaw.otter.demo

import com.jackbradshaw.otter.OtterComponent
import com.jackbradshaw.otter.demo.items.CubeLevel
import com.jackbradshaw.otter.demo.items.ItemsModule
import com.jackbradshaw.otter.demo.materials.MaterialsModule
import com.jackbradshaw.otter.demo.support.SupportModule
import dagger.Component

interface DemoComponent {
  fun world(): CubeLevel
}

@DemoScope
@Component(
    modules = [MaterialsModule::class, SupportModule::class, ItemsModule::class],
    dependencies = [OtterComponent::class])
interface ProdDemoComponent : DemoComponent {
  @Component.Builder
  interface Builder {
    fun consuming(otterComponent: OtterComponent): Builder

    fun build(): ProdDemoComponent
  }
}

fun demo(otter: OtterComponent): DemoComponent =
    DaggerProdDemoComponent.builder().consuming(otter).build()
