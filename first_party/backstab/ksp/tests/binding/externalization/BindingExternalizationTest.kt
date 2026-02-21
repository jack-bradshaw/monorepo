package com.jackbradshaw.backstab.ksp.tests.binding.externalization

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Verifies that bindings defined deep within the component graph can be externalized via the
 * aggregate component.
 *
 * Verification is performed by building the aggregate component and checking that an object
 * constructed deep within the graph matches the object with the same qualified type pulled from the
 * aggregate. This approach ensures bound values are propagated from the leaf components to the
 * aggregate properly (i.e. externalized). If the components compile and if the retrieved instance
 * matches exactly then the graph was properly compiled and connected. All allowable binding
 * variations are used to ensure Backstab-generated code is fully compatible with standard Dagger.
 */
@RunWith(JUnit4::class)
class BindingExternalizationTest {

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
  fun type_alias() {
    val aggregate = DaggerTypeAliasAgg.builder().build()
    assertThat(aggregate.target().userId()).isSameInstanceAs(TypeAliasModule.instance)
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
