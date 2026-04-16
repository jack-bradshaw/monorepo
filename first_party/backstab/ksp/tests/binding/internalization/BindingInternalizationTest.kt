package com.jackbradshaw.backstab.ksp.tests.binding.internalization

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Verifies that bindings defined at the aggregate component can be internalized deep within the
 * component graph.
 *
 * Verification is performed by building the aggregate component and checking that an object
 * provided to the aggregate matches the object with the same qualified type retrieved deep within
 * the graph. This approach ensures bound values are propagated from the aggregate to the leaf
 * components properly (i.e. internalized). If the components compile and if the retrieved instance
 * matches exactly then the graph was properly compiled and connected. All allowable binding
 * variations are used to ensure Backstab-generated code is fully compatible with standard Dagger.
 */
@RunWith(JUnit4::class)
class BindingInternalizationTest {

  @Test
  fun unqualified() {
    val aggregate = DaggerUnqualifiedAgg.builder().build()
    assertThat(aggregate.target().foo()).isSameInstanceAs(UnqualifiedModule.instance)
  }

  @Test
  fun named() {
    val aggregate = DaggerNamedAgg.builder().build()
    assertThat(aggregate.target().foo()).isSameInstanceAs(NamedModule.instance)
  }

  @Test
  fun qualified() {
    val aggregate = DaggerQualifiedAgg.builder().build()
    assertThat(aggregate.target().foo()).isSameInstanceAs(QualifiedModule.instance)
  }

  @Test
  fun typeArguments_invariant() {
    val aggregate = DaggerTypeArgumentsInvariantAgg.builder().build()
    assertThat(aggregate.target().box()).isSameInstanceAs(TypeArgumentsInvariantModule.instance)
  }

  @Test
  fun typeArguments_wide() {
    val aggregate = DaggerTypeArgumentsWideAgg.builder().build()
    assertThat(aggregate.target().box()).isSameInstanceAs(TypeArgumentsWideModule.instance)
  }

  @Test
  fun typeArguments_deep() {
    val aggregate = DaggerTypeArgumentsDeepAgg.builder().build()
    assertThat(aggregate.target().box()).isSameInstanceAs(TypeArgumentsDeepModule.instance)
  }

  @Test
  fun typeArguments_covariant() {
    val aggregate = DaggerTypeArgumentsCovariantAgg.builder().build()
    assertThat(aggregate.target().box()).isSameInstanceAs(TypeArgumentsCovariantModule.instance)
  }

  @Test
  fun typeArguments_contravariant() {
    val aggregate = DaggerTypeArgumentsContravariantAgg.builder().build()
    assertThat(aggregate.target().box()).isSameInstanceAs(TypeArgumentsContravariantModule.instance)
  }
}
