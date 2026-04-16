package com.jackbradshaw.backstab.ksp.tests.misc

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Verifies that Backstab works in uncommon edge cases, including structural nesting and
 * manually-defined module interoperability.
 *
 * Verification for structural nesting is performed by building the aggregate component. This
 * approach ensures that Backstab correctly processes and connects non-standard graph structures
 * (e.g. components nested inside other classes). If the aggregate can be instantiated the system
 * works as intended.
 *
 * Verification for module interoperability is performed by building the aggregate component and
 * checking that an object constructed deep within the graph matches the object with the same
 * qualified type pulled from the aggregate. This approach ensures components provided via manual
 * Dagger modules are correctly integrated into the component graph. If the components compile and
 * the retrieved instances match exactly then the graph was properly compiled and connected.
 */
@RunWith(JUnit4::class)
class MiscTest {

  @Test
  fun nested_component() {
    DaggerNestedComponentAgg.builder().build()
  }

  @Test
  fun nested_aggregate() {
    DaggerNestedAggregateOuter_Agg.builder().build()
  }

  @Test
  fun moduleProvided_leaf() {
    val aggregate = DaggerModuleProvidedLeafAgg.builder().build()
    assertThat(aggregate.foo()).isSameInstanceAs(ModuleProvidedLeafModule1.instance)
  }

  @Test
  fun moduleProvided_middle() {
    val aggregate = DaggerModuleProvidedMiddleAgg.builder().build()
    assertThat(aggregate.foo()).isSameInstanceAs(ModuleProvidedMiddleModule1.instance)
  }

  @Test
  fun moduleProvided_root() {
    val aggregate = DaggerModuleProvidedRootAgg.builder().build()
    assertThat(aggregate.foo()).isSameInstanceAs(ModuleProvidedRootModule1.instance)
  }
}
