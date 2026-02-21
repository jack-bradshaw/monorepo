package com.jackbradshaw.backstab.ksp.tests.binding.internalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Qualifier

interface QualifiedFoo

class QualifiedFooImpl @Inject constructor() : QualifiedFoo

@Qualifier annotation class MyQualifier

@Module
object QualifiedModule {
  val instance = QualifiedFooImpl()

  @Provides @MyQualifier fun provideFoo(): QualifiedFoo = instance
}

@Component
@Backstab
interface QualifiedA {
  @MyQualifier fun foo(): QualifiedFoo

  @Component.Builder
  interface Builder {
    @BindsInstance fun foo(@MyQualifier foo: QualifiedFoo): Builder

    fun build(): QualifiedA
  }
}

@Component(modules = [QualifiedModule::class, QualifiedA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface QualifiedAgg {
  fun target(): QualifiedA

  @Component.Builder
  interface Builder {
    fun build(): QualifiedAgg
  }
}
