// force rebuild
package com.jackbradshaw.backstab.tests

import com.jackbradshaw.backstab.annotations.backstab.Backstab
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Scope

/** A simple class for use in components. */
class Foo

/** A simple class for use in components. */
class Bar

/** A scope for all test components. */
@Scope
annotation class TestScope

@Component
@Backstab
// Removed @TestScope to allow dependency
interface LeafComponent {
    @Component.Builder
    interface Builder {
        fun build(): LeafComponent
    }
}

@Component
@TestScope
@Backstab
interface NoBuilderComponent

@Component
@TestScope
@Backstab
interface SingleBindingComponent {
    @Component.Builder
    interface Builder {
        fun foo(@BindsInstance f: Foo): Builder
        fun build(): SingleBindingComponent
    }
}

@Component
@TestScope
@Backstab
interface MultipleBindingsComponent {
    @Component.Builder
    interface Builder {
        fun foo(@BindsInstance f: Foo): Builder
        fun bar(@BindsInstance b: Bar): Builder
        fun build(): MultipleBindingsComponent
    }
}

@Component
@TestScope
@Backstab
interface QualifiedBindingComponent {
    @Component.Builder
    interface Builder {
        fun foo(@BindsInstance @Named("myQualifier") f: Foo): Builder
        fun build(): QualifiedBindingComponent
    }
}

@Component
interface ProviderComponent {
    fun foo(): Foo

    @Component.Builder
    interface Builder {
        fun foo(@BindsInstance foo: Foo): Builder
        fun build(): ProviderComponent
    }
}

// Tests Component Dependency
@Component(dependencies = [ProviderComponent::class])
@TestScope
@Backstab
interface ConsumerComponent {
    @Component.Builder
    interface Builder {
        fun providerComponent(component: ProviderComponent): Builder
        fun build(): ConsumerComponent
    }
}

// Tests Component Dependency + Manual Binding
@Component(dependencies = [ProviderComponent::class])
@TestScope
@Backstab
interface ConsumerWithBindingComponent {
    @Component.Builder
    interface Builder {
        fun providerComponent(component: ProviderComponent): Builder
        fun bar(@BindsInstance bar: Bar): Builder
        fun build(): ConsumerWithBindingComponent
    }
}

// Tests Multiple Component Dependencies
@Component(dependencies = [ProviderComponent::class, LeafComponent::class])
@TestScope
@Backstab
interface MultipleConsumersComponent {
    @Component.Builder
    interface Builder {
        fun providerComponent(p: ProviderComponent): Builder
        fun leafComponent(l: LeafComponent): Builder
        fun build(): MultipleConsumersComponent
    }
}

// Tests Deep Graph (Bottom -> Middle -> Top)
// Dependencies MUST be unscoped if dependent is disjoint scope, OR parent scope.
// For simplicity, make dependencies unscoped.
@Component
interface DeepBottomComponent {
    fun foo(): Foo
    @Component.Builder interface Builder { fun foo(@BindsInstance f: Foo): Builder; fun build(): DeepBottomComponent }
}

@Component(dependencies = [DeepBottomComponent::class])
interface DeepMiddleComponent {
    fun foo(): Foo
    @Component.Builder interface Builder { fun deepBottomComponent(b: DeepBottomComponent): Builder; fun build(): DeepMiddleComponent }
}

@Component(dependencies = [DeepMiddleComponent::class])
@TestScope
@Backstab
interface DeepTopComponent {
    @Component.Builder
    interface Builder {
        fun deepMiddleComponent(m: DeepMiddleComponent): Builder
        fun build(): DeepTopComponent
    }
}

// Tests Diamond Graph (Shared -> Left/Right -> Source)
@Component
interface DiamondSharedComponent {
    fun foo(): Foo
    @Component.Builder interface Builder { fun foo(@BindsInstance f: Foo): Builder; fun build(): DiamondSharedComponent }
}

@Component(dependencies = [DiamondSharedComponent::class])
interface DiamondLeftComponent {
    fun foo(): Foo // Re-exposes Foo from Shared
    fun shared(): DiamondSharedComponent
    @Component.Builder interface Builder { fun diamondSharedComponent(s: DiamondSharedComponent): Builder; fun build(): DiamondLeftComponent }
}

@Component(dependencies = [DiamondSharedComponent::class])
interface DiamondRightComponent {
    fun foo(): Foo // Re-exposes Foo from Shared
    fun shared(): DiamondSharedComponent
    @Component.Builder interface Builder { fun diamondSharedComponent(s: DiamondSharedComponent): Builder; fun build(): DiamondRightComponent }
}

@Component(dependencies = [DiamondLeftComponent::class, DiamondRightComponent::class])
@TestScope
@Backstab
interface DiamondSourceComponent {
    @Component.Builder
    interface Builder {
        fun diamondLeftComponent(l: DiamondLeftComponent): Builder
        fun diamondRightComponent(r: DiamondRightComponent): Builder
        fun build(): DiamondSourceComponent
    }
}



