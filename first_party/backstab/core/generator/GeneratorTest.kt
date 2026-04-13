package com.jackbradshaw.backstab.core.generator

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.model.BackstabTarget.ComponentInstantiator.BuilderInterface
import com.jackbradshaw.backstab.core.model.BackstabTarget.ComponentInstantiator.BuilderInterface.BuildFunction
import com.jackbradshaw.backstab.core.model.BackstabTarget.ComponentInstantiator.BuilderInterface.SetterFunction
import com.jackbradshaw.backstab.core.model.BackstabTarget.ComponentInstantiator.CreateFunction
import com.jackbradshaw.backstab.core.model.BackstabTarget.ComponentInstantiator.FactoryFunction
import com.jackbradshaw.backstab.core.model.BackstabTarget.Qualifier
import com.jackbradshaw.oksp.model.SourceFile
import com.jackbradshaw.backstab.core.model.Type
import kotlinx.coroutines.runBlocking
import org.junit.Test

/** Abstract test that all instances of [Generator] should pass. */
abstract class GeneratorTest {

  @Test
  fun builder_bindsInstance_typeArguments() = runBlocking {
    /*
     * ```
     * interface DeepComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun bindDeep(arg0: Map<String, List<Int>>): Builder
     *     fun build(): DeepComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "DeepComponent",
            instantiator =
                builder(
                    setters =
                        listOf(
                            setter(
                                name = "bindDeep",
                                type =
                                    type(
                                        "Map",
                                        packageName = "kotlin.collections",
                                        typeArguments =
                                            listOf(
                                                type("String", packageName = "kotlin"),
                                                type(
                                                    "List",
                                                    packageName = "kotlin.collections",
                                                    typeArguments =
                                                        listOf(
                                                            type(
                                                                "Int",
                                                                packageName = "kotlin"))))))),
                    returnType = type("DeepComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides
        import kotlin.Int
        import kotlin.String
        import kotlin.collections.List
        import kotlin.collections.Map

        @Module
        public object DeepComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: Map<String, List<Int>>): DeepComponent =
              DaggerDeepComponent.builder()
          .bindDeep(arg0)
          .build()
        }
        """)
  }

  @Test
  fun builder_bindsInstance_namedRepeated() = runBlocking {
    /*
     * ```
     * interface QualifiedComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun bindA(@Named("A") arg0: Foo): Builder
     *     fun bindB(@Named("B") arg1: Foo): Builder
     *     fun build(): QualifiedComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "QualifiedComponent",
            instantiator =
                builder(
                    setters =
                        listOf(
                            setter("bindA", type("Foo"), Qualifier.Named("A")),
                            setter("bindB", type("Foo"), Qualifier.Named("B"))),
                    returnType = type("QualifiedComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides
        import javax.inject.Named

        @Module
        public object QualifiedComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(@Named("A") arg0: Foo, @Named("B") arg1: Foo): QualifiedComponent =
              DaggerQualifiedComponent.builder()
          .bindA(arg0)
          .bindB(arg1)
          .build()
        }
        """)
  }

  @Test
  fun builder_bindsInstance_namedSingular() = runBlocking {
    /*
     * ```
     * interface QualifiedComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun bindFoo(@Named("myQualifier") arg0: Foo): Builder
     *     fun build(): QualifiedComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "QualifiedComponent",
            instantiator =
                builder(
                    setters =
                        listOf(setter("bindFoo", type("Foo"), Qualifier.Named("myQualifier"))),
                    returnType = type("QualifiedComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides
        import javax.inject.Named

        @Module
        public object QualifiedComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(@Named("myQualifier") arg0: Foo): QualifiedComponent =
              DaggerQualifiedComponent.builder()
          .bindFoo(arg0)
          .build()
        }
        """)
  }

  @Test
  fun builder_bindsInstance_qualifierRepeated() = runBlocking {
    /*
     * ```
     * interface QualifiedComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun bindA(@QualifierA arg0: Foo): Builder
     *     fun bindB(@QualifierB arg1: Foo): Builder
     *     fun build(): QualifiedComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "QualifiedComponent",
            instantiator =
                builder(
                    setters =
                        listOf(
                            setter(
                                "bindA",
                                type("Foo"),
                                Qualifier.Custom("com.example", listOf("QualifierA"))),
                            setter(
                                "bindB",
                                type("Foo"),
                                Qualifier.Custom("com.example", listOf("QualifierB")))),
                    returnType = type("QualifiedComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object QualifiedComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(@QualifierA arg0: Foo, @QualifierB arg1: Foo): QualifiedComponent =
              DaggerQualifiedComponent.builder()
          .bindA(arg0)
          .bindB(arg1)
          .build()
        }
        """)
  }

  @Test
  fun builder_bindsInstance_qualifierSingular() = runBlocking {
    /*
     * ```
     * interface QualifiedComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun bindFoo(@MyQualifier arg0: Foo): Builder
     *     fun build(): QualifiedComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "QualifiedComponent",
            instantiator =
                builder(
                    setters =
                        listOf(
                            setter(
                                "bindFoo",
                                type("Foo"),
                                Qualifier.Custom("com.example", listOf("MyQualifier")))),
                    returnType = type("QualifiedComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object QualifiedComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(@MyQualifier arg0: Foo): QualifiedComponent =
              DaggerQualifiedComponent.builder()
          .bindFoo(arg0)
          .build()
        }
        """)
  }

  @Test
  fun builder_bindsInstance_unqualified() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun bindFoo(arg0: Foo): Builder
     *     fun build(): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            instantiator =
                builder(
                    setters = listOf(setter("bindFoo", type("Foo"))),
                    returnType = type("MyComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: Foo): MyComponent = DaggerMyComponent.builder()
          .bindFoo(arg0)
          .build()
        }
        """)
  }

  @Test
  fun builder_complex() = runBlocking {
    /*
     * ```
     * interface ComplexComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun bindComplex(@Named("myQualifier") arg0: List<String>): Builder
     *     fun bindModule(arg1: MyModule): Builder
     *     fun build(): ComplexComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "ComplexComponent",
            instantiator =
                builder(
                    setters =
                        listOf(
                            setter(
                                name = "bindComplex",
                                type =
                                    type(
                                        "List",
                                        packageName = "kotlin.collections",
                                        typeArguments =
                                            listOf(type("String", packageName = "kotlin"))),
                                qualifier = Qualifier.Named("myQualifier")),
                            setter("bindModule", type("MyModule"))),
                    returnType = type("ComplexComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides
        import javax.inject.Named
        import kotlin.String
        import kotlin.collections.List

        @Module
        public object ComplexComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(@Named("myQualifier") arg0: List<String>, arg1: MyModule):
              ComplexComponent = DaggerComplexComponent.builder()
          .bindComplex(arg0)
          .bindModule(arg1)
          .build()
        }
        """)
  }

  @Test
  fun builder_componentDependency() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun bindBase(arg0: BaseComponent): Builder
     *     fun build(): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            instantiator =
                builder(
                    setters = listOf(setter("bindBase", type("BaseComponent"))),
                    returnType = type("MyComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: BaseComponent): MyComponent = DaggerMyComponent.builder()
          .bindBase(arg0)
          .build()
        }
        """)
  }

  @Test
  fun builder_customNames() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun setFoo(arg0: Foo): Builder
     *     fun execute(): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            instantiator =
                builder(
                    setters = listOf(setter("setFoo", type("Foo"))),
                    returnType = type("MyComponent"),
                    buildName = "execute"))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: Foo): MyComponent = DaggerMyComponent.builder()
          .setFoo(arg0)
          .execute()
        }
        """)
  }

  @Test
  fun builder_deeplyNestedArgument() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun bindFoo(arg0: Outer.Inner.Deep.Foo): Builder
     *     fun build(): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            instantiator =
                builder(
                    setters = listOf(setter("bindFoo", type("Outer", "Inner", "Deep", "Foo"))),
                    returnType = type("MyComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: Outer.Inner.Deep.Foo): MyComponent = DaggerMyComponent.builder()
          .bindFoo(arg0)
          .build()
        }
        """)
  }

  @Test
  fun builder_empty() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun build(): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            instantiator = builder(setters = emptyList(), returnType = type("MyComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(): MyComponent = DaggerMyComponent.builder()
          .build()
        }
        """)
  }

  @Test
  fun builder_mixed() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun bindDep(arg0: DepComponent): Builder
     *     fun bindSimple(arg1: Simple): Builder
     *     fun bindNamedBar(@Named("myQualifier") arg2: Bar): Builder
     *     fun bindGeneric(arg3: List<String>): Builder
     *     fun bindModule(arg4: MyModule): Builder
     *     fun build(): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            instantiator =
                builder(
                    setters =
                        listOf(
                            setter("bindDep", type("DepComponent")),
                            setter("bindSimple", type("Simple")),
                            setter("bindNamedBar", type("Bar"), Qualifier.Named("myQualifier")),
                            setter(
                                "bindGeneric",
                                type(
                                    "List",
                                    packageName = "kotlin.collections",
                                    typeArguments =
                                        listOf(type("String", packageName = "kotlin")))),
                            setter("bindModule", type("MyModule"))),
                    returnType = type("MyComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides
        import javax.inject.Named
        import kotlin.String
        import kotlin.collections.List

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(
            arg0: DepComponent,
            arg1: Simple,
            @Named("myQualifier") arg2: Bar,
            arg3: List<String>,
            arg4: MyModule,
          ): MyComponent = DaggerMyComponent.builder()
          .bindDep(arg0)
          .bindSimple(arg1)
          .bindNamedBar(arg2)
          .bindGeneric(arg3)
          .bindModule(arg4)
          .build()
        }
        """)
  }

  @Test
  fun builder_module() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun myModule(arg0: MyModule): Builder
     *     fun build(): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            instantiator =
                builder(
                    setters = listOf(setter("myModule", type("MyModule"))),
                    returnType = type("MyComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: MyModule): MyComponent = DaggerMyComponent.builder()
          .myModule(arg0)
          .build()
        }
        """)
  }

  @Test
  fun builder_nestedInnerClass() = runBlocking {
    /*
     * ```
     * interface Outer {
     *   interface Inner {
     *     interface MyComponent {
     *       @Component.Builder
     *       interface Builder {
     *         fun bindFoo(arg0: Enclosing.Foo): Builder
     *         fun build(): MyComponent
     *       }
     *     }
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = listOf("Outer", "Inner", "MyComponent"),
            instantiator =
                builder(
                    setters = listOf(setter("bindFoo", type("Enclosing", "Foo"))),
                    returnType = type("Outer", "Inner", "MyComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object Outer_Inner_MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: Enclosing.Foo): Outer.Inner.MyComponent =
              DaggerOuter_Inner_MyComponent.builder()
          .bindFoo(arg0)
          .build()
        }
        """)
  }

  @Test
  fun builder_rootPackage() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun bindFoo(arg0: Foo): Builder
     *     fun build(): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            packageName = "",
            instantiator =
                builder(
                    setters = listOf(setter("bindFoo", type("Foo", packageName = ""))),
                    returnType = type("MyComponent", packageName = "")))

    assertGenerated(
        target,
        """
        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: Foo): MyComponent = DaggerMyComponent.builder()
          .bindFoo(arg0)
          .build()
        }
        """)
  }

  @Test
  fun builder_variance() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Builder
     *   interface Builder {
     *     fun bindOut(arg0: Producer<out Foo>): Builder
     *     fun bindIn(arg1: Consumer<in Bar>): Builder
     *     fun bindStar(arg2: Box<*>): Builder
     *     fun build(): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            instantiator =
                builder(
                    setters =
                        listOf(
                            setter(
                                "bindOut",
                                type(
                                    "Producer",
                                    typeArguments =
                                        listOf(
                                            argument(
                                                type("Foo"),
                                                variance = Type.TypeArgument.Variance.COVARIANT)))),
                            setter(
                                "bindIn",
                                type(
                                    "Consumer",
                                    typeArguments =
                                        listOf(
                                            argument(
                                                type("Bar"),
                                                variance =
                                                    Type.TypeArgument.Variance.CONTRAVARIANT)))),
                            setter("bindStar", type("Box", typeArguments = listOf(star())))),
                    returnType = type("MyComponent")))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(
            arg0: Producer<out Foo>,
            arg1: Consumer<in Bar>,
            arg2: Box<*>,
          ): MyComponent = DaggerMyComponent.builder()
          .bindOut(arg0)
          .bindIn(arg1)
          .bindStar(arg2)
          .build()
        }
        """)
  }

  @Test
  fun create_empty() = runBlocking {
    /*
     * ```
     * interface MyComponent
     * ```
     */
    val target = target(name = "MyComponent", instantiator = CreateFunction)

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(): MyComponent = DaggerMyComponent.create()
        }
        """)
  }

  @Test
  fun create_nestedInnerClass() = runBlocking {
    /*
     * ```
     * interface Outer {
     *   interface Inner {
     *     interface MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(name = listOf("Outer", "Inner", "MyComponent"), instantiator = CreateFunction)

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object Outer_Inner_MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(): Outer.Inner.MyComponent = DaggerOuter_Inner_MyComponent.create()
        }
        """)
  }

  @Test
  fun create_rootPackage() = runBlocking {
    /*
     * ```
     * interface MyComponent
     * ```
     */
    val target = target(name = "MyComponent", packageName = "", instantiator = CreateFunction)

    assertGenerated(
        target,
        """
        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(): MyComponent = DaggerMyComponent.create()
        }
        """)
  }

  @Test
  fun factory_bindsInstance_typeArguments() = runBlocking {
    /*
     * ```
     * interface DeepComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(arg0: Map<String, List<Int>>): DeepComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "DeepComponent",
            instantiator =
                factory(
                    parameters =
                        listOf(
                            parameter(
                                type =
                                    type(
                                        "Map",
                                        packageName = "kotlin.collections",
                                        typeArguments =
                                            listOf(
                                                type("String", packageName = "kotlin"),
                                                type(
                                                    "List",
                                                    packageName = "kotlin.collections",
                                                    typeArguments =
                                                        listOf(
                                                            type(
                                                                "Int",
                                                                packageName = "kotlin")))))))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides
        import kotlin.Int
        import kotlin.String
        import kotlin.collections.List
        import kotlin.collections.Map

        @Module
        public object DeepComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: Map<String, List<Int>>): DeepComponent =
              DaggerDeepComponent.factory().create(arg0)
        }
        """)
  }

  @Test
  fun factory_bindsInstance_namedRepeated() = runBlocking {
    /*
     * ```
     * interface QualifiedComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(@Named("A") arg0: Foo, @Named("B") arg1: Foo): QualifiedComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "QualifiedComponent",
            instantiator =
                factory(
                    parameters =
                        listOf(
                            parameter(type("Foo"), Qualifier.Named("A")),
                            parameter(type("Foo"), Qualifier.Named("B")))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides
        import javax.inject.Named

        @Module
        public object QualifiedComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(@Named("A") arg0: Foo, @Named("B") arg1: Foo): QualifiedComponent =
              DaggerQualifiedComponent.factory().create(arg0, arg1)
        }
        """)
  }

  @Test
  fun factory_bindsInstance_namedSingular() = runBlocking {
    /*
     * ```
     * interface FactoryComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(@Named("myQualifier") arg0: Foo): FactoryComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "FactoryComponent",
            instantiator = factory(listOf(parameter(type("Foo"), Qualifier.Named("myQualifier")))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides
        import javax.inject.Named

        @Module
        public object FactoryComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(@Named("myQualifier") arg0: Foo): FactoryComponent =
              DaggerFactoryComponent.factory().create(arg0)
        }
        """)
  }

  @Test
  fun factory_bindsInstance_qualifierRepeated() = runBlocking {
    /*
     * ```
     * interface QualifiedComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(@QualifierA arg0: Foo, @QualifierB arg1: Foo): QualifiedComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "QualifiedComponent",
            instantiator =
                factory(
                    parameters =
                        listOf(
                            parameter(
                                type("Foo"), Qualifier.Custom("com.example", listOf("QualifierA"))),
                            parameter(
                                type("Foo"),
                                Qualifier.Custom("com.example", listOf("QualifierB"))))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object QualifiedComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(@QualifierA arg0: Foo, @QualifierB arg1: Foo): QualifiedComponent =
              DaggerQualifiedComponent.factory().create(arg0, arg1)
        }
        """)
  }

  @Test
  fun factory_bindsInstance_qualifierSingular() = runBlocking {
    /*
     * ```
     * interface FactoryComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(@MyQualifier arg0: Foo): FactoryComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "FactoryComponent",
            instantiator =
                factory(
                    listOf(
                        parameter(
                            type("Foo"), Qualifier.Custom("com.example", listOf("MyQualifier"))))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object FactoryComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(@MyQualifier arg0: Foo): FactoryComponent =
              DaggerFactoryComponent.factory().create(arg0)
        }
        """)
  }

  @Test
  fun factory_bindsInstance_unqualified() = runBlocking {
    /*
     * ```
     * interface FactoryComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(arg0: Foo): FactoryComponent
     *   }
     * }
     * ```
     */
    val target =
        target(name = "FactoryComponent", instantiator = factory(listOf(parameter(type("Foo")))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object FactoryComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: Foo): FactoryComponent =
              DaggerFactoryComponent.factory().create(arg0)
        }
        """)
  }

  @Test
  fun factory_complex() = runBlocking {
    /*
     * ```
     * interface ComplexComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(@Named("myQualifier") arg0: List<String>, arg1: MyModule): ComplexComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "ComplexComponent",
            instantiator =
                factory(
                    parameters =
                        listOf(
                            parameter(
                                type =
                                    type(
                                        "List",
                                        packageName = "kotlin.collections",
                                        typeArguments =
                                            listOf(type("String", packageName = "kotlin"))),
                                qualifier = Qualifier.Named("myQualifier")),
                            parameter(type("MyModule")))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides
        import javax.inject.Named
        import kotlin.String
        import kotlin.collections.List

        @Module
        public object ComplexComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(@Named("myQualifier") arg0: List<String>, arg1: MyModule):
              ComplexComponent = DaggerComplexComponent.factory().create(arg0, arg1)
        }
        """)
  }

  @Test
  fun factory_componentDependency() = runBlocking {
    /*
     * ```
     * interface FactoryComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(arg0: BaseComponent): FactoryComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "FactoryComponent",
            instantiator = factory(listOf(parameter(type("BaseComponent")))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object FactoryComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: BaseComponent): FactoryComponent =
              DaggerFactoryComponent.factory().create(arg0)
        }
        """)
  }

  @Test
  fun factory_customNames() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun execute(arg0: Foo): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            instantiator =
                factory(parameters = listOf(parameter(type("Foo"))), factoryName = "execute"))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: Foo): MyComponent = DaggerMyComponent.factory().execute(arg0)
        }
        """)
  }

  @Test
  fun factory_deeplyNestedArgument() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(arg0: Outer.Inner.Deep.Foo): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            instantiator = factory(listOf(parameter(type("Outer", "Inner", "Deep", "Foo")))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: Outer.Inner.Deep.Foo): MyComponent =
              DaggerMyComponent.factory().create(arg0)
        }
        """)
  }

  @Test
  fun factory_empty() = runBlocking {
    /*
     * ```
     * interface FactoryComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(): FactoryComponent
     *   }
     * }
     * ```
     */
    val target = target(name = "FactoryComponent", instantiator = factory())

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object FactoryComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(): FactoryComponent = DaggerFactoryComponent.factory().create()
        }
        """)
  }

  @Test
  fun factory_mixed() = runBlocking {
    /*
     * ```
     * interface FactoryComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(
     *       arg0: DepComponent,
     *       arg1: Simple,
     *       @Named("myQualifier") arg2: Bar,
     *       arg3: List<String>,
     *       arg4: MyModule
     *     ): FactoryComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "FactoryComponent",
            instantiator =
                factory(
                    parameters =
                        listOf(
                            parameter(type("DepComponent")),
                            parameter(type("Simple")),
                            parameter(type("Bar"), Qualifier.Named("myQualifier")),
                            parameter(
                                type(
                                    "List",
                                    packageName = "kotlin.collections",
                                    typeArguments =
                                        listOf(type("String", packageName = "kotlin")))),
                            parameter(type("MyModule")))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides
        import javax.inject.Named
        import kotlin.String
        import kotlin.collections.List

        @Module
        public object FactoryComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(
            arg0: DepComponent,
            arg1: Simple,
            @Named("myQualifier") arg2: Bar,
            arg3: List<String>,
            arg4: MyModule,
          ): FactoryComponent = DaggerFactoryComponent.factory().create(arg0, arg1, arg2, arg3, arg4)
        }
        """)
  }

  @Test
  fun factory_module() = runBlocking {
    /*
     * ```
     * interface FactoryComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(arg0: MyModule): FactoryComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "FactoryComponent", instantiator = factory(listOf(parameter(type("MyModule")))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object FactoryComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: MyModule): FactoryComponent =
              DaggerFactoryComponent.factory().create(arg0)
        }
        """)
  }

  @Test
  fun factory_nestedInnerClass() = runBlocking {
    /*
     * ```
     * interface Outer {
     *   interface Inner {
     *     interface MyComponent {
     *       @Component.Factory
     *       interface Factory {
     *         fun create(arg0: Enclosing.Foo): MyComponent
     *       }
     *     }
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = listOf("Outer", "Inner", "MyComponent"),
            instantiator = factory(listOf(parameter(type("Enclosing", "Foo")))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object Outer_Inner_MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: Enclosing.Foo): Outer.Inner.MyComponent =
              DaggerOuter_Inner_MyComponent.factory().create(arg0)
        }
        """)
  }

  @Test
  fun factory_nestedInnerClass_empty() = runBlocking {
    /*
     * ```
     * interface Outer {
     *   interface Inner {
     *     interface MyComponent {
     *       @Component.Factory
     *       interface Factory {
     *         fun create(): MyComponent
     *       }
     *     }
     *   }
     * }
     * ```
     */
    val target = target(name = listOf("Outer", "Inner", "MyComponent"), instantiator = factory())

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object Outer_Inner_MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(): Outer.Inner.MyComponent =
              DaggerOuter_Inner_MyComponent.factory().create()
        }
        """)
  }

  @Test
  fun factory_rootPackage() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(arg0: Foo): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            packageName = "",
            instantiator = factory(listOf(parameter(type("Foo", packageName = "")))))

    assertGenerated(
        target,
        """
        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(arg0: Foo): MyComponent = DaggerMyComponent.factory().create(arg0)
        }
        """)
  }

  @Test
  fun factory_variance() = runBlocking {
    /*
     * ```
     * interface MyComponent {
     *   @Component.Factory
     *   interface Factory {
     *     fun create(arg0: Producer<out Foo>, arg1: Consumer<in Bar>, arg2: Box<*>): MyComponent
     *   }
     * }
     * ```
     */
    val target =
        target(
            name = "MyComponent",
            instantiator =
                factory(
                    parameters =
                        listOf(
                            parameter(
                                type(
                                    "Producer",
                                    typeArguments =
                                        listOf(
                                            argument(
                                                type("Foo"),
                                                variance = Type.TypeArgument.Variance.COVARIANT)))),
                            parameter(
                                type(
                                    "Consumer",
                                    typeArguments =
                                        listOf(
                                            argument(
                                                type("Bar"),
                                                variance =
                                                    Type.TypeArgument.Variance.CONTRAVARIANT)))),
                            parameter(type("Box", typeArguments = listOf(star()))))))

    assertGenerated(
        target,
        """
        package com.example

        import com.jackbradshaw.backstab.core.annotations.AggregateScope
        import dagger.Module
        import dagger.Provides

        @Module
        public object MyComponent_BackstabModule {
          @Provides
          @AggregateScope
          public fun provideComponent(
            arg0: Producer<out Foo>,
            arg1: Consumer<in Bar>,
            arg2: Box<*>,
          ): MyComponent = DaggerMyComponent.factory().create(arg0, arg1, arg2)
        }
        """)
  }
  /**
   * Gets the subject under test.
   *
   * Must return the same object on each call (within a single test run).
   */
  protected abstract fun subject(): Generator

  /** Creates a [Type] for use in tests. */
  protected fun type(
      vararg name: String,
      packageName: String = "com.example",
      typeArguments: List<Any> = emptyList(),
      isNullable: Boolean = false
  ): Type {
    val mappedTypeArguments =
        typeArguments.map {
          when (it) {
            is Type -> Type.TypeArgument.Specific(it, Type.TypeArgument.Variance.INVARIANT)
            is Type.TypeArgument -> it
            else -> throw IllegalArgumentException("Type argument must be Type or TypeArgument")
          }
        }
    return Type(
        packageName = packageName,
        nameChain = name.toList(),
        typeArguments = mappedTypeArguments,
        isNullable = isNullable)
  }

  /** Creates a [Type.TypeArgument] for use in tests. */
  protected fun argument(
      type: Type,
      variance: Type.TypeArgument.Variance = Type.TypeArgument.Variance.INVARIANT
  ) = Type.TypeArgument.Specific(type, variance)

  /** Creates a star-projected [Type.TypeArgument] for use in tests. */
  protected fun star() = Type.TypeArgument.Star

  /** Creates a [SetterFunction] for use in tests. */
  protected fun setter(name: String, type: Type, qualifier: Qualifier? = null) =
      SetterFunction(name = name, type = type, qualifier = qualifier)

  /** Creates a [FactoryFunction.Parameter] for use in tests. */
  protected fun parameter(type: Type, qualifier: Qualifier? = null) =
      FactoryFunction.Parameter(type = type, qualifier = qualifier)

  /** Creates a [BuilderInterface] for use in tests. */
  protected fun builder(
      setters: List<SetterFunction>,
      returnType: Type,
      buildName: String = "build"
  ) =
      BuilderInterface(
          setters = setters,
          buildFunction = BuildFunction(name = buildName, returnType = returnType))

  /** Creates a [FactoryFunction] for use in tests. */
  protected fun factory(
      parameters: List<FactoryFunction.Parameter> = emptyList(),
      factoryName: String = "create"
  ) = FactoryFunction(name = factoryName, parameters = parameters)

  /** Creates a [BackstabTarget] for use in tests. */
  protected fun target(
      name: List<String>,
      instantiator: BackstabTarget.ComponentInstantiator,
      packageName: String = "com.example"
  ): BackstabTarget {
    val component = BackstabTarget.Component(packageName = packageName, nameChain = name)
    val header =
        SourceFile(
            packageName = component.packageName, fileName = "ComponentFile", extension = "kt")
    return BackstabTarget(header = header, component = component, instantiator = instantiator)
  }

  /** Creates a [BackstabTarget] for use in tests. */
  protected fun target(
      name: String,
      instantiator: BackstabTarget.ComponentInstantiator,
      packageName: String = "com.example"
  ) = target(listOf(name), instantiator, packageName)

  /** Asserts that the generator produces the [expected] content for the given [target]. */
  protected suspend fun assertGenerated(target: BackstabTarget, expected: String) {
    val module = subject().generateModuleFor(target)
    val content = module.sourceFile.contents
    assertThat(content).isEqualTo(expected.trimIndent() + "\n")
  }
}
