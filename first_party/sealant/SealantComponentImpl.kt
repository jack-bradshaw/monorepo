package com.jackbradshaw.sealant

import com.jackbradshaw.closet.resourcemanager.ResourceManagerImplModule
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.sealant.hub.SealedHubModule
import dagger.Component

@SealantScope
@Component(
    modules =
        [SealedHubModule::class, ResourceManagerImplModule::class],
    dependencies = [CoroutinesComponent::class])
interface SealantComponentImpl : SealantComponent

fun sealantComponent(
    coroutinesComponent: CoroutinesComponent = coroutinesComponent()
): SealantComponent =
    DaggerSealantComponentImpl.builder().coroutinesComponent(coroutinesComponent).build()
