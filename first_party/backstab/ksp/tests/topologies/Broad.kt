package com.jackbradshaw.backstab.ksp.tests.topologies

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

// --- Foo Classes ---

class BroadFooA
@Inject
constructor(
    val b1: BroadFooB1,
    val b2: BroadFooB2,
    val b3: BroadFooB3,
    val b4: BroadFooB4,
    val b5: BroadFooB5
)

class BroadFooB1
@Inject
constructor(
    val c1: BroadFooC1,
    val c2: BroadFooC2,
    val c3: BroadFooC3,
    val c4: BroadFooC4,
    val c5: BroadFooC5
)

class BroadFooB2
@Inject
constructor(
    val c6: BroadFooC6,
    val c7: BroadFooC7,
    val c8: BroadFooC8,
    val c9: BroadFooC9,
    val c10: BroadFooC10
)

class BroadFooB3
@Inject
constructor(
    val c11: BroadFooC11,
    val c12: BroadFooC12,
    val c13: BroadFooC13,
    val c14: BroadFooC14,
    val c15: BroadFooC15
)

class BroadFooB4
@Inject
constructor(
    val c16: BroadFooC16,
    val c17: BroadFooC17,
    val c18: BroadFooC18,
    val c19: BroadFooC19,
    val c20: BroadFooC20
)

class BroadFooB5
@Inject
constructor(
    val c21: BroadFooC21,
    val c22: BroadFooC22,
    val c23: BroadFooC23,
    val c24: BroadFooC24,
    val c25: BroadFooC25
)

class BroadFooC1 @Inject constructor()

class BroadFooC2 @Inject constructor()

class BroadFooC3 @Inject constructor()

class BroadFooC4 @Inject constructor()

class BroadFooC5 @Inject constructor()

class BroadFooC6 @Inject constructor()

class BroadFooC7 @Inject constructor()

class BroadFooC8 @Inject constructor()

class BroadFooC9 @Inject constructor()

class BroadFooC10 @Inject constructor()

class BroadFooC11 @Inject constructor()

class BroadFooC12 @Inject constructor()

class BroadFooC13 @Inject constructor()

class BroadFooC14 @Inject constructor()

class BroadFooC15 @Inject constructor()

class BroadFooC16 @Inject constructor()

class BroadFooC17 @Inject constructor()

class BroadFooC18 @Inject constructor()

class BroadFooC19 @Inject constructor()

class BroadFooC20 @Inject constructor()

class BroadFooC21 @Inject constructor()

class BroadFooC22 @Inject constructor()

class BroadFooC23 @Inject constructor()

class BroadFooC24 @Inject constructor()

class BroadFooC25 @Inject constructor()

// --- Modules with Static Instances ---

@Module
object BroadC1Module {
  val instance = BroadFooC1()

  @Provides fun provide() = instance
}

@Module
object BroadC2Module {
  val instance = BroadFooC2()

  @Provides fun provide() = instance
}

@Module
object BroadC3Module {
  val instance = BroadFooC3()

  @Provides fun provide() = instance
}

@Module
object BroadC4Module {
  val instance = BroadFooC4()

  @Provides fun provide() = instance
}

@Module
object BroadC5Module {
  val instance = BroadFooC5()

  @Provides fun provide() = instance
}

@Module
object BroadC6Module {
  val instance = BroadFooC6()

  @Provides fun provide() = instance
}

@Module
object BroadC7Module {
  val instance = BroadFooC7()

  @Provides fun provide() = instance
}

@Module
object BroadC8Module {
  val instance = BroadFooC8()

  @Provides fun provide() = instance
}

@Module
object BroadC9Module {
  val instance = BroadFooC9()

  @Provides fun provide() = instance
}

@Module
object BroadC10Module {
  val instance = BroadFooC10()

  @Provides fun provide() = instance
}

@Module
object BroadC11Module {
  val instance = BroadFooC11()

  @Provides fun provide() = instance
}

@Module
object BroadC12Module {
  val instance = BroadFooC12()

