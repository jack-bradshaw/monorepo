package com.jackbradshaw.obelisk.oksp

import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.obelisk.core.component.ObeliskScope
import com.jackbradshaw.obelisk.oksp.service.ServiceModule
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.sluice.SluiceComponent
import dagger.Component

@ObeliskScope
@Component(
    dependencies = [
        CoroutinesComponent::class,
        SluiceComponent::class,
        Application.KspComponent::class
    ],
    modules = [ServiceModule::class]
)
interface ObeliskOkspComponentImpl : ObeliskOkspComponent {
  @Component.Builder
  interface Builder {
    fun coroutinesComponent(component: CoroutinesComponent): Builder
    fun sluiceComponent(component: SluiceComponent): Builder
    fun kspComponent(component: Application.KspComponent): Builder
    fun build(): ObeliskOkspComponentImpl
  }
}
