package com.jackbradshaw.concurrency.pulsar.testing

import com.jackbradshaw.concurrency.ConcurrencyScope
import com.jackbradshaw.coroutines.CoroutinesFake
import com.jackbradshaw.coroutines.DaggerCoroutinesFake
import dagger.Component
import javax.inject.Inject
import kotlinx.coroutines.test.TestScope

class TestPulsarImplTest : TestPulsarTest() {

  @Inject lateinit var subject: TestPulsar

  @Inject lateinit var testScope: TestScope

  override fun setup() {
    DaggerTestComponent.builder().setCoroutines(DaggerCoroutinesFake.create()).build().inject(this)
  }

  override fun subject() = subject

  override fun testScope() = testScope
}

@ConcurrencyScope
@Component(dependencies = [CoroutinesFake::class], modules = [TestPulsarModule::class])
interface TestComponent {
  fun inject(target: TestPulsarImplTest)

  @Component.Builder
  interface Builder {
    fun setCoroutines(coroutines: CoroutinesFake): Builder

    fun build(): TestComponent
  }
}
