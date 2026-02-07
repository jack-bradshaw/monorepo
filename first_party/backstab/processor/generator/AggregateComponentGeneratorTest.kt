package com.jackbradshaw.backstab.processor.generator

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.backstab.processor.model.BackstabComponent
import com.jackbradshaw.backstab.processor.model.BackstabComponent.Builder
import com.jackbradshaw.backstab.processor.model.BackstabComponent.BuilderFunction
import com.jackbradshaw.backstab.processor.model.BackstabComponent.Create
import com.jackbradshaw.backstab.processor.model.BackstabComponent.Factory
import com.jackbradshaw.backstab.processor.model.BackstabComponent.FactoryParameter
import com.jackbradshaw.backstab.processor.model.BackstabComponent.Qualification
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Checks that the generator correctly handles the various instantiator types and binding topologies
 * supported by Dagger.
 *
 * **Testing Dimensions:**
 * 1. **Instantiator Type:**
 *     * `Create`: Implicit creation via `create()`.
 *     * `Builder`: Explicit `@Component.Builder`.
 *     * `Factory`: Explicit `@Component.Factory`.
 * 2. **Binding Topology:**
 *     * `BindsInstance`: Binding instances directly (Simple, Named, Qualified).
 *     * `ComponentDependency`: Binding other components as dependencies.
 *     * `Mixed`: Combinations of instance bindings and component dependencies.
 *     * `Empty`: Components with no modules or dependencies.
 */
abstract class AggregateComponentGeneratorTest {

  /** **Subject:** The [AggregateComponentGenerator] under test. */
  abstract fun subject(): AggregateComponentGenerator

