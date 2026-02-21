package com.jackbradshaw.backstab.ksp.tests.binding.externalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Qualifier

class QualifiedFoo @Inject constructor()

@Qualifier annotation class MyQualifier

@Module
object QualifiedModule {
  val instance = QualifiedFoo()

  @Provides @MyQualifier fun provideFoo(): QualifiedFoo = instance
}

@Component(modules = [QualifiedModule::class])
@Backstab
interface QualifiedA {
  @MyQualifier fun foo(): QualifiedFoo

  @Component.Builder
  interface Builder {
    fun build(): QualifiedA
  }
}

@Component(modules = [QualifiedA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface QualifiedAgg {
  fun target(): QualifiedA

  @Component.Builder
  interface Builder {
    fun build(): QualifiedAgg
  }
}
