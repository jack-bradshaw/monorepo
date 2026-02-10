package com.jackbradshaw.backstab

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.backstab.BaseTypes.Bar
import com.jackbradshaw.backstab.BaseTypes.Foo
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


/**
 * Tests the Backstab integration end-to-end.
 *
 * This test exercises the components defined in `TestingComponents.kt` and verifies their behavior.
 * Each test case has an associated entry in `TestingComponents.kt`, with strict separation to keep
 * test cases independent. * This ensures that failures in one topology do not cascade to others and
 * that the test suite remains robust as the system evolves.
 *
 * **Testing Dimensions:** The test suite covers the following dimensions across the Dagger topology
 * space:
 * 1. **Topology:** The structure of the dependency graph (Isolated, Shallow, Wide, Deep, Diamond).
 * 2. **Instantiation:** How the components are created (Implicit `create()`, `Builder`, `Factory`).
 * 3. **Bindings:** How dependencies are provided (Modules, `@BindsInstance`, Qualifiers).
 * 4. **Edge Cases:** Generic types, Type Aliases, Nullability.
 *
 * **Naming Convention:** Test cases are named `[Dimension]_[Case]` (e.g., `topology_diamond`,
 * `instantiation_factory`). The associated components in `TestingComponents.kt` follow the pattern:
 * `[Dimension]_[Case]_Test_[Role]` (e.g., `Topology_Diamond_Test_Aggregate`,
 * `Topology_Diamond_Test_DiamondLeft`).
 */
@RunWith(JUnit4::class)
class BackstabKspTest {

  @Test
  fun topology_isolated() {
    val foo = Foo()
    val aggregate = DaggerTopology_Isolated_Test_Aggregate.builder().bindFoo(foo).build()

    assertThat(aggregate.target().foo()).isSameInstanceAs(foo)
  }

  @Test
  fun topology_shallow() {
    val foo = Foo()
    val supporting = DaggerTopology_Shallow_Test_Supporting.builder().bindFoo(foo).build()

    val aggregate = DaggerTopology_Shallow_Test_Aggregate.builder().supporting(supporting).build()

    assertThat(aggregate.target().foo()).isSameInstanceAs(foo)
  }

  @Test
  fun topology_wide() {
    val foo = Foo()
    val bar = Bar()
    val supportingA = DaggerTopology_Wide_Test_SupportingA.builder().bindFoo(foo).build()
    val supportingB = DaggerTopology_Wide_Test_SupportingB.builder().bindBar(bar).build()

    val aggregate =
        DaggerTopology_Wide_Test_Aggregate.builder()
            .supportingA(supportingA)
            .supportingB(supportingB)
            .build()

    assertThat(aggregate.target().foo()).isSameInstanceAs(foo)
    assertThat(aggregate.target().bar()).isSameInstanceAs(bar)
  }

  @Test
  fun topology_deep() {
    val foo = Foo()
    val deepRoot = DaggerTopology_Deep_Test_DeepRoot.builder().bindFoo(foo).build()
    val deepNode = DaggerTopology_Deep_Test_DeepNode.builder().deepRoot(deepRoot).build()

    val aggregate = DaggerTopology_Deep_Test_Aggregate.builder().deepNode(deepNode).build()

    assertThat(aggregate.target().foo()).isSameInstanceAs(foo)
  }

  @Test
  fun topology_diamond() {
    val foo = Foo()
    val diamondRoot = DaggerTopology_Diamond_Test_DiamondRoot.builder().bindFoo(foo).build()
    val diamondLeft =
        DaggerTopology_Diamond_Test_DiamondLeft.builder().diamondRoot(diamondRoot).build()
    val diamondRight =
        DaggerTopology_Diamond_Test_DiamondRight.builder().diamondRoot(diamondRoot).build()

    val aggregate =
        DaggerTopology_Diamond_Test_Aggregate.builder()
            .diamondLeft(diamondLeft)
            .diamondRight(diamondRight)
            .build()

    assertThat(aggregate.target().foo()).isSameInstanceAs(foo)
  }

  @Test
  fun instantiation_implicit() {
    val aggregate = DaggerInstantiation_Implicit_Test_Aggregate.create()
    assertThat(aggregate.target()).isNotNull()
  }

  @Test
  fun instantiation_builder() {
    val foo = Foo()
    val aggregate = DaggerInstantiation_Builder_Test_Aggregate.builder().bindFoo(foo).build()
    assertThat(aggregate.target().foo()).isSameInstanceAs(foo)
  }

  @Test
  fun instantiation_factory() {
    val foo = Foo()
    val aggregate = DaggerInstantiation_Factory_Test_Aggregate.factory().create(foo)
    assertThat(aggregate.target().foo()).isSameInstanceAs(foo)
  }

  @Test
  fun bindings_modules() {
    val aggregate = DaggerBindings_Modules_Test_Aggregate.create()
    assertThat(aggregate.target().foo()).isNotNull()
  }

  @Test
  fun bindings_bindsInstance() {
    val foo = Foo()
    val aggregate = DaggerBindings_BindsInstance_Test_Aggregate.builder().bindFoo(foo).build()

    assertThat(aggregate.target().foo()).isSameInstanceAs(foo)
  }

  @Test
  fun bindings_qualifiers() {
    val foo = Foo()
    val bar = Bar()
    val aggregate =
        DaggerBindings_Qualifiers_Test_Aggregate.builder()
            .bindNamedFoo(foo)
            .bindQualifiedBar(bar)
            .build()

    assertThat(aggregate.target().namedFoo()).isSameInstanceAs(foo)
    assertThat(aggregate.target().qualifiedBar()).isSameInstanceAs(bar)
  }

  @Test
  fun edgeCase_generics() {
    val list = listOf("hello")
    val aggregate = DaggerEdgeCase_Generics_Test_Aggregate.builder().bindStrings(list).build()

    assertThat(aggregate.target().strings()).isEqualTo(list)
  }

  @Test
  fun edgeCase_typeAliases() {
    val user = "user123"
    val aggregate = DaggerEdgeCase_TypeAliases_Test_Aggregate.builder().bindUser(user).build()

    assertThat(aggregate.target().user()).isEqualTo(user)
  }

  @Test
  fun edgeCase_nullability() {
    // Null case
    assertThat(
            DaggerEdgeCase_Nullability_Test_Aggregate.builder()
                .bindNullableFoo(null)
                .build()
                .target()
                .nullableFoo())
        .isNull()

    // Non-null case
    val foo = Foo()
    assertThat(
            DaggerEdgeCase_Nullability_Test_Aggregate.builder()
                .bindNullableFoo(foo)
                .build()
                .target()
                .nullableFoo())
        .isSameInstanceAs(foo)
  }
}
