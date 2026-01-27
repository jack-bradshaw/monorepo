package com.jackbradshaw.backstab.tests

import com.jackbradshaw.backstab.annotations.meta.MetaScope
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Component(modules = [LeafComponentAutoModule::class])
@MetaScope
interface LeafMetaComponent {
    fun leafComponent(): LeafComponent
}

@Component(modules = [NoBuilderComponentAutoModule::class])
@MetaScope
interface NoBuilderMetaComponent {
    fun noBuilderComponent(): NoBuilderComponent
}

@Component(modules = [SingleBindingComponentAutoModule::class])
@MetaScope
interface SingleBindingMetaComponent {
    fun singleBindingComponent(): SingleBindingComponent

    @Component.Builder
    interface Builder {
        fun foo(@BindsInstance foo: Foo): Builder
        fun build(): SingleBindingMetaComponent
    }
}

@Component(modules = [MultipleBindingsComponentAutoModule::class])
@MetaScope
interface MultipleBindingsMetaComponent {
    fun multipleBindingsComponent(): MultipleBindingsComponent

    @Component.Builder
    interface Builder {
        fun foo(@BindsInstance foo: Foo): Builder
        fun bar(@BindsInstance bar: Bar): Builder
        fun build(): MultipleBindingsMetaComponent
    }
}



@Component(modules = [QualifiedBindingComponentAutoModule::class])
@MetaScope
interface QualifiedBindingMetaComponent {
    fun qualifiedBindingComponent(): QualifiedBindingComponent

    @Component.Builder
    interface Builder {
        fun foo(@BindsInstance @Named("myQualifier") foo: Foo): Builder
        fun build(): QualifiedBindingMetaComponent
    }
}

@Component(modules = [ConsumerComponentAutoModule::class])
@MetaScope
interface ConsumerMetaComponent {
    fun consumerComponent(): ConsumerComponent

    @Component.Builder
    interface Builder {
        fun providerComponent(@BindsInstance provider: ProviderComponent): Builder
        fun build(): ConsumerMetaComponent
    }
}

@Component(modules = [ConsumerWithBindingComponentAutoModule::class])
@MetaScope
interface ConsumerWithBindingMetaComponent {
    fun consumerWithBindingComponent(): ConsumerWithBindingComponent

    @Component.Builder
    interface Builder {
        fun providerComponent(@BindsInstance provider: ProviderComponent): Builder
        fun bar(@BindsInstance bar: Bar): Builder
        fun build(): ConsumerWithBindingMetaComponent
    }
}

@Component(modules = [MultipleConsumersComponentAutoModule::class])
@MetaScope
interface MultipleConsumersMetaComponent {
    fun multipleConsumersComponent(): MultipleConsumersComponent

    @Component.Builder
    interface Builder {
        fun providerComponent(@BindsInstance p: ProviderComponent): Builder
        fun leafComponent(@BindsInstance l: LeafComponent): Builder
        fun build(): MultipleConsumersMetaComponent
    }
}

@Component(modules = [DeepTopComponentAutoModule::class])
@MetaScope
interface DeepTopMetaComponent {
    fun deepTopComponent(): DeepTopComponent

    @Component.Builder
    interface Builder {
        fun deepMiddleComponent(@BindsInstance m: DeepMiddleComponent): Builder
        fun build(): DeepTopMetaComponent
    }
}

@Component(modules = [DiamondSourceComponentAutoModule::class])
@MetaScope
interface DiamondSourceMetaComponent {
    fun diamondSourceComponent(): DiamondSourceComponent

    @Component.Builder
    interface Builder {
        fun diamondLeftComponent(@BindsInstance l: DiamondLeftComponent): Builder
        fun diamondRightComponent(@BindsInstance r: DiamondRightComponent): Builder
        fun build(): DiamondSourceMetaComponent
    }
}
