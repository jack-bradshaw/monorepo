package com.jackbradshaw.backstab.core

import com.jackbradshaw.backstab.core.generator.GeneratorImplModule
import com.jackbradshaw.backstab.core.main.MainImplModule
import com.jackbradshaw.backstab.core.host.HostComponent
import dagger.Component

@CoreScope
@Component(
    modules = [MainImplModule::class, GeneratorImplModule::class],
    dependencies = [HostComponent::class])
interface CoreComponentImpl : CoreComponent {
  @Component.Builder
  interface Builder {
    fun consuming(component: HostComponent): Builder
    fun build(): CoreComponentImpl
  }
}