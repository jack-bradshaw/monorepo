package com.jackbradshaw.concurrency.pulsar

import com.jackbradshaw.concurrency.ConcurrencyScope
import dagger.Component
import javax.inject.Inject

class PulsarImplTest : PulsarTest() {

  @Inject lateinit var subject: PulsarImpl

  override fun setupSubject() {
    DaggerTestComponent.builder().build().inject(this)
  }

  override fun subject() = subject
}

@ConcurrencyScope
@Component
interface TestComponent {
  fun inject(target: PulsarImplTest)

  @Component.Builder
  interface Builder {
    fun build(): TestComponent
  }
}
