package com.jackbradshaw.backstab.core.generator

import com.jackbradshaw.backstab.core.model.BackstabComponent
import com.jackbradshaw.backstab.core.model.BackstabComponent.Builder
import com.jackbradshaw.backstab.core.model.BackstabComponent.BuilderMethod
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test


abstract class MetaComponentGeneratorTest {

    abstract fun subject(): MetaComponentGenerator

    @Test
    fun generateModuleForSimpleComponent() = runBlocking {
        val component = BackstabComponent(
            packageName = "com.example",
            name = "MyComponent",
            builder = Builder(emptyList())
        )

        val fileSpec = subject().generate(component)
        val content = fileSpec.toString()

        assertThat(content).isEqualTo("""
            package com.example

            import com.jackbradshaw.backstab.annotations.meta.MetaScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object MyComponentAutoModule {
              @Provides
              @MetaScope
              public fun provideMyComponent(): MyComponent = DaggerMyComponent.builder()
                .build()
            }
        """.trimIndent() + "\n")
    }
    @Test
    fun generateModuleForComponentWithoutBuilder() = runBlocking {
        val component = BackstabComponent(
            packageName = "com.example",
            name = "NoBuilderComponent",
            builder = null
        )

        val fileSpec = subject().generate(component)
        val content = fileSpec.toString()

        assertThat(content).isEqualTo("""
            package com.example

            import com.jackbradshaw.backstab.annotations.meta.MetaScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object NoBuilderComponentAutoModule {
              @Provides
              @MetaScope
              public fun provideNoBuilderComponent(): NoBuilderComponent = DaggerNoBuilderComponent.create()
            }
        """.trimIndent() + "\n")
    }

    @Test
    fun generateModuleWithSingleBinding() = runBlocking {
        val component = BackstabComponent(
            packageName = "com.example",
            name = "MyComponent",
            builder = Builder(
                listOf(
                    BuilderMethod(
                        methodName = "bindFoo",
                        paramType = ClassName("com.example", "Foo")
                    )
                )
            )
        )

        val fileSpec = subject().generate(component)
        val content = fileSpec.toString()

        assertThat(content).isEqualTo("""
            package com.example

            import com.jackbradshaw.backstab.annotations.meta.MetaScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object MyComponentAutoModule {
              @Provides
              @MetaScope
              public fun provideMyComponent(arg0: Foo): MyComponent = DaggerMyComponent.builder()
                .bindFoo(arg0)
                .build()
            }
        """.trimIndent() + "\n")
    }

    @Test
    fun generateModuleWithMultipleBindings() = runBlocking {
        val component = BackstabComponent(
            packageName = "com.example",
            name = "ComplexComponent",
            builder = Builder(
                listOf(
                    BuilderMethod("foo", ClassName("com.example", "Foo")),
                    BuilderMethod("bar", ClassName("com.example", "Bar"))
                )
            )
        )

        val fileSpec = subject().generate(component)
        val content = fileSpec.toString()

        assertThat(content).isEqualTo("""
            package com.example

            import com.jackbradshaw.backstab.annotations.meta.MetaScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object ComplexComponentAutoModule {
              @Provides
              @MetaScope
              public fun provideComplexComponent(arg0: Foo, arg1: Bar): ComplexComponent =
                  DaggerComplexComponent.builder()
                .foo(arg0)
                .bar(arg1)
                .build()
            }
        """.trimIndent() + "\n")
    }

    @Test
    fun generateModuleWithQualifiedBinding() = runBlocking {
        val qualifierAnnotation = AnnotationSpec.builder(
            ClassName("javax.inject", "Named")
        ).addMember("%S", "myQualifier").build()

        val component = BackstabComponent(
            packageName = "com.example",
            name = "QualifiedComponent",
            builder = Builder(
                listOf(
                    BuilderMethod(
                        methodName = "bindFoo",
                        paramType = ClassName("com.example", "Foo").copy(annotations = listOf(qualifierAnnotation))
                    )
                )
            )
        )

        val fileSpec = subject().generate(component)
        val content = fileSpec.toString()

        assertThat(content).isEqualTo("""
            package com.example

            import com.jackbradshaw.backstab.annotations.meta.MetaScope
            import dagger.Module
            import dagger.Provides
            import javax.inject.Named

            @Module
            public object QualifiedComponentAutoModule {
              @Provides
              @MetaScope
              public fun provideQualifiedComponent(arg0: @Named("myQualifier") Foo): QualifiedComponent =
                  DaggerQualifiedComponent.builder()
                .bindFoo(arg0)
                .build()
            }
        """.trimIndent() + "\n")
    }
}