  @Provides fun provide() = instance
}

@Module
object BroadC13Module {
  val instance = BroadFooC13()

  @Provides fun provide() = instance
}

@Module
object BroadC14Module {
  val instance = BroadFooC14()

  @Provides fun provide() = instance
}

@Module
object BroadC15Module {
  val instance = BroadFooC15()

  @Provides fun provide() = instance
}

@Module
object BroadC16Module {
  val instance = BroadFooC16()

  @Provides fun provide() = instance
}

@Module
object BroadC17Module {
  val instance = BroadFooC17()

  @Provides fun provide() = instance
}

@Module
object BroadC18Module {
  val instance = BroadFooC18()

  @Provides fun provide() = instance
}

@Module
object BroadC19Module {
  val instance = BroadFooC19()

  @Provides fun provide() = instance
}

@Module
object BroadC20Module {
  val instance = BroadFooC20()

  @Provides fun provide() = instance
}

@Module
object BroadC21Module {
  val instance = BroadFooC21()

  @Provides fun provide() = instance
}

@Module
object BroadC22Module {
  val instance = BroadFooC22()

  @Provides fun provide() = instance
}

@Module
object BroadC23Module {
  val instance = BroadFooC23()

  @Provides fun provide() = instance
}

@Module
object BroadC24Module {
  val instance = BroadFooC24()

  @Provides fun provide() = instance
}

@Module
object BroadC25Module {
  val instance = BroadFooC25()

  @Provides fun provide() = instance
}

@Module
object BroadB1Module {
  val instance =
      BroadFooB1(
          BroadC1Module.instance,
          BroadC2Module.instance,
          BroadC3Module.instance,
          BroadC4Module.instance,
          BroadC5Module.instance)

  @Provides fun provide() = instance
}

@Module
object BroadB2Module {
  val instance =
      BroadFooB2(
          BroadC6Module.instance,
          BroadC7Module.instance,
          BroadC8Module.instance,
          BroadC9Module.instance,
          BroadC10Module.instance)

  @Provides fun provide() = instance
}

@Module
object BroadB3Module {
  val instance =
      BroadFooB3(
          BroadC11Module.instance,
          BroadC12Module.instance,
          BroadC13Module.instance,
          BroadC14Module.instance,
          BroadC15Module.instance)

  @Provides fun provide() = instance
}

@Module
object BroadB4Module {
  val instance =
      BroadFooB4(
          BroadC16Module.instance,
          BroadC17Module.instance,
          BroadC18Module.instance,
          BroadC19Module.instance,
          BroadC20Module.instance)

  @Provides fun provide() = instance
}

@Module
object BroadB5Module {
  val instance =
      BroadFooB5(
          BroadC21Module.instance,
          BroadC22Module.instance,
          BroadC23Module.instance,
          BroadC24Module.instance,
          BroadC25Module.instance)

  @Provides fun provide() = instance
}

@Module
object BroadAModule {
  val instance =
      BroadFooA(
          BroadB1Module.instance,
          BroadB2Module.instance,
          BroadB3Module.instance,
          BroadB4Module.instance,
          BroadB5Module.instance)

  @Provides fun provide() = instance
}

// --- Components ---

@Component(modules = [BroadC1Module::class])
@Backstab
interface BroadC1 {
  fun fooC1(): BroadFooC1

  @Component.Builder
  interface Builder {
    fun build(): BroadC1
  }
}

@Component(modules = [BroadC2Module::class])
@Backstab
interface BroadC2 {
  fun fooC2(): BroadFooC2

  @Component.Builder
  interface Builder {
    fun build(): BroadC2
  }
}

@Component(modules = [BroadC3Module::class])
@Backstab
interface BroadC3 {
  fun fooC3(): BroadFooC3

  @Component.Builder
  interface Builder {
    fun build(): BroadC3
  }
}

@Component(modules = [BroadC4Module::class])
@Backstab
interface BroadC4 {
  fun fooC4(): BroadFooC4

