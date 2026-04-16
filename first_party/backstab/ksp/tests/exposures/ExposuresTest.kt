package com.jackbradshaw.backstab.ksp.tests.exposures

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Verifies that Backstab aggregate components can correctly expose one or more components from the
 * dependency graph.
 *
 * Verification is performed by building the aggregate component and checking that the requested
 * components are accessible and correctly connected (i.e. they provide the expected objects). If
 * the components compile and provide the expected types then the graph was properly compiled and
 * exposed.
 */
@RunWith(JUnit4::class)
class ExposuresTest {

  /**
   * Topology: A <-- B <-- C
   *
   * Exposure: A (root)
   */
  @Test
  fun exposeRoot() {
    DaggerRootComponentAgg.builder().build().a().foo()
  }

  /**
   * Topology: A <-- B <-- C
   *
   * Exposure: B (transitive)
   */
  @Test
  fun exposeTransitive() {
    DaggerTransitiveComponentAgg.builder().build().b().foo()
  }

  /**
   * Topology: A <-- B <-- C
   *
   * Exposure: A, B, C (all)
   */
  @Test
  fun exposeMultiple() {
    val agg = DaggerMultipleComponentsAgg.builder().build()
    agg.a().foo()
    agg.b().foo()
    agg.c().foo()
  }
}
