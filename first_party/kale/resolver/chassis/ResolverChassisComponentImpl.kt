package com.jackbradshaw.kale.resolver.chassis

import com.jackbradshaw.closet.resourcemanager.ResourceManagerComponent
import com.jackbradshaw.closet.resourcemanager.resourceManagerComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.kale.KaleScope
import com.jackbradshaw.kale.provider.ProviderRunner
import com.jackbradshaw.kale.provider.ProviderRunnerComponent
import com.jackbradshaw.kale.provider.providerRunnerComponent
import dagger.Component

/** [ResolverChassisComponent] backed by a [ProviderRunner]. */
@KaleScope
@Component(
    dependencies =
        [
            CoroutinesComponent::class,
            ResourceManagerComponent::class,
            ProviderRunnerComponent::class],
    modules = [ResolverChassisModule::class])
interface ResolverChassisComponentImpl : ResolverChassisComponent {
  @Component.Builder
  interface Builder {
    fun consuming(coroutines: CoroutinesComponent): Builder

    fun consuming(resourceManager: ResourceManagerComponent): Builder

    fun consuming(providerRunner: ProviderRunnerComponent): Builder

    fun build(): ResolverChassisComponentImpl
  }
}

/** Provides a new [ResolverChassisComponentImpl]. */
fun resolverChassisComponent(
    coroutines: CoroutinesComponent = coroutinesComponent(),
    resourceManager: ResourceManagerComponent = resourceManagerComponent(coroutines),
    providerRunner: ProviderRunnerComponent = providerRunnerComponent()
): ResolverChassisComponent =
    DaggerResolverChassisComponentImpl.builder()
        .consuming(coroutines)
        .consuming(resourceManager)
        .consuming(providerRunner)
        .build()
