package com.jackbradshaw.backstab.processor.generator

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.backstab.processor.model.BackstabComponent
import com.jackbradshaw.backstab.processor.model.BackstabComponent.ComponentInstantiator.BuilderInterface
import com.jackbradshaw.backstab.processor.model.BackstabComponent.ComponentInstantiator.SetterFunction
import com.jackbradshaw.backstab.processor.model.BackstabComponent.ComponentInstantiator.BuildFunction
import com.jackbradshaw.backstab.processor.model.BackstabComponent.ComponentInstantiator.CreateFunction
import com.jackbradshaw.backstab.processor.model.BackstabComponent.ComponentInstantiator.FactoryFunction
import com.jackbradshaw.backstab.processor.model.BackstabComponent.Qualification
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Checks that the generator correctly handles the various instantiator types and binding topologies
 * supported by Dagger.
 *
 * **Testing Dimensions:**
 * 1. **Instantiator Type:**
 *     * `CreateFunction`: Implicit creation via `create()`.
 *     * `BuilderInterface`: Explicit `@Component.Builder`.
 *     * `FactoryFunction`: Explicit `@Component.Factory`.
 * 2. **Binding Topology:**
 *     * `BindsInstance`: Binding instances directly (Simple, Named, Qualified).
 *     * `ComponentDependency`: Binding other components as dependencies.
 *     * `Mixed`: Combinations of instance bindings and component dependencies.
 *     * `Empty`: Components with no modules or dependencies.
 */
abstract class GeneratorTest {

  /** **Subject:** The [Generator] under test. */
  abstract fun subject(): Generator

