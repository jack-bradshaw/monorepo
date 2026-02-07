package com.jackbradshaw.backstab.processor.generator

import com.jackbradshaw.backstab.processor.BackstabCoreScope
import dagger.Component
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AggregateComponentGeneratorImplTest : AggregateComponentGeneratorTest() {
  override fun subject(): AggregateComponentGenerator {
    return DaggerAggregateComponentGeneratorTestComponent.create().aggregateComponentGenerator()
  }
}

@BackstabCoreScope
@Component(modules = [AggregateComponentGeneratorModule::class])
interface AggregateComponentGeneratorTestComponent {
  fun aggregateComponentGenerator(): AggregateComponentGenerator
}
