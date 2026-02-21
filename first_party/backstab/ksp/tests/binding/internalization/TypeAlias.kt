package com.jackbradshaw.backstab.ksp.tests.binding.internalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

interface UserId

class UserIdImpl @Inject constructor() : UserId

@Module
object TypeAliasModule {
  val instance = UserIdImpl()

  @Provides fun provideUserId(): UserId = instance
}

@Component
@Backstab
interface TypeAliasA {
  fun userId(): UserId

  @Component.Builder
  interface Builder {
    @BindsInstance fun userId(userId: UserId): Builder

    fun build(): TypeAliasA
  }
}

@Component(modules = [TypeAliasModule::class, TypeAliasA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TypeAliasAgg {
  fun target(): TypeAliasA

  @Component.Builder
  interface Builder {
    fun build(): TypeAliasAgg
  }
}
