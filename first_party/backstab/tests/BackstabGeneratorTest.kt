package com.jackbradshaw.backstab.tests

import com.jackbradshaw.backstab.processor.BackstabComponent
import com.jackbradshaw.backstab.processor.BackstabGenerator
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import org.junit.Test
import org.junit.Assert.assertTrue

class BackstabGeneratorTest {

    @Test
    fun `generate module for simple component`() {
        val component = BackstabComponent(
            packageName = "com.example",
            simpleName = "MyComponent",
            builderBindings = emptyList()
        )

        val fileSpec = BackstabGenerator.generate(component)
        val content = fileSpec.toString()

        assertContent(content, 
            "package com.example",
            "object MyComponentAutoModule",
            "@Module",
            "@Provides",
            "@MetaScope",
            "fun provideMyComponent(): MyComponent",
            "DaggerMyComponent.builder()",
            ".build()"
        )
    }

    @Test
    fun `generate module with single binding`() {
        val component = BackstabComponent(
            packageName = "com.example",
            simpleName = "MyComponent",
            builderBindings = listOf(
                BackstabComponent.ComponentBuilderMethod(
                    methodName = "bindInt",
                    paramName = "myInt",
                    paramType = ClassName("kotlin", "Int")
                )
            )
        )

        val fileSpec = BackstabGenerator.generate(component)
        val content = fileSpec.toString()

        assertContent(content,
            "fun provideMyComponent(myInt: Int): MyComponent",
            ".bindInt(myInt)"
        )
    }

    @Test
    fun `generate module with multiple bindings`() {
        val component = BackstabComponent(
            packageName = "com.example",
            simpleName = "ComplexComponent",
            builderBindings = listOf(
                BackstabComponent.ComponentBuilderMethod("foo", "fooParam", ClassName("com.example", "Foo")),
                BackstabComponent.ComponentBuilderMethod("bar", "barParam", ClassName("com.example", "Bar"))
            )
        )

        val fileSpec = BackstabGenerator.generate(component)
        val content = fileSpec.toString()

        assertContent(content,
            "fun provideComplexComponent(",
            "fooParam: Foo",
            "barParam: Bar",
            ")",
            ".foo(fooParam)",
            ".bar(barParam)"
        )
    }

    private fun assertContent(content: String, vararg expectedSnippets: String) {
        val missing = expectedSnippets.filter { !content.contains(it) }
        if (missing.isNotEmpty()) {
            throw AssertionError(
                "Generated content missing snippets: $missing\n" +
                "Full content:\n$content"
            )
        }
    }
}
