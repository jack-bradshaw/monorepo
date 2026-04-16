package com.jackbradshaw.backstab.ksp.tests.topologies

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class WideFooB1 @Inject constructor()

class WideFooB2 @Inject constructor()

class WideFooB3 @Inject constructor()

class WideFooB4 @Inject constructor()

class WideFooB5 @Inject constructor()

class WideFooA
@Inject
constructor(
    val b1: WideFooB1,
    val b2: WideFooB2,
    val b3: WideFooB3,
    val b4: WideFooB4,
    val b5: WideFooB5
)

@Module
object WideB1Module {
  val instance = WideFooB1()

  @Provides fun provide() = instance
}

@Module
object WideB2Module {
  val instance = WideFooB2()

  @Provides fun provide() = instance
}

@Module
object WideB3Module {
  val instance = WideFooB3()

  @Provides fun provide() = instance
}

@Module
object WideB4Module {
  val instance = WideFooB4()

  @Provides fun provide() = instance
}

@Module
object WideB5Module {
  val instance = WideFooB5()

  @Provides fun provide() = instance
}

@Module
object WideAModule {
  val instance =
      WideFooA(
          WideB1Module.instance,
          WideB2Module.instance,
          WideB3Module.instance,
          WideB4Module.instance,
          WideB5Module.instance)

  @Provides fun provide() = instance
}

@Component(modules = [WideAModule::class])
@Backstab
interface WideA {
  fun fooA(): WideFooA

  @Component.Builder
  interface Builder {
    fun build(): WideA
  }
}

@Component(modules = [WideB1Module::class])
@Backstab
interface WideB1 {
  fun fooB1(): WideFooB1

  @Component.Builder
  interface Builder {
    fun build(): WideB1
  }
}

@Component(modules = [WideB2Module::class])
@Backstab
interface WideB2 {
  fun fooB2(): WideFooB2

  @Component.Builder
  interface Builder {
    fun build(): WideB2
  }
}

@Component(modules = [WideB3Module::class])
@Backstab
interface WideB3 {
  fun fooB3(): WideFooB3

  @Component.Builder
  interface Builder {
    fun build(): WideB3
  }
}

@Component(modules = [WideB4Module::class])
@Backstab
interface WideB4 {
  fun fooB4(): WideFooB4

  @Component.Builder
  interface Builder {
    fun build(): WideB4
  }
}

@Component(modules = [WideB5Module::class])
@Backstab
interface WideB5 {
  fun fooB5(): WideFooB5

  @Component.Builder
  interface Builder {
    fun build(): WideB5
  }
}

@Component(
    modules =
        [
            WideA_BackstabModule::class,
            WideB1_BackstabModule::class,
            WideB2_BackstabModule::class,
            WideB3_BackstabModule::class,
            WideB4_BackstabModule::class,
            WideB5_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface WideAgg {
  fun targetA(): WideA

  fun targetB1(): WideB1

  fun targetB2(): WideB2

  fun targetB3(): WideB3

  fun targetB4(): WideB4

  fun targetB5(): WideB5

  @Component.Builder
  interface Builder {
    fun build(): WideAgg
  }
}
