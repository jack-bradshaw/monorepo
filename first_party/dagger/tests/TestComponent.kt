package first_party.dagger.tests

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

// 0. Qualifiers
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class MyQualifier

// 1. Upstream Dependency
interface UpstreamComponent {
  @Named("upstream") fun provideUpstreamString(): String
}

// 2. Target for Members Injection
class TargetClass {
  @Inject @Named("upstream") lateinit var upstreamString: String
}

// 3. The Main Component
@Singleton
@Component(modules = [TestModule::class], dependencies = [UpstreamComponent::class])
interface TestComponent {
  fun getString(): String

  @Named("upstream") fun getUpstreamString(): String

  @MyQualifier fun getQualifiedString(): String

  fun getLazyString(): Lazy<String>

  fun getProviderString(): Provider<String>

  fun getSet(): Set<String>

  fun getMap(): Map<String, Int>

  fun inject(target: TargetClass)

  fun subcomponentBuilder(): TestSubcomponent.Builder
}

// 4. The Module
@Module
class TestModule {
  @Provides @Singleton fun provideString(): String = "Hello Dagger"

  @Provides @MyQualifier fun provideQualifiedString(): String = "Qualified"

  @Provides @IntoSet fun provideSetItem1(): String = "Item1"

  @Provides @IntoSet fun provideSetItem2(): String = "Item2"

  @Provides @IntoMap @StringKey("one") fun provideMapItem1(): Int = 1

  @Provides @IntoMap @StringKey("two") fun provideMapItem2(): Int = 2
}

// 5. Subcomponent
@Subcomponent
interface TestSubcomponent {
  fun getChildString(): String

  @Subcomponent.Builder
  interface Builder {
    fun build(): TestSubcomponent
  }
}
