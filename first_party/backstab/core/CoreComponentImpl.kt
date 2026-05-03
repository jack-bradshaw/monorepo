package com.jackbradshaw.backstab.core

import com.jackbradshaw.backstab.core.generator.GeneratorImplModule
import com.jackbradshaw.backstab.core.main.MainImplModule
import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.obelisk.core.services.ObeliskControlService
import com.jackbradshaw.obelisk.core.services.ObeliskDataService
import com.jackbradshaw.obelisk.core.services.ObeliskErrorService
import dagger.Component

@CoreScope
@Component(
    modules = [MainImplModule::class, GeneratorImplModule::class],
    dependencies = [ObeliskServiceComponent::class])
interface CoreComponentImpl : CoreComponent {
  @Component.Builder
  interface Builder {
    fun consuming(component: ObeliskServiceComponent): Builder

    fun build(): CoreComponentImpl
  }
}

interface ObeliskServiceComponent {
  fun dataService(): ObeliskDataService<BackstabTarget, BackstabModule>
  fun controlService(): ObeliskControlService
  fun errorService(): ObeliskErrorService<BackstabTarget>
}
