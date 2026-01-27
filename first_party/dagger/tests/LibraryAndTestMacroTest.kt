package first_party.dagger.tests

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

/** Tests to verify the generated Dagger components work as intended. */
class LibraryAndTestMacroTest {

  private lateinit var component: TestComponent

  @Before
  fun setup() {
    val upstream =
        object : UpstreamComponent {
          override fun provideUpstreamString() = "Upstream"
        }

    component = DaggerTestComponent.builder().upstreamComponent(upstream).build()
  }

  @Test
  fun givenBasicProvision_expectHelloDagger() {
    val result = component.getString()
    assertThat(result).isEqualTo("Hello Dagger")
  }

  @Test
  fun givenDependeny_expectUpstreamString() {
    val result = component.getUpstreamString()
    assertThat(result).isEqualTo("Upstream")
  }

  @Test
  fun givenMultibindings_expectSetSizeAndContent() {
    val set = component.getSet()
    assertThat(set).hasSize(2)
    assertThat(set).contains("Item1")
    assertThat(set).contains("Item2")
  }

  @Test
  fun givenMultibindings_expectMapSizeAndContent() {
    val map = component.getMap()
    assertThat(map).hasSize(2)
    assertThat(map).containsEntry("one", 1)
    assertThat(map).containsEntry("two", 2)
  }

  @Test
  fun givenCustomQualifier_expectQualifiedString() {
    val result = component.getQualifiedString()
    assertThat(result).isEqualTo("Qualified")
  }

  @Test
  fun givenLazyInjection_expectString() {
    val result = component.getLazyString().get()
    assertThat(result).isEqualTo("Hello Dagger")
  }

  @Test
  fun givenProviderInjection_expectString() {
    val result = component.getProviderString().get()
    assertThat(result).isEqualTo("Hello Dagger")
  }

  @Test
  fun givenMembersInjection_expectFieldInjected() {
    val target = TargetClass()
    component.inject(target)
    assertThat(target.upstreamString).isEqualTo("Upstream")
  }

  @Test
  fun givenSubcomponentBuilder_expectSubcomponentCreated() {
    val sub = component.subcomponentBuilder().build()
    assertThat(sub).isNotNull()
  }
}
