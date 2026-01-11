package com.jackbradshaw.backstab.tests

import com.jackbradshaw.backstab.annotations.Backstab
import dagger.Component
import javax.inject.Scope

@Scope
annotation class TestScope

@Component
@TestScope
@Backstab
interface SimpleComponent {
    @Component.Builder
    interface Builder {
        fun build(): SimpleComponent
    }
    // Force rebuild 3
}
