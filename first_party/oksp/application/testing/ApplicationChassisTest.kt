package com.jackbradshaw.oksp.testing.application.chassis

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.kale.model.Log
import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Source as KaleSource
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.model.LogLevel
import com.jackbradshaw.oksp.model.Source
import com.jackbradshaw.oksp.service.KspService
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

abstract class ApplicationChassisTest {

  abstract fun coroutines(): RealisticCoroutinesTestingComponent

  @Test
  fun e2eNull__noActions__returnsSuccess() =
      runBlocking<Unit> {
        val app = EmptyApplication()
        val result = subject().run(app, sources = emptySet())
        assertThat(result).isInstanceOf(Result.Success::class.java)
      }

  @Test
  fun e2eNormal__providesSources__resolvable() =
      runBlocking<Unit> {
        var foundFile = false
        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                CoroutineScope(coroutines().ioDispatcher()).launch {
                  val service = component.kspService()
                  service.allowProcessing()
                  service.onEachRoundStart().first()
                  service.withContext {
                    foundFile = it.resolver.getNewFiles().any { it.fileName == "File.kt" }
                  }
                  val completeRoundsJob = launch {
                    service.onEachRoundStart().collect { service.completeRound() }
                  }
                  service.completeRound()
                  service.onFinalRoundComplete().first()
                  service.allowTermination()
                  completeRoundsJob.cancelAndJoin()
                }
              }
            }
        val result =
            subject()
                .run(
                    app,
                    setOf(
                        KaleSource(
                            TEST_SOURCE.fileName,
                            TEST_SOURCE.extension,
                            TEST_SOURCE.packageName,
                            TEST_SOURCE.contents)))
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(foundFile).isTrue()
      }

  @Test
  fun e2eNormal__generatesFile__returnsSuccessWithFile() =
      runBlocking<Unit> {
        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                CoroutineScope(coroutines().ioDispatcher()).launch {
                  val service = component.kspService()
                  service.allowProcessing()
                  service.onEachRoundStart().first()
                  service.publish(TEST_FILE, emptyList())
                  val completeRoundsJob = launch {
                    service.onEachRoundStart().collect { service.completeRound() }
                  }
                  service.completeRound()
                  service.onFinalRoundComplete().first()
                  service.allowTermination()
                  completeRoundsJob.cancelAndJoin()
                }
              }
            }
        val result = subject().run(app, emptySet())
        assertThat(result).isInstanceOf(Result.Success::class.java)
        val genSources = result.artifacts.kotlinSources
        assertThat(genSources).hasSize(1)
        assertThat(genSources.first().packageName).isEqualTo("com.test")
        assertThat(genSources.first().fileName).isEqualTo("Generated")
      }

  @Test
  fun e2eNormal__emitsLog__returnsSuccessWithLog() =
      runBlocking<Unit> {
        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                CoroutineScope(coroutines().ioDispatcher()).launch {
                  val service = component.kspService()
                  service.allowProcessing()
                  service.onEachRoundStart().first()
                  service.log("Hello Chassis", LogLevel.INFO, null)
                  val completeRoundsJob = launch {
                    service.onEachRoundStart().collect { service.completeRound() }
                  }
                  service.completeRound()
                  service.onFinalRoundComplete().first()
                  service.allowTermination()
                  completeRoundsJob.cancelAndJoin()
                }
              }
            }
        val result = subject().run(app, emptySet())
        assertThat(result).isInstanceOf(Result.Success::class.java)
        val logs = result.logs.filterIsInstance<Log.Info>()
        assertThat(logs).hasSize(1)
        assertThat(logs.first().message).isEqualTo("Hello Chassis")
      }

  @Test
  fun e2eRepeatedCalls__multipleSequentialInvocations__remainIsolated() = runBlocking {
    val app1 = EmptyApplication()
    val app2 = EmptyApplication()

    val result1 = subject().run(app1, emptySet())
    val result2 = subject().run(app2, emptySet())

    assertThat(result1).isNotNull()
    assertThat(result2).isNotNull()
    assertThat(result1).isNotEqualTo(result2)
  }

  @Test
  fun e2eError__onCreateThrows__returnsFailureWithException() =
      runBlocking<Unit> {
        val expectedError = IllegalStateException("App Crashed!")
        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                throw expectedError
              }
            }
        val result = subject().run(app, emptySet()) as Result.Failure
        assertThat(result.error).isInstanceOf(expectedError::class.java)
        assertThat(result.error?.message).isEqualTo(expectedError.message)
      }

  @Test
  fun e2eError__onDestroyThrows__returnsFailureWithException() =
      runBlocking<Unit> {
        val expectedError = IllegalStateException("Cleanup Failed!")
        val app =
            object : StandardApplication() {
              override suspend fun onDestroy() {
                throw expectedError
              }
            }
        val result = subject().run(app, emptySet()) as Result.Failure
        assertThat(result.error).isInstanceOf(expectedError::class.java)
        assertThat(result.error?.message).isEqualTo(expectedError.message)
      }

  @Test
  fun e2eError__onDestroyThrowsAfterFileGenerated__returnsFailureWithFileAndException() =
      runBlocking<Unit> {
        val expectedError = IllegalStateException("Cleanup Failed!")
        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                CoroutineScope(coroutines().ioDispatcher()).launch {
                  val service = component.kspService()
                  service.allowProcessing()
                  service.onEachRoundStart().first()
                  service.publish(TEST_FILE, emptyList())
                  val completeRoundsJob = launch {
                    service.onEachRoundStart().collect { service.completeRound() }
                  }
                  service.completeRound()
                  service.onFinalRoundComplete().first()
                  service.allowTermination()
                  completeRoundsJob.cancelAndJoin()
                }
              }

              override suspend fun onDestroy() {
                throw expectedError
              }
            }
        val result = subject().run(app, emptySet()) as Result.Failure
        assertThat(result.error).isInstanceOf(expectedError::class.java)
        assertThat(result.error?.message).isEqualTo(expectedError.message)
        assertThat(result.artifacts.kotlinSources).hasSize(1)
      }

  @Test
  fun e2eError__onCreateThrowsAndOnDestroyThrows__returnsFailureWithException() =
      runBlocking<Unit> {
        val createError = IllegalStateException("Create Failed!")
        val destroyError = IllegalStateException("Destroy Failed!")
        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                throw createError
              }

              override suspend fun onDestroy() {
                throw destroyError
              }
            }
        val result = subject().run(app, emptySet()) as Result.Failure
        assertThat(result.error).isInstanceOf(createError::class.java)
        assertThat(result.error?.message).isEqualTo(createError.message)
        val suppressedMessages = result.error!!.suppressed.map { it.message }
        assertThat(suppressedMessages).contains(destroyError.message)
      }

  @Test
  fun e2eError__processingFails__returnsFailureWithException() =
      runBlocking<Unit> {
        val expectedError = IllegalStateException("Processing Failed!")
        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                val service = component.kspService()
                CoroutineScope(coroutines().ioDispatcher()).launch {
                  service.allowProcessing()
                  service.onEachRoundStart().first()
                  service.fail(expectedError)
                }
              }
            }
        val result = subject().run(app, emptySet()) as Result.Failure
        assertThat(result.error).isInstanceOf(expectedError::class.java)
        assertThat(result.error?.message).isEqualTo(expectedError.message)
      }

  @Test
  fun e2eError__processingFailsAfterFileGenerated__returnsFailureWithFileAndException() =
      runBlocking<Unit> {
        val expectedError = IllegalStateException("Processing Failed!")
        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                val service = component.kspService()
                CoroutineScope(coroutines().ioDispatcher()).launch {
                  service.allowProcessing()
                  service.onEachRoundStart().first()
                  service.publish(TEST_FILE, emptyList())
                  service.completeRound()
                  service.onEachRoundStart().first()
                  service.fail(expectedError)
                }
              }
            }
        val result = subject().run(app, emptySet()) as Result.Failure
        assertThat(result.error).isInstanceOf(expectedError::class.java)
        assertThat(result.error?.message).isEqualTo(expectedError.message)
        assertThat(result.artifacts.kotlinSources).hasSize(1)
      }

  @Test
  fun e2eError__processingFailsWithMessage__returnsFailureWithLogs() =
      runBlocking<Unit> {
        val expectedMessage = "Processing String Failed!"
        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                val service = component.kspService()
                CoroutineScope(coroutines().ioDispatcher()).launch {
                  service.allowProcessing()
                  service.onEachRoundStart().first()
                  service.fail(expectedMessage)
                }
              }
            }
        val result = subject().run(app, emptySet()) as Result.Failure
        assertThat(result.error).isNull()
        val logs = result.logs.filterIsInstance<Log.Error>()
        assertThat(logs).hasSize(1)
        assertThat(logs.first().message).isEqualTo(expectedMessage)
      }

  @Test
  fun e2eError__processingFailsWithMessageAfterFileGenerated__returnsFailureWithFileAndLogs() =
      runBlocking<Unit> {
        val expectedMessage = "Processing String Failed!"
        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                val service = component.kspService()
                CoroutineScope(coroutines().ioDispatcher()).launch {
                  service.allowProcessing()
                  service.onEachRoundStart().first()
                  service.publish(TEST_FILE, emptyList())
                  service.completeRound()
                  service.onEachRoundStart().first()
                  service.fail(expectedMessage)
                }
              }
            }
        val result = subject().run(app, emptySet()) as Result.Failure
        assertThat(result.error).isNull()
        val logs = result.logs.filterIsInstance<Log.Error>()
        assertThat(logs).hasSize(1)
        assertThat(logs.first().message).isEqualTo(expectedMessage)
        assertThat(result.artifacts.kotlinSources).hasSize(1)
      }

  @Test
  fun e2eError__processingAborts__returnsFailureWithException() =
      runBlocking<Unit> {
        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                val service = component.kspService()
                CoroutineScope(coroutines().ioDispatcher()).launch {
                  service.allowProcessing()
                  service.onEachRoundStart().first()
                  service.abortProcessing()
                }
              }
            }
        val result = subject().run(app, emptySet()) as Result.Failure
        assertThat(result.error).isInstanceOf(kotlinx.coroutines.CancellationException::class.java)
      }

  @Test
  fun e2eError__processingAbortsAfterFileGenerated__returnsFailureWithFileAndException() =
      runBlocking<Unit> {
        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                val service = component.kspService()
                CoroutineScope(coroutines().ioDispatcher()).launch {
                  service.allowProcessing()
                  service.onEachRoundStart().first()
                  service.publish(TEST_FILE, emptyList())
                  service.completeRound()
                  service.onEachRoundStart().first()
                  service.abortProcessing()
                }
              }
            }
        val result = subject().run(app, emptySet()) as Result.Failure
        assertThat(result.error).isInstanceOf(kotlinx.coroutines.CancellationException::class.java)
        assertThat(result.artifacts.kotlinSources).hasSize(1)
      }

  @Test
  fun partial__onStart__entersOnCreate() =
      runBlocking<Unit> {
        val app = BlockApplication()
        val runJob = launch(coroutines().ioDispatcher()) { subject().run(app, emptySet()) }
        app.onCreateStarted.await()
        app.allowCreateCompletion.complete(Unit)

        app.kspService!!.onFinalRoundComplete().first()
        app.kspService!!.allowTermination()

        app.onDestroyStarted.await()
        app.allowDestroyCompletion.complete(Unit)
        runJob.join()
      }

  @Test
  fun partial__onCreate__suppliesKspComponentLinkedToRun() =
      runBlocking<Unit> {
        var componentFound = false
        val app =
            object : EmptyApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                componentFound = true
                super.onCreate(component)
              }
            }
        val result = subject().run(app, emptySet())
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(componentFound).isTrue()
      }

  @Test
  fun partial__whileProcessing__doesNotCallOnDestroy() =
      runBlocking<Unit> {
        val app = BlockApplication()
        val runJob = launch(coroutines().ioDispatcher()) { subject().run(app, emptySet()) }

        app.onCreateStarted.await()
        app.allowCreateCompletion.complete(Unit)

        delay(100)
        assertThat(app.onDestroyStarted.isCompleted).isFalse()

        app.kspService!!.onFinalRoundComplete().first()
        app.kspService!!.allowTermination()
        app.allowDestroyCompletion.complete(Unit)
        runJob.join()
      }

  @Test
  fun partial__onProcessingComplete__entersOnDestroy() =
      runBlocking<Unit> {
        val app = BlockApplication()
        val runJob = launch(coroutines().ioDispatcher()) { subject().run(app, emptySet()) }

        app.onCreateStarted.await()
        app.allowCreateCompletion.complete(Unit)

        app.kspService!!.onFinalRoundComplete().first()
        app.kspService!!.allowTermination()

        app.onDestroyStarted.await()
        app.allowDestroyCompletion.complete(Unit)
        runJob.join()
      }

  @Test
  fun partial__onDestroySuspends__preventsKspCompletion() =
      runBlocking<Unit> {
        val app = BlockApplication()
        val runJob = launch(coroutines().ioDispatcher()) { subject().run(app, emptySet()) }

        app.allowCreateCompletion.complete(Unit)

        app.onCreateStarted.await()
        app.kspService!!.onFinalRoundComplete().first()
        app.kspService!!.allowTermination()

        app.onDestroyStarted.await()

        delay(100)
        assertThat(runJob.isCompleted).isFalse()

        app.allowDestroyCompletion.complete(Unit)
        runJob.join()
      }

  @Test
  fun partial__onCreateSuspends__preventsRoundProcessing() = runBlocking {
    val lock = CompletableDeferred<Unit>()
    val app =
        object : StandardApplication() {
          override suspend fun onCreate(component: Application.KspComponent) {
            lock.await()
            super.onCreate(component)
          }
        }

    val runJob = launch(coroutines().ioDispatcher()) { subject().run(app, emptySet()) }

    delay(500)
    assertThat(runJob.isCompleted).isFalse()

    lock.complete(Unit)
    runJob.join()
  }

  @Test
  fun partial__onCreateError__entersOnDestroy() =
      runBlocking<Unit> {
        var onDestroyCalled = false
        val expectedError = IllegalStateException("onCreate crashed")

        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                throw expectedError
              }

              override suspend fun onDestroy() {
                onDestroyCalled = true
              }
            }

        val result = subject().run(app, emptySet()) as Result.Failure

        assertThat(result.error).isInstanceOf(expectedError::class.java)
        assertThat(result.error?.message).isEqualTo(expectedError.message)
        assertThat(onDestroyCalled).isTrue()
      }

  @Test
  fun partial__onProcessingError__entersOnDestroy() =
      runBlocking<Unit> {
        var onDestroyCalled = false
        val expectedError = IllegalStateException("processing crashed")

        val app =
            object : StandardApplication() {
              override suspend fun onCreate(component: Application.KspComponent) {
                val service = component.kspService()
                CoroutineScope(coroutines().ioDispatcher()).launch {
                  service.allowProcessing()
                  service.onEachRoundStart().first()
                  service.fail(expectedError)
                }
              }

              override suspend fun onDestroy() {
                onDestroyCalled = true
              }
            }

        val result = subject().run(app, emptySet()) as Result.Failure

        assertThat(result.error).isInstanceOf(expectedError::class.java)
        assertThat(result.error?.message).isEqualTo(expectedError.message)
        assertThat(onDestroyCalled).isTrue()
      }

  abstract fun subject(): ApplicationChassis

  open inner class EmptyApplication : Application {
    override suspend fun onCreate(component: Application.KspComponent) {
      val service = component.kspService()
      service.allowProcessing()
      val completeRoundsJob =
          CoroutineScope(coroutines().ioDispatcher()).launch {
            service.onEachRoundStart().collect { service.completeRound() }
          }
      service.allowTermination()
    }

    override suspend fun onDestroy() {}
  }

  open inner class StandardApplication : EmptyApplication() {
    override suspend fun onDestroy() {}
  }

  inner class BlockApplication : StandardApplication() {
    val onCreateStarted = CompletableDeferred<Unit>()
    val allowCreateCompletion = CompletableDeferred<Unit>()
    val onDestroyStarted = CompletableDeferred<Unit>()
    val allowDestroyCompletion = CompletableDeferred<Unit>()

    var kspService: KspService? = null
    var completeRoundsJob: Job? = null

    override suspend fun onCreate(component: Application.KspComponent) {
      kspService = component.kspService()
      onCreateStarted.complete(Unit)
      allowCreateCompletion.await()
      kspService!!.allowProcessing()
      completeRoundsJob =
          CoroutineScope(coroutines().ioDispatcher()).launch {
            kspService!!.onEachRoundStart().collect { kspService!!.completeRound() }
          }
    }

    override suspend fun onDestroy() {
      onDestroyStarted.complete(Unit)
      allowDestroyCompletion.await()
      completeRoundsJob?.cancelAndJoin()
    }
  }

  companion object {
    val TEST_FILE = Source("Generated", "kt", "com.test", "class Generated")
    val TEST_SOURCE =
        Source(
            packageName = "com.test",
            fileName = "File",
            contents = "package com.test\nclass Target")
  }
}
