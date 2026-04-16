package com.jackbradshaw.backstab.core.generator

import com.jackbradshaw.backstab.core.CoreScope
import dagger.Component
import javax.inject.Inject
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GeneratorImplTest : GeneratorTest() {

  @Inject lateinit var generator: Generator

  @Before
  fun setUp() {
    DaggerGeneratorTestComponent.create().inject(this)
  }

  override fun subject(): Generator = generator
}

@CoreScope
@Component(modules = [GeneratorImplModule::class])
interface GeneratorTestComponent {
  fun inject(test: GeneratorImplTest)
}
