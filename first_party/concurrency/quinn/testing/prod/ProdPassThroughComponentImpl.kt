package com.jackbradshaw.concurrency.quinn.testing.prod

import com.jackbradshaw.concurrency.quinn.Quinn
import com.jackbradshaw.concurrency.quinn.QuinnComponent
import com.jackbradshaw.concurrency.quinn.quinnComponent
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(
    dependencies = [QuinnComponent::class],
    modules = [ProdPassThroughComponentImpl.ProdPassThroughModule::class])
interface ProdPassThroughComponentImpl : ProdPassThroughComponent {
  @Component.Builder
  interface Builder {
    fun consuming(component: QuinnComponent): Builder

    fun build(): ProdPassThroughComponentImpl
  }

  @Module
  object ProdPassThroughModule {
    @Provides @Prod fun provideProd(factory: Quinn.Factory): Quinn.Factory = factory
  }
}

fun prodPassThroughComponent(
    quinnComponent: QuinnComponent = quinnComponent()
): ProdPassThroughComponent =
    DaggerProdPassThroughComponentImpl.builder().consuming(quinnComponent).build()