  @Component.Builder
  interface Builder {
    fun build(): BroadC4
  }
}

@Component(modules = [BroadC5Module::class])
@Backstab
interface BroadC5 {
  fun fooC5(): BroadFooC5

  @Component.Builder
  interface Builder {
    fun build(): BroadC5
  }
}

@Component(modules = [BroadC6Module::class])
@Backstab
interface BroadC6 {
  fun fooC6(): BroadFooC6

  @Component.Builder
  interface Builder {
    fun build(): BroadC6
  }
}

@Component(modules = [BroadC7Module::class])
@Backstab
interface BroadC7 {
  fun fooC7(): BroadFooC7

  @Component.Builder
  interface Builder {
    fun build(): BroadC7
  }
}

@Component(modules = [BroadC8Module::class])
@Backstab
interface BroadC8 {
  fun fooC8(): BroadFooC8

  @Component.Builder
  interface Builder {
    fun build(): BroadC8
  }
}

@Component(modules = [BroadC9Module::class])
@Backstab
interface BroadC9 {
  fun fooC9(): BroadFooC9

  @Component.Builder
  interface Builder {
    fun build(): BroadC9
  }
}

@Component(modules = [BroadC10Module::class])
@Backstab
interface BroadC10 {
  fun fooC10(): BroadFooC10

  @Component.Builder
  interface Builder {
    fun build(): BroadC10
  }
}

@Component(modules = [BroadC11Module::class])
@Backstab
interface BroadC11 {
  fun fooC11(): BroadFooC11

  @Component.Builder
  interface Builder {
    fun build(): BroadC11
  }
}

@Component(modules = [BroadC12Module::class])
@Backstab
interface BroadC12 {
  fun fooC12(): BroadFooC12

  @Component.Builder
  interface Builder {
    fun build(): BroadC12
  }
}

@Component(modules = [BroadC13Module::class])
@Backstab
interface BroadC13 {
  fun fooC13(): BroadFooC13

  @Component.Builder
  interface Builder {
    fun build(): BroadC13
  }
}

@Component(modules = [BroadC14Module::class])
@Backstab
interface BroadC14 {
  fun fooC14(): BroadFooC14

  @Component.Builder
  interface Builder {
    fun build(): BroadC14
  }
}

@Component(modules = [BroadC15Module::class])
@Backstab
interface BroadC15 {
  fun fooC15(): BroadFooC15

  @Component.Builder
  interface Builder {
    fun build(): BroadC15
  }
}

@Component(modules = [BroadC16Module::class])
@Backstab
interface BroadC16 {
  fun fooC16(): BroadFooC16

  @Component.Builder
  interface Builder {
    fun build(): BroadC16
  }
}

@Component(modules = [BroadC17Module::class])
@Backstab
interface BroadC17 {
  fun fooC17(): BroadFooC17

  @Component.Builder
  interface Builder {
    fun build(): BroadC17
  }
}

@Component(modules = [BroadC18Module::class])
@Backstab
interface BroadC18 {
  fun fooC18(): BroadFooC18

  @Component.Builder
  interface Builder {
    fun build(): BroadC18
  }
}

@Component(modules = [BroadC19Module::class])
@Backstab
interface BroadC19 {
  fun fooC19(): BroadFooC19

  @Component.Builder
  interface Builder {
    fun build(): BroadC19
  }
}

@Component(modules = [BroadC20Module::class])
@Backstab
interface BroadC20 {
  fun fooC20(): BroadFooC20

  @Component.Builder
  interface Builder {
    fun build(): BroadC20
  }
}

@Component(modules = [BroadC21Module::class])
@Backstab
interface BroadC21 {
  fun fooC21(): BroadFooC21

  @Component.Builder
  interface Builder {
    fun build(): BroadC21
  }
}

@Component(modules = [BroadC22Module::class])
@Backstab
interface BroadC22 {
  fun fooC22(): BroadFooC22

  @Component.Builder
  interface Builder {
    fun build(): BroadC22
  }
}

