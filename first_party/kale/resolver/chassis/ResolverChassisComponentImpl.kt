package com.jackbradshaw.kale.resolver.chassis

import com.jackbradshaw.closet.observable.standard.StandardObservableClosableComponent
import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import com.jackbradshaw.closet.resourcemanager.set.ResourceSetComponent
import com.jackbradshaw.closet.resourcemanager.set.resourceSetComponent
import com.jackbradshaw.concurrency.quinn.QuinnComponent
import com.jackbradshaw.concurrency.quinn.quinnComponent
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
            ResourceSetComponent::class,
            ProviderRunnerComponent::class,
            StandardObservableClosableComponent::class,
            QuinnComponent::class],
    modules = [ResolverChassisModule::class])
interface ResolverChassisComponentImpl : ResolverChassisComponent {
  @Component.Builder
  interface Builder {
    fun consuming(coroutines: CoroutinesComponent): Builder

    fun consuming(resourceSet: ResourceSetComponent): Builder

    fun consuming(providerRunner: ProviderRunnerComponent): Builder

    fun consuming(quinn: QuinnComponent): Builder

    fun consuming(standard: StandardObservableClosableComponent): Builder

    fun build(): ResolverChassisComponentImpl
  }
}

/** Provides a new [ResolverChassisComponentImpl]. */
fun resolverChassisComponent(
    coroutines: CoroutinesComponent = coroutinesComponent(),
    standard: StandardObservableClosableComponent = standardObservableClosableComponent(),
    resourceSet: ResourceSetComponent = resourceSetComponent(coroutines, standard),
    providerRunner: ProviderRunnerComponent = providerRunnerComponent(),
    quinn: QuinnComponent = quinnComponent()
): ResolverChassisComponent =
    DaggerResolverChassisComponentImpl.builder()
        .consuming(coroutines)
        .consuming(resourceSet)
        .consuming(providerRunner)
        .consuming(quinn)
        .consuming(standard)
        .build()
