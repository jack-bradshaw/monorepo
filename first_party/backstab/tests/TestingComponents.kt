package com.jackbradshaw.backstab.tests

import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
import com.jackbradshaw.backstab.annotations.backstab.Backstab
import com.jackbradshaw.backstab.tests.BaseTypes.Bar
import com.jackbradshaw.backstab.tests.BaseTypes.Foo
import com.jackbradshaw.backstab.tests.BaseTypes.MyQualifier
import com.jackbradshaw.backstab.tests.BaseTypes.TestScope
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.annotation.Nullable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Scope

/** Classes, scopes, and qualifiers used across test cases. */
interface BaseTypes {
  class Foo @Inject constructor()

  class Bar @Inject constructor()

  @Scope @Retention(AnnotationRetention.RUNTIME) annotation class TestScope

  @Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class MyQualifier
}

typealias UserId = String

/**
 * Test Case: Topology_Isolated
 *
 * Test setup with the simplest possible Backstab component: a single node with no dependencies.
 *
 * Topology:
 * ```
 * [Stabbed]
 * ```
 */
interface Topology_Isolated_Test {

  // 1. Raw Components (None required for Isolated)

  /** Standalone component. */
  @Component
  @Backstab
  @TestScope
  interface Stabbed {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
      fun build(): Stabbed

      @BindsInstance fun bindFoo(foo: Foo): Builder
    }
  }

  /** The Aggregate wraps the isolated Stabbed. */
  @Component(modules = [Topology_Isolated_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed

    @Component.Builder
    interface Builder {
      fun build(): Aggregate

      @BindsInstance fun bindFoo(foo: Foo): Builder
    }
  }
}

/**
 * Test Case: Topology_Shallow
 *
 * Test setup with a component with a single direct component dependency.
 *
 * Topology:
 * ```
 * [Stabbed] -> [Supporting]
 * ```
 */
interface Topology_Shallow_Test {
  /** The dependency node. */
  @Component
  interface Supporting {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
      fun build(): Supporting

      @BindsInstance fun bindFoo(foo: Foo): Builder
    }
  }

  /** Stabbed component depending on Supporting. */
  @Component(dependencies = [Supporting::class])
  @Backstab
  @TestScope
  interface Stabbed {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
      fun build(): Stabbed

      fun supporting(c: Supporting): Builder
    }
  }

  /** Aggregate wraps Stabbed, which depends on Supporting. */
  @Component(modules = [Topology_Shallow_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed

    @Component.Builder
    interface Builder {
      fun build(): Aggregate

      @BindsInstance fun supporting(c: Supporting): Builder
    }
  }
}

/**
 * Test Case: Topology_Wide
 *
 * Test setup with a component with multiple direct component dependencies.
 *
 * Topology:
 * ```
 * [Stabbed] --> [SupportingA]
 *    |
 *    \-------> [SupportingB]
 * ```
 */
interface Topology_Wide_Test {
  /** Independent supporting nodes. */
  @Component
  interface SupportingA {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
      fun build(): SupportingA

      @BindsInstance fun bindFoo(foo: Foo): Builder
    }
  }

  @Component
  interface SupportingB {
    fun bar(): Bar

    @Component.Builder
    interface Builder {
      fun build(): SupportingB

      @BindsInstance fun bindBar(bar: Bar): Builder
    }
  }

  /** Stabbed component depending on multiple supportings. */
  @Component(dependencies = [SupportingA::class, SupportingB::class])
  @Backstab
  @TestScope
  interface Stabbed {
    fun foo(): Foo

    fun bar(): Bar

    @Component.Builder
    interface Builder {
      fun build(): Stabbed

      fun supportingA(c: SupportingA): Builder

      fun supportingB(c: SupportingB): Builder
    }
  }

  /** Aggregate wraps Stabbed with multiple dependencies. */
  @Component(modules = [Topology_Wide_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed

    @Component.Builder
    interface Builder {
      fun build(): Aggregate

      @BindsInstance fun supportingA(c: SupportingA): Builder

      @BindsInstance fun supportingB(c: SupportingB): Builder
    }
  }
}

/**
 * Test Case: Topology_Deep
 *
 * Test setup with a linear chain of dependencies.
 *
 * Topology:
 * ```
 * [Stabbed] -> [Node] -> [Root]
 * ```
 */
interface Topology_Deep_Test {
  /** Dependency chain components. */
  @Component
  interface DeepRoot {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
      fun build(): DeepRoot

