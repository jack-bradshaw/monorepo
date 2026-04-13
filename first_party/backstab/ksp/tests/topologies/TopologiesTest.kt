package com.jackbradshaw.backstab.ksp.tests.topologies

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Verifies that Backstab can correctly resolve and connect complex dependency topologies.
 *
 * Verification is performed by building the aggregate component and checking that an object
 * constructed deep within the graph matches the object with the same qualified type pulled from the
 * aggregate. This approach ensures intermediate components properly propagate the objects from the
 * leaves to the aggregate. If the components compile and if the retrieved instance is correct then
 * the graph was properly compiled and connected. A representive series of toploogies used to ensure
 * Backstab-generated code is fully compatible with standard Dagger, including toplogies where all
 * nodes are transitively connected and topologies where nodes form distinct and disconnected
 * subgraphs (note, in Backstab they share bindings regardless).
 */
@RunWith(JUnit4::class)
class TopologiesTest {

  // @formatter:off
  /** Topology: A */
  // @formatter:on
  @Test
  fun singleRoot_isolated() {
    assertThat(DaggerIsolatedAgg.builder().build().target().fooA())
        .isSameInstanceAs(IsolatedAModule.instance)
  }

  // @formatter:off
  /** Topology: A <-- B */
  // @formatter:on
  @Test
  fun singleRoot_shallow() {
    val agg = DaggerShallowAgg.builder().build()
    assertThat(agg.targetA().fooA()).isSameInstanceAs(ShallowAModule.instance)
    assertThat(agg.targetB().fooB()).isSameInstanceAs(ShallowBModule.instance)
    assertThat(agg.targetA().fooA().b).isSameInstanceAs(agg.targetB().fooB())
  }

  // @formatter:off
  /** Topology: A <-- B <-- C <-- D <-- E */
  // @formatter:on
  @Test
  fun singleRoot_deep() {
    val agg = DaggerDeepAgg.builder().build()
    assertThat(agg.targetA().fooA()).isSameInstanceAs(DeepAModule.instance)
    assertThat(agg.targetB().fooB()).isSameInstanceAs(DeepBModule.instance)
    assertThat(agg.targetC().fooC()).isSameInstanceAs(DeepCModule.instance)
    assertThat(agg.targetD().fooD()).isSameInstanceAs(DeepDModule.instance)
    assertThat(agg.targetE().fooE()).isSameInstanceAs(DeepEModule.instance)
  }

  // @formatter:off
  /** Topology: /-- B --\ A <-- -- D \-- C --/ */
  // @formatter:on
  @Test
  fun singleRoot_diamond() {
    val agg = DaggerDiamondAgg.builder().build()
    assertThat(agg.targetA().fooA()).isSameInstanceAs(DiamondAModule.instance)
    assertThat(agg.targetB().fooB()).isSameInstanceAs(DiamondBModule.instance)
    assertThat(agg.targetC().fooC()).isSameInstanceAs(DiamondCModule.instance)
    assertThat(agg.targetD().fooD()).isSameInstanceAs(DiamondDModule.instance)
  }

  // @formatter:off
  /** Topology: /-- B1 /--- B2 A <----- B3 \--- B4 \-- B5 */
  // @formatter:on
  @Test
  fun singleRoot_wide() {
    val agg = DaggerWideAgg.builder().build()
    assertThat(agg.targetA().fooA()).isSameInstanceAs(WideAModule.instance)
    assertThat(agg.targetB1().fooB1()).isSameInstanceAs(WideB1Module.instance)
    assertThat(agg.targetB2().fooB2()).isSameInstanceAs(WideB2Module.instance)
    assertThat(agg.targetB3().fooB3()).isSameInstanceAs(WideB3Module.instance)
    assertThat(agg.targetB4().fooB4()).isSameInstanceAs(WideB4Module.instance)
    assertThat(agg.targetB5().fooB5()).isSameInstanceAs(WideB5Module.instance)
  }

  // @formatter:off
  /** Topology: /--- B <-- C A <-- / \--------/ */
  // @formatter:on
  @Test
  fun singleRoot_triangle() {
    val agg = DaggerTriangleAgg.builder().build()
    assertThat(agg.targetA().fooA()).isSameInstanceAs(TriangleAModule.instance)
    assertThat(agg.targetB().fooB()).isSameInstanceAs(TriangleBModule.instance)
    assertThat(agg.targetC().fooC()).isSameInstanceAs(TriangleCModule.instance)
  }

