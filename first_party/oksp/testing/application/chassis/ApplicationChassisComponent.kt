package com.jackbradshaw.oksp.testing.application.chassis

import com.jackbradshaw.kale.provider.ProviderRunnerComponent
import dagger.Binds
import dagger.Component
import dagger.Module

@Module
interface ApplicationChassisModule {
  @Binds fun bind(impl: ApplicationChassisImpl): ApplicationChassis
}

@Component(dependencies = [ProviderRunnerComponent::class], modules = [ApplicationChassisModule::class])
interface ApplicationChassisComponent {
  fun chassis(): ApplicationChassis
}

fun applicationChassisComponent(
  providerRunnerComponent: ProviderRunnerComponent
): ApplicationChassisComponent =
    DaggerApplicationChassisComponent.builder().providerRunnerComponent(providerRunnerComponent).build()