      @BindsInstance fun bindFoo(foo: Foo): Builder
    }
  }

  @Component(dependencies = [DeepRoot::class])
  interface DeepNode {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
      fun build(): DeepNode

      fun deepRoot(deepRoot: DeepRoot): Builder
    }
  }

  /** Apex of the dependency chain. */
  @Component(dependencies = [DeepNode::class])
  @Backstab
  @TestScope
  interface Stabbed {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
      fun build(): Stabbed

      fun deepNode(deepNode: DeepNode): Builder
    }
  }

  /** Aggregate wraps the deep chain. */
  @Component(modules = [Topology_Deep_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed

    @Component.Builder
    interface Builder {
      fun build(): Aggregate

      @BindsInstance fun deepNode(deepNode: DeepNode): Builder
    }
  }
}

/**
 * Test Case: Topology_Diamond
 *
 * Test setup with a diamond graph (Apex -> Left/Right -> Root).
 *
 * Topology:
 * ```
 *           /-> [Left]  -\
 * [Stabbed] -              -> [Root]
 *           \-> [Right] -/
 * ```
 */
interface Topology_Diamond_Test {
  /** Diamond graph infrastructure. */
  @Component
  interface DiamondRoot {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
      fun build(): DiamondRoot

      @BindsInstance fun bindFoo(foo: Foo): Builder
    }
  }

  @Component(dependencies = [DiamondRoot::class])
  interface DiamondLeft {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
      fun build(): DiamondLeft

      fun diamondRoot(diamondRoot: DiamondRoot): Builder
    }
  }

  @Component(dependencies = [DiamondRoot::class])
  interface DiamondRight {
    // Right doesn't expose foo, just exists in the graph
    fun diamondRoot(): DiamondRoot

    @Component.Builder
    interface Builder {
      fun build(): DiamondRight

      fun diamondRoot(diamondRoot: DiamondRoot): Builder
    }
  }

  /** Apex of the diamond graph. */
  @Component(dependencies = [DiamondLeft::class, DiamondRight::class])
  @Backstab
  @TestScope
  interface Stabbed {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
      fun build(): Stabbed

      fun diamondLeft(l: DiamondLeft): Builder

      fun diamondRight(r: DiamondRight): Builder
    }
  }

  /** Aggregate wraps the diamond topology. */
  @Component(modules = [Topology_Diamond_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed

    @Component.Builder
    interface Builder {
      fun build(): Aggregate

      @BindsInstance fun diamondLeft(l: DiamondLeft): Builder

      @BindsInstance fun diamondRight(r: DiamondRight): Builder
    }
  }
}

/**
 * Test Case: Instantiation_Implicit
 *
 * Test setup with a component with no Builder/Factory (uses create()).
 *
 * Topology:
 * ```
 * [Stabbed]
 * ```
 */
interface Instantiation_Implicit_Test {
  // 1. Raw (None)

  // 2. Backstab Stabbed
  @Component
  @Backstab
  @TestScope
  interface Stabbed {
    // No Builder/Factory
  }

  // 3. Aggregate Wrapper
  @Component(modules = [Instantiation_Implicit_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed
    // Implicit create, no builder needed in wrapper either if it has no deps
  }
}

/**
 * Test Case: Instantiation_Builder
 *
 * Test setup with a component with @Component.Builder.
 *
 * Topology:
 * ```
 * [Stabbed]
 * ```
 */
interface Instantiation_Builder_Test {
  // 1. Raw (None)

  // 2. Backstab Stabbed
  @Component
  @Backstab
  @TestScope
  interface Stabbed {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
      fun build(): Stabbed

      @BindsInstance fun bindFoo(foo: Foo): Builder
    }
  }

  // 3. Aggregate Wrapper
  @Component(modules = [Instantiation_Builder_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed

    @Component.Builder
    interface Builder {
      fun build(): Aggregate

      @BindsInstance fun bindFoo(foo: Foo): Builder
    }
  }
}

/**
 * Test Case: Instantiation_Factory
 *
 * Test setup with a component with @Component.Factory.
 *
 * Topology:
 * ```
 * [Stabbed]
 * ```
 */
interface Instantiation_Factory_Test {
  // 1. Raw (None)

  // 2. Backstab Stabbed
  @Component
  @Backstab
  @TestScope
  interface Stabbed {
    fun foo(): Foo

    @Component.Factory
    interface Factory {
      fun create(@BindsInstance foo: Foo): Stabbed
    }
  }

  // 3. Aggregate Wrapper
  @Component(modules = [Instantiation_Factory_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed

    @Component.Factory
    interface Factory {
      fun create(@BindsInstance foo: Foo): Aggregate
    }
  }
}

/** Component used by Bindings_Modules_Test */
@Module
class Bindings_Modules_Test_Module {
  @Provides fun provideFoo(): Foo = Foo()
}

/**
 * Test Case: Bindings_Modules
 *
 * Test setup with standard Dagger module processing.
 *
 * Topology:
 * ```
 * [Stabbed]
 *    |
 *    \---> [Module]
 * ```
 */
interface Bindings_Modules_Test {
  @Component(modules = [Bindings_Modules_Test_Module::class])
  @Backstab
  @TestScope
  interface Stabbed {
    fun foo(): Foo
  }

