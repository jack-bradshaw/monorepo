package com.jackbradshaw.sealant

import com.jackbradshaw.closet.observable.standard.StandardObservableClosableComponent
import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import com.jackbradshaw.closet.resourcemanager.set.ResourceSetComponent
import com.jackbradshaw.closet.resourcemanager.set.resourceSetComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.sealant.hub.SealedHubModule
import com.jackbradshaw.sealant.source.SealedSourceModule
import dagger.Component

@SealantScope
@Component(
    modules = [SealedHubModule::class, SealedSourceModule::class],
    dependencies =
        [
            ResourceSetComponent::class,
            CoroutinesComponent::class,
            StandardObservableClosableComponent::class])
interface SealantComponentImpl : SealantComponent

fun sealantComponent(
    coroutinesComponent: CoroutinesComponent = coroutinesComponent(),
    standardObservableClosableComponent: StandardObservableClosableComponent =
        standardObservableClosableComponent()
): SealantComponent =
    DaggerSealantComponentImpl.builder()
        .resourceSetComponent(
            resourceSetComponent(coroutinesComponent, standardObservableClosableComponent))
        .coroutinesComponent(coroutinesComponent)
        .standardObservableClosableComponent(standardObservableClosableComponent)
        .build()