  // @formatter:off
  /**
   * Topology: /-- C1 /--- C2 /-- B1 <--- C3 / \--- C4 / \-- C5 / / /-- C6 / /--- C7 / --- B2
   * <------- C8 / \--- C9 / \-- C10 / / /-- C11 / /--- C12 A <----------- B3 <------ C13 \ \--- C14
   * \ \-- C15 \ \ /-- C16 \ /--- C17 \ --- B4 <------- C18 \ \--- C19 \ \-- C20 \ \ /-- C21 \ /---
   * C22 \-- B5 <--- C23 \--- C24 \-- C25
   */
  // @formatter:on
  @Test
  fun singleRoot_broad() {
    val target = DaggerBroadAgg.builder().build().target()
    assertThat(target.fooA()).isSameInstanceAs(BroadAModule.instance)
    assertThat(target.fooB1()).isSameInstanceAs(BroadB1Module.instance)
    assertThat(target.fooB2()).isSameInstanceAs(BroadB2Module.instance)
    assertThat(target.fooB3()).isSameInstanceAs(BroadB3Module.instance)
    assertThat(target.fooB4()).isSameInstanceAs(BroadB4Module.instance)
    assertThat(target.fooB5()).isSameInstanceAs(BroadB5Module.instance)
    assertThat(target.fooC1()).isSameInstanceAs(BroadC1Module.instance)
    assertThat(target.fooC2()).isSameInstanceAs(BroadC2Module.instance)
    assertThat(target.fooC3()).isSameInstanceAs(BroadC3Module.instance)
    assertThat(target.fooC4()).isSameInstanceAs(BroadC4Module.instance)
    assertThat(target.fooC5()).isSameInstanceAs(BroadC5Module.instance)
    assertThat(target.fooC6()).isSameInstanceAs(BroadC6Module.instance)
    assertThat(target.fooC7()).isSameInstanceAs(BroadC7Module.instance)
    assertThat(target.fooC8()).isSameInstanceAs(BroadC8Module.instance)
    assertThat(target.fooC9()).isSameInstanceAs(BroadC9Module.instance)
    assertThat(target.fooC10()).isSameInstanceAs(BroadC10Module.instance)
    assertThat(target.fooC11()).isSameInstanceAs(BroadC11Module.instance)
    assertThat(target.fooC12()).isSameInstanceAs(BroadC12Module.instance)
    assertThat(target.fooC13()).isSameInstanceAs(BroadC13Module.instance)
    assertThat(target.fooC14()).isSameInstanceAs(BroadC14Module.instance)
    assertThat(target.fooC15()).isSameInstanceAs(BroadC15Module.instance)
    assertThat(target.fooC16()).isSameInstanceAs(BroadC16Module.instance)
    assertThat(target.fooC17()).isSameInstanceAs(BroadC17Module.instance)
    assertThat(target.fooC18()).isSameInstanceAs(BroadC18Module.instance)
    assertThat(target.fooC19()).isSameInstanceAs(BroadC19Module.instance)
    assertThat(target.fooC20()).isSameInstanceAs(BroadC20Module.instance)
    assertThat(target.fooC21()).isSameInstanceAs(BroadC21Module.instance)
    assertThat(target.fooC22()).isSameInstanceAs(BroadC22Module.instance)
    assertThat(target.fooC23()).isSameInstanceAs(BroadC23Module.instance)
    assertThat(target.fooC24()).isSameInstanceAs(BroadC24Module.instance)
    assertThat(target.fooC25()).isSameInstanceAs(BroadC25Module.instance)
  }

  // @formatter:off
  /**
   * Topology: A
   *
   * B
   */
  // @formatter:on
  @Test
  fun multipleRoots_disconnected() {
    val agg = DaggerDisconnectedAgg.builder().build()
    assertThat(agg.targetA().fooA()).isSameInstanceAs(DisconnectedAModule.instance)
    assertThat(agg.targetB().fooB()).isSameInstanceAs(DisconnectedBModule.instance)
  }

  // @formatter:off
  /** Topology: A <--\ -- C B <--/ */
  // @formatter:on
  @Test
  fun multipleRoots_joinedAtBase() {
    val agg = DaggerJoinedAtBaseAgg.builder().build()
    assertThat(agg.targetA().fooA()).isSameInstanceAs(JoinedAtBaseAModule.instance)
    assertThat(agg.targetB().fooB()).isSameInstanceAs(JoinedAtBaseBModule.instance)
    assertThat(agg.targetC().fooC()).isSameInstanceAs(JoinedAtBaseCModule.instance)
  }

  // @formatter:off
  /** Topology: A <--\ /-- D <--\ -- C <-- -- F B <--/ \-- E <--/ */
  // @formatter:on
  @Test
  fun multipleRoots_joinedDiamond() {
    val agg = DaggerJoinedDiamondAgg.builder().build()
    assertThat(agg.targetA().fooA()).isSameInstanceAs(JoinedDiamondAModule.instance)
    assertThat(agg.targetB().fooB()).isSameInstanceAs(JoinedDiamondBModule.instance)
    assertThat(agg.targetC().fooC()).isSameInstanceAs(JoinedDiamondCModule.instance)
    assertThat(agg.targetD().fooD()).isSameInstanceAs(JoinedDiamondDModule.instance)
    assertThat(agg.targetE().fooE()).isSameInstanceAs(JoinedDiamondEModule.instance)
    assertThat(agg.targetF().fooF()).isSameInstanceAs(JoinedDiamondFModule.instance)
  }

  // @formatter:off
  /** Topology: A <-- C <-- E <--\ -- G B <-- D <-- F <--/ */
  // @formatter:on
  @Test
  fun multipleRoots_convergingChains() {
    val agg = DaggerConvergingChainsAgg.builder().build()
    assertThat(agg.targetA().fooA()).isSameInstanceAs(ConvergingChainsAModule.instance)
    assertThat(agg.targetB().fooB()).isSameInstanceAs(ConvergingChainsBModule.instance)
    assertThat(agg.targetC().fooC()).isSameInstanceAs(ConvergingChainsCModule.instance)
  }

  // @formatter:off
  /**
   * Topology: A1 <-- A2 <-- A3 <-- A4 <-- A5 <-- A6 <-- A7 <-- A8 <-- A9 <-- A10
   *
   * B1 <-- B2
   */
  // @formatter:on
  @Test
  fun multipleRoots_asymmetricChains() {
    val agg = DaggerAsymmetricChainsAgg.builder().build()
    assertThat(agg.targetA().fooA()).isSameInstanceAs(AsymmetricChainsAModule.instance)
    assertThat(agg.targetB().fooB()).isSameInstanceAs(AsymmetricChainsBModule.instance)
  }
}
