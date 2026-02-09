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

@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class LibQualifier

interface LibUpstreamComponent {
  @Named("upstream") fun provideUpstreamString(): String
}

class LibTargetClass {
  @Inject @Named("upstream") lateinit var upstreamString: String
}

@Singleton
@Component(modules = [LibTestModule::class], dependencies = [LibUpstreamComponent::class])
interface LibTestComponent {
  fun getString(): String

  @Named("upstream") fun getUpstreamString(): String

  @LibQualifier fun getQualifiedString(): String

  fun getLazyString(): Lazy<String>

  fun getProviderString(): Provider<String>

  fun getSet(): Set<String>

  fun getMap(): Map<String, Int>

  fun inject(target: LibTargetClass)

  fun subcomponentBuilder(): LibTestSubcomponent.Builder
}

@Module
class LibTestModule {
  @Provides @Singleton fun provideString(): String = "Hello Dagger"

  @Provides @LibQualifier fun provideQualifiedString(): String = "Qualified"

  @Provides @IntoSet fun provideSetItem1(): String = "Item1"

  @Provides @IntoSet fun provideSetItem2(): String = "Item2"

  @Provides @IntoMap @StringKey("one") fun provideMapItem1(): Int = 1

  @Provides @IntoMap @StringKey("two") fun provideMapItem2(): Int = 2
}

@Subcomponent
interface LibTestSubcomponent {
  fun getChildString(): String

  @Subcomponent.Builder
  interface Builder {
    fun build(): LibTestSubcomponent
  }
}
