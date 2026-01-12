package com.jackbradshaw.backstab.tests

import com.jackbradshaw.backstab.annotations.Backstab
import dagger.Component
import dagger.BindsInstance
import javax.inject.Qualifier

interface Dep
interface Data

@Qualifier annotation class MyQualifier

// Case 1: Component without builder
@Backstab
@Component
interface ComponentWithoutBuilder

// Case 2: Component with builder and build() only
@Backstab
@Component
interface ComponentWithBuilderBuildOnly {
    @Component.Builder
    interface Builder {
        fun build(): ComponentWithBuilderBuildOnly
    }
}

// Case 3: Component with builder and consuming
@Backstab
@Component(dependencies = [Dep::class])
interface ComponentWithBuilderConsuming {
    @Component.Builder
    interface Builder {
        fun consuming(dep: Dep): Builder
        fun build(): ComponentWithBuilderConsuming
    }
}

// Case 4: Component with builder and binding
@Backstab
@Component
interface ComponentWithBuilderBinding {
    @Component.Builder
    interface Builder {
        @BindsInstance fun binding(data: Data): Builder
        fun build(): ComponentWithBuilderBinding
    }
}

// Case 5: Component with builder and consuming and binding
@Backstab
@Component(dependencies = [Dep::class])
interface ComponentWithBuilderConsumingBinding {
    @Component.Builder
    interface Builder {
        fun consuming(dep: Dep): Builder
        @BindsInstance fun binding(data: Data): Builder
        fun build(): ComponentWithBuilderConsumingBinding
    }
}

// Case 6: Component with qualified binding
@Backstab
@Component
interface ComponentWithQualifiedBinding {
    @Component.Builder
    interface Builder {
        @BindsInstance fun binding(@MyQualifier data: Data): Builder
        fun build(): ComponentWithQualifiedBinding
    }
}