  /**
   * **Dimensions:** `BuilderInterface` + `BindsInstance` (Simple)
   *
   * Verifies that the generator correctly handles a component with an `@Component.BuilderInterface` that
   * binds a simple instance.
   */
  @Test
  fun generateModule_builder_bindsInstance() = runBlocking {
    val component =
        BackstabComponent(
            className = ClassName("com.example", "MyComponent"),
            instantiator =
                BuilderInterface(
                    componentSetters =
                        listOf(
                            SetterFunction(
                                name = MemberName(ClassName("com.example", "MyComponent").nestedClass("Builder"), "bindFoo"),
                                type = ClassName("com.example", "Foo"))),
                    boundInstanceSetters = emptyList(),
                    buildFunction =
                        BuildFunction(MemberName(ClassName("com.example", "MyComponent").nestedClass("Builder"), "build"), ClassName("com.example", "MyComponent"))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `BuilderInterface` + `BindsInstance` (Named Qualification)
   *
   * Verifies that the generator correctly handles a component with an `@Component.BuilderInterface` that
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
            className = ClassName("com.example", "QualifiedComponent"),
            instantiator =
                BuilderInterface(
                    componentSetters = emptyList(),
                    boundInstanceSetters =
                        listOf(
                            SetterFunction(
                                name = MemberName(ClassName("com.example", "MyComponent").nestedClass("Builder"), "bindFoo"),
                                type = ClassName("com.example", "Foo"),
                                qualification = qualification)),
                    buildFunction =
                        BuildFunction(MemberName(ClassName("com.example", "QualifiedComponent").nestedClass("Builder"), "build"), ClassName("com.example", "QualifiedComponent"))
))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `BuilderInterface` + `BindsInstance` (Custom Qualification)
   *
   * Verifies that the generator correctly handles a component with an `@Component.BuilderInterface` that
   * binds an instance qualified with a custom qualifier annotation.
   */
  @Test
  fun generateModule_builder_bindsInstance_qualified() = runBlocking {
    val qualifierAnnotation =
        AnnotationSpec.builder(ClassName("com.example", "MyQualifier")).build()

    val qualification = Qualification.Qualifier(qualifierAnnotation)

    val component =
        BackstabComponent(
            className = ClassName("com.example", "CustomQualifiedComponent"),
            instantiator =
                BuilderInterface(
                    componentSetters = emptyList(),
                    boundInstanceSetters =
                        listOf(
                            SetterFunction(
                                name = MemberName(ClassName("com.example", "MyComponent").nestedClass("Builder"), "bindFoo"),
                                type = ClassName("com.example", "Foo"),
                                qualification = qualification)),
                    buildFunction =
                        BuildFunction(
                            MemberName(ClassName("com.example", "CustomQualifiedComponent").nestedClass("Builder"), "build"), ClassName("com.example", "CustomQualifiedComponent"))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `BuilderInterface` + `ComponentDependency`
   *
   * Verifies that the generator correctly handles a component that depends on another component.
   */
  @Test
  fun generateModule_builder_componentDependency() = runBlocking {
    val component =
        BackstabComponent(
            className = ClassName("com.example", "DependentComponent"),
            instantiator =
                BuilderInterface(
                    componentSetters =
                        listOf(
                            SetterFunction(
                                MemberName(ClassName("com.example", "DependentComponent").nestedClass("Builder"), "depComponent"), ClassName("com.example", "BaseComponent"))),
                    boundInstanceSetters = emptyList(),
                    buildFunction =
                        BuildFunction(MemberName(ClassName("com.example", "DependentComponent").nestedClass("Builder"), "build"), ClassName("com.example", "DependentComponent"))
))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `BuilderInterface` + `Mixed` (Dependency + BindsInstance + Qualifiers)
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
            className = ClassName("com.example", "MixedComponent"),
            instantiator =
                BuilderInterface(
                    componentSetters =
                        listOf(SetterFunction(MemberName(ClassName("com.example", "MixedComponent").nestedClass("Builder"), "dep"), ClassName("com.example", "DepComponent"))
),
                    boundInstanceSetters =
                        listOf(
                            SetterFunction(MemberName(ClassName("com.example", "MixedComponent").nestedClass("Builder"), "bindSimple"), ClassName("com.example", "Simple"))
,
                            SetterFunction(
                                MemberName(ClassName("com.example", "MixedComponent").nestedClass("Builder"), "bindNamed"),
                                ClassName("com.example", "NamedFoo"),
                                namedQualification),
                            SetterFunction(
                                MemberName(ClassName("com.example", "MixedComponent").nestedClass("Builder"), "bindQualified"),
                                ClassName("com.example", "QualifiedBar"),
                                customQualification)),
                    buildFunction =
                        BuildFunction(MemberName(ClassName("com.example", "MixedComponent").nestedClass("Builder"), "build"), ClassName("com.example", "MixedComponent"))
))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
            className = ClassName("com.example", "ComplexComponent"),
            instantiator =
                BuilderInterface(
                    componentSetters =
                        listOf(
                            SetterFunction(MemberName(ClassName("com.example", "ComplexComponent").nestedClass("Builder"), "dep1"), ClassName("com.example", "Dep1"))
,
                            SetterFunction(MemberName(ClassName("com.example", "ComplexComponent").nestedClass("Builder"), "dep2"), ClassName("com.example", "Dep2"))
),
                    boundInstanceSetters = emptyList(),
                    buildFunction =
                        BuildFunction(MemberName(ClassName("com.example", "ComplexComponent").nestedClass("Builder"), "build"), ClassName("com.example", "ComplexComponent"))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
            className = ClassName("com.example", "ComplexComponent"),
            instantiator =
                BuilderInterface(
                    componentSetters = emptyList(),
                    boundInstanceSetters =
                        listOf(
                            SetterFunction(name = MemberName(ClassName("com.example", "MyComponent").nestedClass("Builder"), "bindFoo"), type = ClassName("com.example", "Foo")),
                            SetterFunction(name = MemberName(ClassName("com.example", "MyComponent").nestedClass("Builder"), "bindBar"), type = ClassName("com.example", "Bar"))),
                    buildFunction =
                        BuildFunction(MemberName(ClassName("com.example", "ComplexComponent").nestedClass("Builder"), "build"), ClassName("com.example", "ComplexComponent"))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `BuilderInterface` + `Empty`
   *
   * Verifies that the generator correctly handles a component with no bindings.
   */
  @Test
  fun generateModule_builder_noBindings() = runBlocking {
    val component =
        BackstabComponent(
            className = ClassName("com.example", "MyComponent"),
            instantiator =
                BuilderInterface(
                    componentSetters = emptyList(),
                    boundInstanceSetters = emptyList(),
                    buildFunction =
                        BuildFunction(MemberName(ClassName("com.example", "MyComponent").nestedClass("Builder"), "build"), ClassName("com.example", "MyComponent"))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `CreateFunction`
   *
   * Verifies that the generator correctly handles a component with no builder or factory, by
   * falling back to the standard `create()` function.
   */
  @Test
  fun generateModule_create() = runBlocking {
    val component =
        BackstabComponent(
            className = ClassName("com.example", "NoBuilderComponent"),
            instantiator = CreateFunction)

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `FactoryFunction` + `BindsInstance` (Simple)
   *
   * Verifies that the generator correctly handles a component with an `@Component.FactoryFunction` that
   * binds a simple instance.
   */
  @Test
  fun generateModule_factory_bindsInstance() = runBlocking {
    val component =
        BackstabComponent(
            className = ClassName("com.example", "FactoryComponent"),
            instantiator =
                FactoryFunction(
                    name = MemberName(ClassName("com.example", "MyComponent").nestedClass("Factory"), "create"),
                    parameters = listOf(FactoryFunction.Parameter(ClassName("com.example", "Foo")))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `FactoryFunction` + `BindsInstance` (Named Qualification)
   *
   * Verifies that the generator correctly handles a component with an `@Component.FactoryFunction` that
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
            className = ClassName("com.example", "FactoryComponent"),
            instantiator =
                FactoryFunction(
                    name = MemberName(ClassName("com.example", "MyComponent").nestedClass("Factory"), "create"),
                    parameters =
                        listOf(FactoryFunction.Parameter(ClassName("com.example", "Foo"), qualification))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `FactoryFunction` + `BindsInstance` (Custom Qualification)
   *
   * Verifies that the generator correctly handles a component with an `@Component.FactoryFunction` that
   * binds an instance qualified with a custom qualifier annotation.
   */
  @Test
  fun generateModule_factory_bindsInstance_qualified() = runBlocking {
    val qualifierAnnotation =
        AnnotationSpec.builder(ClassName("com.example", "MyQualifier")).build()

    val qualification = Qualification.Qualifier(qualifierAnnotation)

    val component =
        BackstabComponent(
            className = ClassName("com.example", "FactoryComponent"),
            instantiator =
                FactoryFunction(
                    name = MemberName(ClassName("com.example", "MyComponent").nestedClass("Factory"), "create"),
                    parameters =
                        listOf(FactoryFunction.Parameter(ClassName("com.example", "Foo"), qualification))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `FactoryFunction` + `ComponentDependency`
   *
   * Verifies that the generator correctly handles a factory component that depends on another
   * component.
   */
  @Test
  fun generateModule_factory_componentDependency() = runBlocking {
    val component =
        BackstabComponent(
            className = ClassName("com.example", "FactoryComponent"),
            instantiator =
                FactoryFunction(
                    name = MemberName(ClassName("com.example", "MyComponent").nestedClass("Factory"), "create"),
                    parameters =
                        listOf(FactoryFunction.Parameter(ClassName("com.example", "BaseComponent")))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `FactoryFunction` + `Mixed` (Dependency + BindsInstance + Qualifiers)
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
            className = ClassName("com.example", "FactoryComponent"),
            instantiator =
                FactoryFunction(
                    name = MemberName(ClassName("com.example", "MyComponent").nestedClass("Factory"), "create"),
                    parameters =
                        listOf(
                            FactoryFunction.Parameter(ClassName("com.example", "DepComponent")),
                            FactoryFunction.Parameter(ClassName("com.example", "Simple")),
                            FactoryFunction.Parameter(
                                ClassName("com.example", "NamedFoo"), namedQualification),
                            FactoryFunction.Parameter(
                                ClassName("com.example", "QualifiedBar"), customQualification))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `FactoryFunction` + `ComponentDependency` (Multiple)
   *
   * Verifies that the generator correctly handles a factory component with multiple component
   * dependencies.
   */
  @Test
  fun generateModule_factory_multipleComponentBindings() = runBlocking {
    val component =
        BackstabComponent(
            className = ClassName("com.example", "FactoryComponent"),
            instantiator =
                FactoryFunction(
                    name = MemberName(ClassName("com.example", "MyComponent").nestedClass("Factory"), "create"),
                    parameters =
                        listOf(
                            FactoryFunction.Parameter(ClassName("com.example", "Dep1")),
                            FactoryFunction.Parameter(ClassName("com.example", "Dep2")))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `FactoryFunction` + `BindsInstance` (Multiple)
   *
   * Verifies that the generator correctly handles a factory component with multiple instance
   * bindings.
   */
  @Test
  fun generateModule_factory_multipleInstanceBindings() = runBlocking {
    val component =
        BackstabComponent(
            className = ClassName("com.example", "FactoryComponent"),
            instantiator =
                FactoryFunction(
                    name = MemberName(ClassName("com.example", "MyComponent").nestedClass("Factory"), "create"),
                    parameters =
                        listOf(
                            FactoryFunction.Parameter(ClassName("com.example", "Foo")),
                            FactoryFunction.Parameter(ClassName("com.example", "Bar")))))

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
   * **Dimensions:** `FactoryFunction` + `Empty`
   *
   * Verifies that the generator correctly handles a factory component with no bindings.
   */
  @Test
  fun generateModule_factory_noBindings() = runBlocking {
    val component =
        BackstabComponent(
            className = ClassName("com.example", "FactoryComponent"),
            instantiator = FactoryFunction(name = MemberName(ClassName("com.example", "FactoryComponent").nestedClass("Factory"), "create"), parameters = emptyList())
)

    val fileSpec = subject().generate(component)
    val content = fileSpec.toString()

    assertThat(content)
        .isEqualTo(
            """
            package com.example

            import com.jackbradshaw.backstab.annotations.AggregateScope
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
