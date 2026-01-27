package com.jackbradshaw.backstab.core.generator

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import dagger.Component

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