@Component(modules = [BroadC23Module::class])
@Backstab
interface BroadC23 {
  fun fooC23(): BroadFooC23

  @Component.Builder
  interface Builder {
    fun build(): BroadC23
  }
}

@Component(modules = [BroadC24Module::class])
@Backstab
interface BroadC24 {
  fun fooC24(): BroadFooC24

  @Component.Builder
  interface Builder {
    fun build(): BroadC24
  }
}

@Component(modules = [BroadC25Module::class])
@Backstab
interface BroadC25 {
  fun fooC25(): BroadFooC25

  @Component.Builder
  interface Builder {
    fun build(): BroadC25
  }
}

@Component(
    dependencies = [BroadC1::class, BroadC2::class, BroadC3::class, BroadC4::class, BroadC5::class],
    modules = [BroadB1Module::class])
@Backstab
interface BroadB1 {
  fun fooB1(): BroadFooB1

  fun fooC1(): BroadFooC1

  fun fooC2(): BroadFooC2

  fun fooC3(): BroadFooC3

  fun fooC4(): BroadFooC4

  fun fooC5(): BroadFooC5

  @Component.Builder
  interface Builder {
    fun build(): BroadB1

    fun c1(c: BroadC1): Builder

    fun c2(c: BroadC2): Builder

    fun c3(c: BroadC3): Builder

    fun c4(c: BroadC4): Builder

    fun c5(c: BroadC5): Builder
  }
}

@Component(
    dependencies =
        [BroadC6::class, BroadC7::class, BroadC8::class, BroadC9::class, BroadC10::class],
    modules = [BroadB2Module::class])
@Backstab
interface BroadB2 {
  fun fooB2(): BroadFooB2

  fun fooC6(): BroadFooC6

  fun fooC7(): BroadFooC7

  fun fooC8(): BroadFooC8

  fun fooC9(): BroadFooC9

  fun fooC10(): BroadFooC10

  @Component.Builder
  interface Builder {
    fun build(): BroadB2

    fun c6(c: BroadC6): Builder

    fun c7(c: BroadC7): Builder

    fun c8(c: BroadC8): Builder

    fun c9(c: BroadC9): Builder

    fun c10(c: BroadC10): Builder
  }
}

@Component(
    dependencies =
        [BroadC11::class, BroadC12::class, BroadC13::class, BroadC14::class, BroadC15::class],
    modules = [BroadB3Module::class])
@Backstab
interface BroadB3 {
  fun fooB3(): BroadFooB3

  fun fooC11(): BroadFooC11

  fun fooC12(): BroadFooC12

  fun fooC13(): BroadFooC13

  fun fooC14(): BroadFooC14

  fun fooC15(): BroadFooC15

  @Component.Builder
  interface Builder {
    fun build(): BroadB3

    fun c11(c: BroadC11): Builder

    fun c12(c: BroadC12): Builder

    fun c13(c: BroadC13): Builder

    fun c14(c: BroadC14): Builder

    fun c15(c: BroadC15): Builder
  }
}

@Component(
    dependencies =
        [BroadC16::class, BroadC17::class, BroadC18::class, BroadC19::class, BroadC20::class],
    modules = [BroadB4Module::class])
@Backstab
interface BroadB4 {
  fun fooB4(): BroadFooB4

  fun fooC16(): BroadFooC16

  fun fooC17(): BroadFooC17

  fun fooC18(): BroadFooC18

  fun fooC19(): BroadFooC19

  fun fooC20(): BroadFooC20

  @Component.Builder
  interface Builder {
    fun build(): BroadB4

    fun c16(c: BroadC16): Builder

    fun c17(c: BroadC17): Builder

    fun c18(c: BroadC18): Builder

    fun c19(c: BroadC19): Builder

    fun c20(c: BroadC20): Builder
  }
}

@Component(
    dependencies =
        [BroadC21::class, BroadC22::class, BroadC23::class, BroadC24::class, BroadC25::class],
    modules = [BroadB5Module::class])
