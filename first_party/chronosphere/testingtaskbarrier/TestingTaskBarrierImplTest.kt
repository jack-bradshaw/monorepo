package com.jackbradshaw.chronosphere.testingtaskbarrier

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.idleable.Idleable
import dagger.Component
import javax.inject.Scope
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestingTaskBarrierImplTest : TestingTaskBarrierTest() {

  private lateinit var underTest: TestingTaskBarrier

  override fun setupSubject(gating: Set<Idleable>) {
    underTest =
        DaggerTestingTaskBarrierImplTest_TestComponent.builder()
            .consuming(testingTaskBarrierComponent())
            .build()
            .factory()
            .create(gating)
  }

  override fun subject() = underTest

  @Test
  fun isIdle_nothingGated_returnsTrue() = runBlocking {
    setupSubject(emptySet())

    val barrier = subject()

    assertThat(barrier.isIdle()).isTrue()
  }

  @Test
  fun isIdle_allGatedIdle_returnsTrue() = runBlocking {
    val idleables = List(5) { TestingTaskBarrierTest.ControllableIdleable() }
    setupSubject(idleables.toSet())
    val barrier = subject()

    // No action to perform, idleables start as idle = true

    assertThat(barrier.isIdle()).isTrue()
  }

  @Test
  fun isIdle_oneGatedNonIdle_returnsFalse() = runBlocking {
    val idleables = List(2) { TestingTaskBarrierTest.ControllableIdleable() }
    setupSubject(idleables.toSet())
    val barrier = subject()

    // All others start as idle = true
    val slowIdleable = idleables[1]
    slowIdleable.idle = false

    assertThat(barrier.isIdle()).isFalse()
  }

  @Scope annotation class TestScope

  @TestScope
  @Component(dependencies = [TestingTaskBarrierComponent::class])
  interface TestComponent {

    fun factory(): TestingTaskBarrier.Factory

    @Component.Builder
    interface Builder {
      fun consuming(taskBarrier: TestingTaskBarrierComponent): Builder

      fun build(): TestComponent
    }
  }
}
