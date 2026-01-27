package first_party.dagger.tests

import org.junit.Test
import org.junit.Before
import com.google.common.truth.Truth.assertThat

/** Tests to verify the generated Dagger components work as intended. */
class DaggerKspTest {

    private lateinit var component: TestComponent

    @Before
    fun setup() {
        val upstream = object : UpstreamComponent {
            override fun provideUpstreamString() = "Upstream"
        }

        component = DaggerTestComponent.builder()
            .upstreamComponent(upstream)
            .build()
    }

    @Test
    fun givenBasicProvision_expectHelloDagger() {
        assertThat(component.getString()).isEqualTo("Hello Dagger")
    }

    @Test
    fun givenDependeny_expectUpstreamString() {
        assertThat(component.getUpstreamString()).isEqualTo("Upstream")
    }

    @Test
    fun givenMultibindings_expectSetSizeAndContent() {
        val multibindingSet = component.getSet()
        assertThat(multibindingSet).containsExactly("Item1", "Item2")
    }

    @Test
    fun givenMultibindings_expectMapSizeAndContent() {
        val multibindingMap = component.getMap()
        assertThat(multibindingMap).containsExactlyEntriesIn(mapOf("one" to 1, "two" to 2))
    }

    @Test
    fun givenCustomQualifier_expectQualifiedString() {
        assertThat(component.getQualifiedString()).isEqualTo("Qualified")
    }

    @Test
    fun givenLazyInjection_expectString() {
        assertThat(component.getLazyString().get()).isEqualTo("Hello Dagger")
    }
    
    @Test
    fun givenProviderInjection_expectString() {
        assertThat(component.getProviderString().get()).isEqualTo("Hello Dagger")
    }

    @Test
    fun givenMembersInjection_expectFieldInjected() {
        val target = TargetClass()
        component.inject(target)
        assertThat(target.upstreamString).isEqualTo("Upstream")
    }

    @Test
    fun givenSubcomponentBuilder_expectSubcomponentCreated() {
        assertThat(component.subcomponentBuilder().build()).isNotNull()
    }
}
