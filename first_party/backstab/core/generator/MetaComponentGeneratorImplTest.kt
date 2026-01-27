package com.jackbradshaw.backstab.core.generator

import dagger.Component
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MetaComponentGeneratorImplTest : MetaComponentGeneratorTest() {
  override fun subject(): MetaComponentGenerator {
    return DaggerMetaComponentGeneratorTestComponent.create().metaComponentGenerator()
  }
}

@Component(modules = [MetaComponentGeneratorModule::class])
interface MetaComponentGeneratorTestComponent {
  fun metaComponentGenerator(): MetaComponentGenerator
}
