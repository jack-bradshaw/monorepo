package com.jackbradshaw.otter.demo

import com.jackbradshaw.otter.OtterComponent
import com.jackbradshaw.otter.demo.items.ItemsModule
import com.jackbradshaw.otter.demo.materials.MaterialsModule
import com.jackbradshaw.otter.demo.support.SupportModule
import dagger.Component

@DemoScope
@Component(
    modules = [MaterialsModule::class, SupportModule::class, ItemsModule::class],
    dependencies = [OtterComponent::class])
interface DemoComponentImpl : DemoComponent {
  @Component.Builder
  interface Builder {
    fun consuming(otterComponent: OtterComponent): Builder

    fun build(): DemoComponentImpl
  }
}

fun demoComponent(otter: OtterComponent): DemoComponent =
    DaggerDemoComponentImpl.builder().consuming(otter).build()
