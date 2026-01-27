# Coroutines

Kotlin coroutine infrastructure.

## Release

Not released to third party package managers.

## Utilities

This package provides the following production utilities:

- [IO Dispatcher](/first_party/coroutines/io): A
  [CoroutineDispatcher](https://kotlinlang.org/api/latest/jvm/stdlib/kotlinx.coroutines/-coroutine-dispatcher/)
  for IO-bound work.

This package provides the following test utilities:

- [Test Scope](/first_party/coroutines/testing/scope): A
  [TestScope](https://kotlinlang.org/api/kotlinx.coroutines.test/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-scope/)
  to use and control coroutines in tests.
- [Launcher](/first_party/coroutines/testing/launcher): A utility for launching coroutines in tests.

The utilities are integrated/exposed using [Dagger](https://dagger.dev), and two components are
provided:

- The [production component](/first_party/coroutines/Coroutines.kt), which provides production
  versions
- The [test component](/first_party/coroutines/testing/TestCoroutines.kt), which provides test
  doubles.

The test component extends the production component; therefore, the test component can be
substituted anywhere the production component is required. Furthermore, an equivalent Kotlin factory
function is provided for each, specifically `com.jackbradshaw.coroutines.coroutines` for the former,
and `com.jackbradshaw.coroutines.testing.testCoroutines` for the latter.

## Example

The following example contains a `FooRepository` class which uses the `IO` dispatcher from the
coroutine infrastructure to perform background work. The production setup integrates the production
infrastructure for normal execution, and the test setup integrates the test infrastructure to
control virtual time and make the test deterministic.

```kotlin
import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.coroutines.Coroutines
import com.jackbradshaw.coroutines.coroutines
import com.jackbradshaw.coroutines.io.Io
import com.jackbradshaw.coroutines.testing.testCoroutines
import dagger.Component
import javax.inject.Inject
import javax.inject.Scope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Scope @Retention(AnnotationRetention.RUNTIME) annotation class FooRepositoryScope

@FooRepositoryScope
class FooRepository @Inject constructor(
  @Io private val ioScope: CoroutineScope
) {
  suspend fun fetchData(): String = withContext(ioScope.coroutineContext) {
    // Simulate network delay.
    delay(1000)

    // Simulate network data.
    "foo"
  }
}

@FooRepositoryScope
@Component(dependencies = [Coroutines::class])
interface FooRepositoryComponent {
  fun repository(): FooRepository

  @Component.Builder
  interface Builder {
    fun consuming(coroutines: Coroutines): Builder
    fun build(): FooRepositoryComponent
  }
}

fun fooRepository(coroutines: Coroutines = coroutines()) = DaggerFooRepositoryComponent.builder().consuming(coroutines).build()

class Main {
  fun main(args: Array<String>) {
    runBlocking {
      val repository = fooRepository().repository()
      val data = repository.fetchData()
      println("Fetched data: $data")
    }
  }
}

@FooRepositoryScope
@Component(dependencies = [Coroutines::class])
interface FooRepositoryTestComponent {
  fun inject(target: FooRepositoryTest)

  @Component.Builder
  interface Builder {
    fun consuming(coroutines: Coroutines): Builder
    fun build(): FooRepositoryTestComponent
  }
}

fun fooRepositoryTest(coroutines: Coroutines = testCoroutines()) = DaggerFooRepositoryTestComponent.builder().consuming(coroutines).build()

@RunWith(JUnit4::class)
class FooRepositoryTest {

  @Inject lateinit var launcher: Launcher
  @Inject lateinit var testScope: TestScope
  @Inject lateinit var repository: FooRepository

  @Before
  fun setup() {
    fooRepositoryTest().inject(this)
  }

  @Test
  fun fetchData_returnsData() = testScope.runTest {
    // Launch the fetchData coroutine eagerly using the launcher
    val deferredData = launcher.launchEagerly {
      repository.fetchData()
    }

    // Advance the virtual time until all coroutines are idle
    testScope.advanceUntilIdle()

    // Await the result from the deferred coroutine
    val data = deferredData.await()
    assertThat(data).isEqualTo("foo")
  }
}
## Issues

Issues relating to this package and its subpackages are tagged with `coroutines`.

## Contributions

Third-party contributions are accepted.
```