  /**
   * **Dimensions:** `Builder` + `BindsInstance` (Simple)
   *
   * Verifies that the generator correctly handles a component with an `@Component.Builder` that
   * binds a simple instance.
   */
  @Test
  fun generateModule_builder_bindsInstance() = runBlocking {
    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("MyComponent"),
            instantiator =
                Builder(
                    buildFunction =
                        BuilderFunction("build", ClassName("com.example", "MyComponent")),
                    componentBindings =
                        listOf(
                            BuilderFunction(
                                functionName = "bindFoo",
                                paramType = ClassName("com.example", "Foo"))),
                    instanceBindings = emptyList()))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object MyComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideMyComponent(arg0: Foo): MyComponent = DaggerMyComponent.builder()
                .bindFoo(arg0)
                .build()
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Builder` + `BindsInstance` (Named Qualification)
   *
   * Verifies that the generator correctly handles a component with an `@Component.Builder` that
   * binds an instance qualified with `@Named`.
   */
  @Test
  fun generateModule_builder_bindsInstance_named() = runBlocking {
    val qualifierAnnotation =
        AnnotationSpec.builder(ClassName("javax.inject", "Named"))
            .addMember("%S", "myQualifier")
            .build()

    val qualification = Qualification.Named(qualifierAnnotation)

    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("QualifiedComponent"),
            instantiator =
                Builder(
                    buildFunction =
                        BuilderFunction("build", ClassName("com.example", "QualifiedComponent")),
                    componentBindings = emptyList(),
                    instanceBindings =
                        listOf(
                            BuilderFunction(
                                functionName = "bindFoo",
                                paramType = ClassName("com.example", "Foo"),
                                qualification = qualification))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides
            import javax.inject.Named

            @Module
            public object QualifiedComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideQualifiedComponent(@Named("myQualifier") arg0: Foo): QualifiedComponent =
                  DaggerQualifiedComponent.builder()
                .bindFoo(arg0)
                .build()
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Builder` + `BindsInstance` (Custom Qualification)
   *
   * Verifies that the generator correctly handles a component with an `@Component.Builder` that
   * binds an instance qualified with a custom qualifier annotation.
   */
  @Test
  fun generateModule_builder_bindsInstance_qualified() = runBlocking {
    val qualifierAnnotation =
        AnnotationSpec.builder(ClassName("com.example", "MyQualifier")).build()

    val qualification = Qualification.Qualifier(qualifierAnnotation)

    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("CustomQualifiedComponent"),
            instantiator =
                Builder(
                    buildFunction =
                        BuilderFunction(
                            "build", ClassName("com.example", "CustomQualifiedComponent")),
                    componentBindings = emptyList(),
                    instanceBindings =
                        listOf(
                            BuilderFunction(
                                functionName = "bindFoo",
                                paramType = ClassName("com.example", "Foo"),
                                qualification = qualification))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object CustomQualifiedComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideCustomQualifiedComponent(@MyQualifier arg0: Foo): CustomQualifiedComponent =
                  DaggerCustomQualifiedComponent.builder()
                .bindFoo(arg0)
                .build()
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Builder` + `ComponentDependency`
   *
   * Verifies that the generator correctly handles a component that depends on another component.
   */
  @Test
  fun generateModule_builder_componentDependency() = runBlocking {
    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("DependentComponent"),
            instantiator =
                Builder(
                    buildFunction =
                        BuilderFunction("build", ClassName("com.example", "DependentComponent")),
                    componentBindings =
                        listOf(
                            BuilderFunction(
                                "depComponent", ClassName("com.example", "BaseComponent"))),
                    instanceBindings = emptyList()))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object DependentComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideDependentComponent(arg0: BaseComponent): DependentComponent =
                  DaggerDependentComponent.builder()
                .depComponent(arg0)
                .build()
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Builder` + `Mixed` (Dependency + BindsInstance + Qualifiers)
   *
   * Verifies that the generator correctly handles a component with a mixture of both component
   * dependencies and instance bindings.
   */
  @Test
  fun generateModule_builder_mixedBindings() = runBlocking {
    val namedAnnotation =
        AnnotationSpec.builder(ClassName("javax.inject", "Named"))
            .addMember("%S", "myQualifier")
            .build()
    val namedQualification = Qualification.Named(namedAnnotation)

    val customAnnotation = AnnotationSpec.builder(ClassName("com.example", "MyQualifier")).build()
    val customQualification = Qualification.Qualifier(customAnnotation)

    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("MixedComponent"),
            instantiator =
                Builder(
                    buildFunction =
                        BuilderFunction("build", ClassName("com.example", "MixedComponent")),
                    componentBindings =
                        listOf(BuilderFunction("dep", ClassName("com.example", "DepComponent"))),
                    instanceBindings =
                        listOf(
                            BuilderFunction("bindSimple", ClassName("com.example", "Simple")),
                            BuilderFunction(
                                "bindNamed",
                                ClassName("com.example", "NamedFoo"),
                                namedQualification),
                            BuilderFunction(
                                "bindQualified",
                                ClassName("com.example", "QualifiedBar"),
                                customQualification))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides
            import javax.inject.Named

            @Module
            public object MixedComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideMixedComponent(
                arg0: DepComponent,
                arg1: Simple,
                @Named("myQualifier") arg2: NamedFoo,
                @MyQualifier arg3: QualifiedBar,
              ): MixedComponent = DaggerMixedComponent.builder()
                .dep(arg0)
                .bindSimple(arg1)
                .bindNamed(arg2)
                .bindQualified(arg3)
                .build()
            }
        """
                .trimIndent() + "\n")
  }

  @Test
  fun generateModule_builder_multipleComponentBindings() = runBlocking {
    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("ComplexComponent"),
            instantiator =
                Builder(
                    buildFunction =
                        BuilderFunction("build", ClassName("com.example", "ComplexComponent")),
                    componentBindings =
                        listOf(
                            BuilderFunction("dep1", ClassName("com.example", "Dep1")),
                            BuilderFunction("dep2", ClassName("com.example", "Dep2"))),
                    instanceBindings = emptyList()))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object ComplexComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideComplexComponent(arg0: Dep1, arg1: Dep2): ComplexComponent =
                  DaggerComplexComponent.builder()
                .dep1(arg0)
                .dep2(arg1)
                .build()
            }
        """
                .trimIndent() + "\n")
  }

  @Test
  fun generateModule_builder_multipleInstanceBindings() = runBlocking {
    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("ComplexComponent"),
            instantiator =
                Builder(
                    buildFunction =
                        BuilderFunction("build", ClassName("com.example", "ComplexComponent")),
                    componentBindings = emptyList(),
                    instanceBindings =
                        listOf(
                            BuilderFunction("bindFoo", ClassName("com.example", "Foo")),
                            BuilderFunction("bindBar", ClassName("com.example", "Bar")))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object ComplexComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideComplexComponent(arg0: Foo, arg1: Bar): ComplexComponent =
                  DaggerComplexComponent.builder()
                .bindFoo(arg0)
                .bindBar(arg1)
                .build()
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Builder` + `Empty`
   *
   * Verifies that the generator correctly handles a component with no bindings.
   */
  @Test
  fun generateModule_builder_noBindings() = runBlocking {
    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("MyComponent"),
            instantiator =
                Builder(
                    buildFunction =
                        BuilderFunction("build", ClassName("com.example", "MyComponent")),
                    componentBindings = emptyList(),
                    instanceBindings = emptyList()))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object MyComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideMyComponent(): MyComponent = DaggerMyComponent.builder()
                .build()
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Create`
   *
   * Verifies that the generator correctly handles a component with no builder or factory, by
   * falling back to the standard `create()` function.
   */
  @Test
  fun generateModule_create() = runBlocking {
    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("NoBuilderComponent"),
            instantiator = Create)

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object NoBuilderComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideNoBuilderComponent(): NoBuilderComponent = DaggerNoBuilderComponent.create()
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Factory` + `BindsInstance` (Simple)
   *
   * Verifies that the generator correctly handles a component with an `@Component.Factory` that
   * binds a simple instance.
   */
  @Test
  fun generateModule_factory_bindsInstance() = runBlocking {
    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("FactoryComponent"),
            instantiator =
                Factory(
                    functionName = "create",
                    parameters = listOf(FactoryParameter(ClassName("com.example", "Foo")))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object FactoryComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideFactoryComponent(arg0: Foo): FactoryComponent =
                  DaggerFactoryComponent.factory().create(arg0)
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Factory` + `BindsInstance` (Named Qualification)
   *
   * Verifies that the generator correctly handles a component with an `@Component.Factory` that
   * binds an instance qualified with `@Named`.
   */
  @Test
  fun generateModule_factory_bindsInstance_named() = runBlocking {
    val qualifierAnnotation =
        AnnotationSpec.builder(ClassName("javax.inject", "Named"))
            .addMember("%S", "myQualifier")
            .build()

    val qualification = Qualification.Named(qualifierAnnotation)

    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("FactoryComponent"),
            instantiator =
                Factory(
                    functionName = "create",
                    parameters =
                        listOf(FactoryParameter(ClassName("com.example", "Foo"), qualification))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides
            import javax.inject.Named

            @Module
            public object FactoryComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideFactoryComponent(@Named("myQualifier") arg0: Foo): FactoryComponent =
                  DaggerFactoryComponent.factory().create(arg0)
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Factory` + `BindsInstance` (Custom Qualification)
   *
   * Verifies that the generator correctly handles a component with an `@Component.Factory` that
   * binds an instance qualified with a custom qualifier annotation.
   */
  @Test
  fun generateModule_factory_bindsInstance_qualified() = runBlocking {
    val qualifierAnnotation =
        AnnotationSpec.builder(ClassName("com.example", "MyQualifier")).build()

    val qualification = Qualification.Qualifier(qualifierAnnotation)

    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("FactoryComponent"),
            instantiator =
                Factory(
                    functionName = "create",
                    parameters =
                        listOf(FactoryParameter(ClassName("com.example", "Foo"), qualification))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object FactoryComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideFactoryComponent(@MyQualifier arg0: Foo): FactoryComponent =
                  DaggerFactoryComponent.factory().create(arg0)
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Factory` + `ComponentDependency`
   *
   * Verifies that the generator correctly handles a factory component that depends on another
   * component.
   */
  @Test
  fun generateModule_factory_componentDependency() = runBlocking {
    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("FactoryComponent"),
            instantiator =
                Factory(
                    functionName = "create",
                    parameters =
                        listOf(FactoryParameter(ClassName("com.example", "BaseComponent")))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object FactoryComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideFactoryComponent(arg0: BaseComponent): FactoryComponent =
                  DaggerFactoryComponent.factory().create(arg0)
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Factory` + `Mixed` (Dependency + BindsInstance + Qualifiers)
   *
   * Verifies that the generator correctly handles a factory component with a mixture of both
   * component dependencies and instance bindings.
   */
  @Test
  fun generateModule_factory_mixedBindings() = runBlocking {
    val namedAnnotation =
        AnnotationSpec.builder(ClassName("javax.inject", "Named"))
            .addMember("%S", "myQualifier")
            .build()
    val namedQualification = Qualification.Named(namedAnnotation)

    val customAnnotation = AnnotationSpec.builder(ClassName("com.example", "MyQualifier")).build()
    val customQualification = Qualification.Qualifier(customAnnotation)

    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("FactoryComponent"),
            instantiator =
                Factory(
                    functionName = "create",
                    parameters =
                        listOf(
                            FactoryParameter(ClassName("com.example", "DepComponent")),
                            FactoryParameter(ClassName("com.example", "Simple")),
                            FactoryParameter(
                                ClassName("com.example", "NamedFoo"), namedQualification),
                            FactoryParameter(
                                ClassName("com.example", "QualifiedBar"), customQualification))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides
            import javax.inject.Named

            @Module
            public object FactoryComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideFactoryComponent(
                arg0: DepComponent,
                arg1: Simple,
                @Named("myQualifier") arg2: NamedFoo,
                @MyQualifier arg3: QualifiedBar,
              ): FactoryComponent = DaggerFactoryComponent.factory().create(arg0, arg1, arg2, arg3)
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Factory` + `ComponentDependency` (Multiple)
   *
   * Verifies that the generator correctly handles a factory component with multiple component
   * dependencies.
   */
  @Test
  fun generateModule_factory_multipleComponentBindings() = runBlocking {
    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("FactoryComponent"),
            instantiator =
                Factory(
                    functionName = "create",
                    parameters =
                        listOf(
                            FactoryParameter(ClassName("com.example", "Dep1")),
                            FactoryParameter(ClassName("com.example", "Dep2")))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object FactoryComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideFactoryComponent(arg0: Dep1, arg1: Dep2): FactoryComponent =
                  DaggerFactoryComponent.factory().create(arg0, arg1)
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Factory` + `BindsInstance` (Multiple)
   *
   * Verifies that the generator correctly handles a factory component with multiple instance
   * bindings.
   */
  @Test
  fun generateModule_factory_multipleInstanceBindings() = runBlocking {
    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("FactoryComponent"),
            instantiator =
                Factory(
                    functionName = "create",
                    parameters =
                        listOf(
                            FactoryParameter(ClassName("com.example", "Foo")),
                            FactoryParameter(ClassName("com.example", "Bar")))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object FactoryComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideFactoryComponent(arg0: Foo, arg1: Bar): FactoryComponent =
                  DaggerFactoryComponent.factory().create(arg0, arg1)
            }
        """
                .trimIndent() + "\n")
  }

  /**
   * **Dimensions:** `Factory` + `Empty`
   *
   * Verifies that the generator correctly handles a factory component with no bindings.
   */
  @Test
  fun generateModule_factory_noBindings() = runBlocking {
    val component =
        BackstabComponent(
            packageName = "com.example",
            names = listOf("FactoryComponent"),
            instantiator = Factory(functionName = "create", parameters = emptyList()))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
            import dagger.Module
            import dagger.Provides

            @Module
            public object FactoryComponent_AggregateModule {
              @Provides
              @AggregateScope
              public fun provideFactoryComponent(): FactoryComponent = DaggerFactoryComponent.factory().create()
            }
        """
                .trimIndent() + "\n")
  }
}
