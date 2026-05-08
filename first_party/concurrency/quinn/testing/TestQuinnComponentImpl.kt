package com.jackbradshaw.concurrency.quinn.testing

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrierComponent
import com.jackbradshaw.chronosphere.testingtaskbarrier.testingTaskBarrierComponent
import com.jackbradshaw.concurrency.quinn.QuinnScope
import com.jackbradshaw.concurrency.quinn.testing.hub.IdleableQuinnHubModule
import com.jackbradshaw.concurrency.quinn.testing.prod.ProdPassThroughComponent
import com.jackbradshaw.concurrency.quinn.testing.prod.prodPassThroughComponent
import com.jackbradshaw.concurrency.quinn.testing.taskbarrier.TestingTaskBarrierModule
import dagger.Component

/** Default [TestQuinnComponent]. */
@QuinnScope
@Component(
    dependencies = [TestingTaskBarrierComponent::class, ProdPassThroughComponent::class],
    modules = [IdleableQuinnHubModule::class, TestingTaskBarrierModule::class])
interface TestQuinnComponentImpl : TestQuinnComponent {
  @Component.Builder
  interface Builder {
    fun consuming(component: TestingTaskBarrierComponent): Builder

    fun consuming(component: ProdPassThroughComponent): Builder

    fun build(): TestQuinnComponentImpl
  }
}

/** Provides a new [TestQuinnComponent]. */
fun testQuinnComponent(
    testingTaskBarrierComponent: TestingTaskBarrierComponent = testingTaskBarrierComponent(),
    prodPassThroughComponent: ProdPassThroughComponent = prodPassThroughComponent()
): TestQuinnComponent =
    DaggerTestQuinnComponentImpl.builder()
        .consuming(testingTaskBarrierComponent)
        .consuming(prodPassThroughComponent)
        .build()
