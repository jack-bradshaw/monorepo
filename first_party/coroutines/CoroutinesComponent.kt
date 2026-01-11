package com.jackbradshaw.coroutines

import com.jackbradshaw.coroutines.io.Io
import com.jackbradshaw.coroutines.io.IoModule
import dagger.Component
import kotlinx.coroutines.CoroutineScope

/** Kotlin coroutine scopes, components, and other related objects. */
interface CoroutinesComponent {
  @Io fun ioCoroutineScope(): CoroutineScope
}

@CoroutinesScope
@Component(modules = [IoModule::class])
interface ProdCoroutinesComponent : CoroutinesComponent {
  @Component.Builder
  interface Builder {
    fun build(): ProdCoroutinesComponent
  }
}

/** Creates a default instance of [CoroutinesComponent]. */
fun coroutinesComponent(): CoroutinesComponent = DaggerProdCoroutinesComponent.builder().build()