  @Component(modules = [Bindings_Modules_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed
  }
}

/**
 * Test Case: Bindings_BindsInstance
 *
 * Test setup with @BindsInstance propagation.
 *
 * Topology:
 * ```
 * [Stabbed]
 *    |
 *    \---> (Bound Instance)
 * ```
 */
interface Bindings_BindsInstance_Test {
  @Component
  @Backstab
  @TestScope
  interface Stabbed {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
      fun build(): Stabbed

      @BindsInstance fun bindFoo(foo: Foo): Builder
    }
  }

  @Component(modules = [Bindings_BindsInstance_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed

    @Component.Builder
    interface Builder {
      fun build(): Aggregate

      @BindsInstance fun bindFoo(foo: Foo): Builder
    }
  }
}

/**
 * Test Case: Bindings_Qualifiers
 *
 * Test setup with @Named and custom qualifiers.
 *
 * Topology:
 * ```
 * [Stabbed]
 *    |
 *    +---> (@Named "foo")
 *    |
 *    \---> (@MyQualifier Bar)
 * ```
 */
interface Bindings_Qualifiers_Test {
  @Component
  @Backstab
  @TestScope
  interface Stabbed {
    @Named("foo") fun namedFoo(): Foo

    @MyQualifier fun qualifiedBar(): Bar

    @Component.Builder
    interface Builder {
      fun build(): Stabbed

      @BindsInstance fun bindNamedFoo(@Named("foo") foo: Foo): Builder

      @BindsInstance fun bindQualifiedBar(@MyQualifier bar: Bar): Builder
    }
  }

  @Component(modules = [Bindings_Qualifiers_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed

    @Component.Builder
    interface Builder {
      fun build(): Aggregate

      @BindsInstance fun bindNamedFoo(@Named("foo") foo: Foo): Builder

      @BindsInstance fun bindQualifiedBar(@MyQualifier bar: Bar): Builder
    }
  }
}

/**
 * Test Case: EdgeCase_Generics
 *
 * Test setup with handling of generic types (List<String>).
 *
 * Topology:
 * ```
 * [Stabbed]
 *    |
 *    \---> (List<String>)
 * ```
 */
interface EdgeCase_Generics_Test {
  @Component
  @Backstab
  @TestScope
  interface Stabbed {
    fun strings(): List<String>

    @Component.Builder
    interface Builder {
      fun build(): Stabbed

      @BindsInstance fun bindStrings(strings: List<String>): Builder
    }
  }

  @Component(modules = [EdgeCase_Generics_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed

    @Component.Builder
    interface Builder {
      fun build(): Aggregate

      @BindsInstance fun bindStrings(strings: List<String>): Builder
    }
  }
}

/**
 * Test Case: EdgeCase_TypeAliases
 *
 * Test setup with handling of type aliases (UserId = String).
 *
 * Topology:
 * ```
 * [Stabbed]
 *    |
 *    \---> (UserId)
 * ```
 */
interface EdgeCase_TypeAliases_Test {
  @Component
  @Backstab
  @TestScope
  interface Stabbed {
    fun user(): UserId

    @Component.Builder
    interface Builder {
      fun build(): Stabbed

      @BindsInstance fun bindUser(user: UserId): Builder
    }
  }

  @Component(modules = [EdgeCase_TypeAliases_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed

    @Component.Builder
    interface Builder {
      fun build(): Aggregate

      @BindsInstance fun bindUser(user: UserId): Builder
    }
  }
}

/**
 * Test Case: EdgeCase_Nullability
 *
 * Test setup with handling of @Nullable annotations.
 *
 * Topology:
 * ```
 * [Stabbed]
 *    |
 *    \---> (Foo?)
 * ```
 */
interface EdgeCase_Nullability_Test {
  @Component
  @Backstab
  @TestScope
  interface Stabbed {
    fun nullableFoo(): Foo?

    @Component.Builder
    interface Builder {
      fun build(): Stabbed

      @BindsInstance fun bindNullableFoo(@Nullable foo: Foo?): Builder
    }
  }

  @Component(modules = [EdgeCase_Nullability_Test_Stabbed_AggregateModule::class])
  @AggregateScope
  interface Aggregate {
    fun target(): Stabbed

    @Component.Builder
    interface Builder {
      fun build(): Aggregate

      @BindsInstance fun bindNullableFoo(@Nullable foo: Foo?): Builder
    }
  }
}
