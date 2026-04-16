package com.jackbradshaw.backstab.ksp.tests.binding.externalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class UserId @Inject constructor(val value: String)

@Module
object TypeAliasModule {
  val instance = UserId("user-123")

  @Provides fun provideUserId(): UserId = instance
}

@Component(modules = [TypeAliasModule::class])
@Backstab
interface TypeAliasA {
  fun userId(): UserId

  @Component.Builder
  interface Builder {
    fun build(): TypeAliasA
  }
}

@Component(modules = [TypeAliasA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TypeAliasAgg {
  fun target(): TypeAliasA

  @Component.Builder
  interface Builder {
    fun build(): TypeAliasAgg
  }
}
