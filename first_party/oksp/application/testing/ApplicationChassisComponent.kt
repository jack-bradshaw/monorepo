package com.jackbradshaw.oksp.testing.application.chassis

import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.kale.provider.ProviderRunnerComponent
import com.jackbradshaw.quinn.QuinnComponent
import com.jackbradshaw.quinn.quinnComponent
import dagger.Component

@Component(
    dependencies =
        [ProviderRunnerComponent::class, CoroutinesComponent::class, QuinnComponent::class],
    modules = [ApplicationChassisModule::class])
interface ApplicationChassisComponent {
  fun chassis(): ApplicationChassis
}

fun applicationChassisComponent(
    providerRunnerComponent: ProviderRunnerComponent,
    coroutineComponent: CoroutinesComponent = coroutinesComponent(),
    quinn: QuinnComponent = quinnComponent()
): ApplicationChassisComponent =
    DaggerApplicationChassisComponent.builder()
        .providerRunnerComponent(providerRunnerComponent)
        .coroutinesComponent(coroutineComponent)
        .quinnComponent(quinn)
        .build()