@Backstab
interface BroadB5 {
  fun fooB5(): BroadFooB5

  fun fooC21(): BroadFooC21

  fun fooC22(): BroadFooC22

  fun fooC23(): BroadFooC23

  fun fooC24(): BroadFooC24

  fun fooC25(): BroadFooC25

  @Component.Builder
  interface Builder {
    fun build(): BroadB5

    fun c21(c: BroadC21): Builder

    fun c22(c: BroadC22): Builder

    fun c23(c: BroadC23): Builder

    fun c24(c: BroadC24): Builder

    fun c25(c: BroadC25): Builder
  }
}

@Component(
    dependencies = [BroadB1::class, BroadB2::class, BroadB3::class, BroadB4::class, BroadB5::class],
    modules = [BroadAModule::class])
@Backstab
interface BroadA {
  fun fooA(): BroadFooA

  fun fooB1(): BroadFooB1

  fun fooB2(): BroadFooB2

  fun fooB3(): BroadFooB3

  fun fooB4(): BroadFooB4

  fun fooB5(): BroadFooB5

  fun fooC1(): BroadFooC1

  fun fooC2(): BroadFooC2

  fun fooC3(): BroadFooC3

  fun fooC4(): BroadFooC4

  fun fooC5(): BroadFooC5

  fun fooC6(): BroadFooC6

  fun fooC7(): BroadFooC7

  fun fooC8(): BroadFooC8

  fun fooC9(): BroadFooC9

  fun fooC10(): BroadFooC10

  fun fooC11(): BroadFooC11

  fun fooC12(): BroadFooC12

  fun fooC13(): BroadFooC13

  fun fooC14(): BroadFooC14

  fun fooC15(): BroadFooC15

  fun fooC16(): BroadFooC16

  fun fooC17(): BroadFooC17

  fun fooC18(): BroadFooC18

  fun fooC19(): BroadFooC19

  fun fooC20(): BroadFooC20

  fun fooC21(): BroadFooC21

  fun fooC22(): BroadFooC22

  fun fooC23(): BroadFooC23

  fun fooC24(): BroadFooC24

  fun fooC25(): BroadFooC25

  @Component.Builder
  interface Builder {
    fun build(): BroadA

    fun b1(b: BroadB1): Builder

    fun b2(b: BroadB2): Builder

    fun b3(b: BroadB3): Builder

    fun b4(b: BroadB4): Builder

    fun b5(b: BroadB5): Builder
  }
}

@Component(
    modules =
        [
            BroadA_BackstabModule::class,
            BroadB1_BackstabModule::class,
            BroadB2_BackstabModule::class,
            BroadB3_BackstabModule::class,
            BroadB4_BackstabModule::class,
            BroadB5_BackstabModule::class,
            BroadC1_BackstabModule::class,
            BroadC2_BackstabModule::class,
            BroadC3_BackstabModule::class,
            BroadC4_BackstabModule::class,
            BroadC5_BackstabModule::class,
            BroadC6_BackstabModule::class,
            BroadC7_BackstabModule::class,
            BroadC8_BackstabModule::class,
            BroadC9_BackstabModule::class,
            BroadC10_BackstabModule::class,
            BroadC11_BackstabModule::class,
            BroadC12_BackstabModule::class,
            BroadC13_BackstabModule::class,
            BroadC14_BackstabModule::class,
            BroadC15_BackstabModule::class,
            BroadC16_BackstabModule::class,
            BroadC17_BackstabModule::class,
            BroadC18_BackstabModule::class,
            BroadC19_BackstabModule::class,
            BroadC20_BackstabModule::class,
            BroadC21_BackstabModule::class,
            BroadC22_BackstabModule::class,
            BroadC23_BackstabModule::class,
            BroadC24_BackstabModule::class,
            BroadC25_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface BroadAgg {
  fun target(): BroadA

  @Component.Builder
  interface Builder {
    fun build(): BroadAgg
  }
}
