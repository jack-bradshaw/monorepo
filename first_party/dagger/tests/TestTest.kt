package first_party.dagger.tests

import com.google.common.truth.Truth.assertThat
import dagger.Component
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import dagger.multibindings.StringKey
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Qualifier
import javax.inject.Singleton
import org.junit.Before
import org.junit.Test

/** Tests to verify the dagger components in the test file were handled properly by Dagger. */
class TestTest {

  private lateinit var component: TestTestComponent

  @Before
  fun setup() {
    val upstream =
        object : TestUpstreamComponent {
          override fun provideUpstreamString() = "Upstream"
        }

    component = DaggerTestTestComponent.builder().testUpstreamComponent(upstream).build()
  }

  @Test
  fun givenBasicProvision_expectHelloDagger() {
    val result = component.getString()
    assertThat(result).isEqualTo("Hello Dagger")
  }

  @Test
  fun givenDependency_expectUpstreamString() {
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
    val target = TestTargetClass()
    component.inject(target)
    assertThat(target.upstreamString).isEqualTo("Upstream")
  }

  @Test
  fun givenSubcomponentBuilder_expectSubcomponentCreated() {
    val sub = component.subcomponentBuilder().build()
    assertThat(sub).isNotNull()
  }
}

@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class TestQualifier

interface TestUpstreamComponent {
  @Named("upstream") fun provideUpstreamString(): String
}

class TestTargetClass {
  @Inject @Named("upstream") lateinit var upstreamString: String
}

@Singleton
@Component(modules = [TestTestModule::class], dependencies = [TestUpstreamComponent::class])
interface TestTestComponent {
  fun getString(): String

  @Named("upstream") fun getUpstreamString(): String

  @TestQualifier fun getQualifiedString(): String

  fun getLazyString(): Lazy<String>

  fun getProviderString(): Provider<String>

  fun getSet(): Set<String>

  fun getMap(): Map<String, Int>

  fun inject(target: TestTargetClass)

  fun subcomponentBuilder(): TestTestSubcomponent.Builder
}

@Module
class TestTestModule {
  @Provides @Singleton fun provideString(): String = "Hello Dagger"

  @Provides @TestQualifier fun provideQualifiedString(): String = "Qualified"

  @Provides @IntoSet fun provideSetItem1(): String = "Item1"

  @Provides @IntoSet fun provideSetItem2(): String = "Item2"

  @Provides @IntoMap @StringKey("one") fun provideMapItem1(): Int = 1

  @Provides @IntoMap @StringKey("two") fun provideMapItem2(): Int = 2
}

@Subcomponent
interface TestTestSubcomponent {
  fun getChildString(): String

  @Subcomponent.Builder
  interface Builder {
    fun build(): TestTestSubcomponent
  }
}
