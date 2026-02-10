package com.jackbradshaw.backstab.processor.generator

import com.jackbradshaw.backstab.processor.ProcessorScope
import dagger.Component
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@RunWith(JUnit4::class)
class GeneratorImplTest : GeneratorTest() {

  @Inject
  lateinit var generator: Generator

  @Before
  fun setUp() {
    DaggerGeneratorTestComponent.create().inject(this)
  }

  override fun subject(): Generator = generator
}

@ProcessorScope
@Component(modules = [GeneratorModule::class])
interface GeneratorTestComponent {
  fun inject(test: GeneratorImplTest)
}
