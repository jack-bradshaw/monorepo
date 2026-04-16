package com.jackbradshaw.oksp.testing.application.test

import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.ApplicationComponent
import com.jackbradshaw.oksp.service.ProcessingService
import com.jackbradshaw.oksp.testing.application.applicationTestRule
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.JUnitCore
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApplicationTestRuleTest {

  @Test
  fun ruleEvaluates_onCreateDoesNotFail() {
    val result = JUnitCore.runClasses(DummyTest_OnCreate_DoesNotFail::class.java)
    result.failures.forEach {
      println("Failure: ${it.exception.message}")
      it.exception.printStackTrace()
    }
    assertThat(result.wasSuccessful()).isTrue()
  }

  @Test
  fun ruleEvaluates_onDestroyOnceAfterOnCreate_DoesNotFail() {
    val result = JUnitCore.runClasses(DummyTest_OnDestroyOnceAfterOnCreate_DoesNotFail::class.java)
    assertThat(result.wasSuccessful()).isTrue()
  }

  @Test
  fun ruleEvaluates_onDestroyTwiceAfterOnCreate_DoesNotFail() {
    val result = JUnitCore.runClasses(DummyTest_OnDestroyTwiceAfterOnCreate_DoesNotFail::class.java)
    assertThat(result.wasSuccessful()).isTrue()
  }

  @Test
  fun ruleEvaluates_onDestroyWithoutOnCreate_DoesNotFail() {
    val result = JUnitCore.runClasses(DummyTest_OnDestroyWithoutOnCreate_DoesNotFail::class.java)
    assertThat(result.wasSuccessful()).isTrue()
  }

  @Test
  fun ruleEvaluates_onCreateFailsDueToBadCode_CatchesError() {
    val result = JUnitCore.runClasses(DummyTest_OnCreate_FailsDueToBadCode::class.java)
    assertThat(result.wasSuccessful()).isTrue()
  }

  class DummyTest_OnCreate_DoesNotFail {
    @get:Rule
    val testRule = applicationTestRule(application = TestApplication(), sources = listOf())

    @Test
    fun dummyTest() = runBlocking {
      val res = testRule.result
      if (res != null && !res.isSuccessful) {
        println("Compilation Failed. KSP Output:")
        println(res.messages)
      }
      assertThat(res?.isSuccessful).isTrue()
    }
  }

  class DummyTest_OnDestroyOnceAfterOnCreate_DoesNotFail {
    @get:Rule
    val testRule = applicationTestRule(application = TestApplication(), sources = listOf())

    @Test
    fun dummyTest() = runBlocking {
      val res = testRule.result
      if (res != null && !res.isSuccessful) {
        println("Compilation Failed. KSP Output:")
        println(res.messages)
      }
      assertThat(res?.isSuccessful).isTrue()
    }
  }

  class DummyTest_OnDestroyTwiceAfterOnCreate_DoesNotFail {
    @get:Rule
    val testRule = applicationTestRule(application = TestApplication(), sources = listOf())

    @Test
    fun dummyTest() = runBlocking {
      val res = testRule.result
      if (res != null && !res.isSuccessful) {
        println("Compilation Failed. KSP Output:")
        println(res.messages)
      }
      assertThat(res?.isSuccessful).isTrue()
    }
  }

  class DummyTest_OnDestroyWithoutOnCreate_DoesNotFail {
    @get:Rule
    val testRule = applicationTestRule(application = TestApplication(), sources = listOf())

    @Test
    fun dummyTest() = runBlocking {
      val res = testRule.result
      if (res != null && !res.isSuccessful) {
        println("Compilation Failed. KSP Output:")
        println(res.messages)
      }
      assertThat(res?.isSuccessful).isTrue()
    }
  }

  class DummyTest_OnCreate_FailsDueToBadCode {
    @get:Rule
    val testRule =
        applicationTestRule(
            application = TestApplication(),
            sources =
                listOf(
                    com.jackbradshaw.kale.ksprunner.JvmSource(
                        packageName = "com.test",
                        fileName = "Bad",
                        extension = "kt",
                        contents = "package com.test\nthis is invalid kotlin code")))

    @Test
    fun dummyTest() = runBlocking {
      val res = testRule.result
      assertThat(res).isNotNull()
      assertThat(res!!.isSuccessful).isFalse()
      assertThat(res.messages).contains("this is invalid kotlin code")
    }
  }

  class Component : ApplicationComponent {
    override fun environment(): SymbolProcessorEnvironment = TODO()

    override fun processingService(): ProcessingService = TODO()
  }

  class TestApplication : Application {
    override suspend fun onCreate(component: ApplicationComponent) {}

    override suspend fun onDestroy() {}
  }
}
