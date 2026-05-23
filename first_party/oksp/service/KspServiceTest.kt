package com.jackbradshaw.oksp.service

import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.symbol.KSAnnotated
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.oksp.model.LogLevel
import com.jackbradshaw.oksp.model.Source
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import kotlin.test.assertFailsWith
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

/**
 * Abstract tests that all [KspService] instances should pass.
 *
 * Tests are divided into two overarching sets:
 * 1. E2E tests: Begin with input sources and end when KSP processing is complete. Assertions do not
 *    occur in the middle of the KSP run, and interactions between start and end are limited to
 *    decisions that influence the flow of events (e.g. defer a symbol, generate a file, etc).
 * 2. Midway tests: Drive to a specific suspension point in the full flow and make assertions (e.g.
 *    were symbols deferred from a previous round, do restriction violations correctly exit).
 *
 * Tests in this repository usually don't use mockito in favour of using fakes and real instances;
 * however, this test is an exception, because constructing KSP types directly is infeasible and
 * using the Kale testing library in this context is impractical. Mockito is used safely within
 * isolated scopes strictly targeting Midway constraints.
 */
abstract class KspServiceTest {
  private val latches = ConcurrentHashMap.newKeySet<CountDownLatch>()

  @After
  fun tearDownLatches() {
    latches.forEach { it.countDown() }
  }

  /**
   * Checks that deferring multiple symbols does not trigger a subsequent round if no files were
   * generated (with N sources provided).
   *
   * Input Sources: N R1: Defer N symbols. Expectation: Completes after 1 round.
   */
  @Test
  fun e2e__deferral__multi_sources_defer_batch__halts_natively_after_one_round() =
      runKspTest(setOf(SOURCE_WITH_ANNOTATION, SOURCE_WITHOUT_ANNOTATION)) { roundCount ->
        var d1: KSAnnotated? = null
        var d2: KSAnnotated? = null
        withContext { context ->
          val resolver = context.resolver
          val symbols =
              resolver.getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).toList()
          d1 = symbols[0]
          d2 = symbols[1]
        }
        subject().defer(d1!!)
        subject().defer(d2!!)

        subject().advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(1)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that deferring a single symbol does not trigger a subsequent round if no files were
   * generated (with N sources provided).
   *
   * Input Sources: N R1: Defer 1 symbol. Expectation: Completes after 1 round.
   */
  @Test
  fun e2e__deferral__multi_sources_defer_one__halts_natively_after_one_round() =
      runKspTest(setOf(SOURCE_WITH_ANNOTATION, SOURCE_WITHOUT_ANNOTATION)) { roundCount ->
        lateinit var deferredTarget: KSAnnotated
        withContext { context ->
          val resolver = context.resolver
          deferredTarget =
              resolver.getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).first()
        }
        defer(deferredTarget)

        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(1)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that deferring a single symbol does not trigger a subsequent round if no files were
   * generated (with 1 source provided).
   *
   * Input Sources: 1 R1: Defer 1 symbol. Expectation: Completes after 1 round.
   */
  @Test
  fun e2e__deferral__one_source_defer_input__halts_natively_after_one_round() =
      runKspTest(setOf(SOURCE_WITH_ANNOTATION)) { roundCount ->
        deferTargetAnnotation()

        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(1)
        assertTerminatedWithoutError()
        assertThat(getGeneratedSources()).isEmpty()
      }

  /**
   * Checks that generating a file and deferring a symbol multiple times triggers exactly one
   * additional round after the last file generation.
   *
   * Input Sources: 1 R1: Generate 1 file + Defer 1 symbol. R2: Generate 1 file + Defer 1 symbol.
   * R3: Do nothing. Expectation: Completes after 3 rounds.
   */
  @Test
  fun e2e__deferral_cascade__input_driven_iterative__runs_three_rounds() =
      runKspTest(setOf(SOURCE_WITH_ANNOTATION)) { roundCount ->
        deferTargetAnnotation()
        publish(SOURCE_GENERATED_1, emptyList())
        advanceThroughCurrentRound()

        deferTargetAnnotation()
        publish(createGeneratedSource(2), emptyList())
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(3)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that generating a file and deferring an input symbol within the same round triggers
   * exactly one additional round. Input Sources: 1 R1: Generate 1 file + Defer 1 symbol. R2: Do
   * nothing. Expectation: Completes after 2 rounds.
   */
  @Test
  fun e2e__deferral_cascade__input_driven_standard__runs_two_rounds() =
      runKspTest(setOf(SOURCE_WITH_ANNOTATION)) { roundCount ->
        deferTargetAnnotation()
        publish(SOURCE_GENERATED_1, emptyList())
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(2)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that deferring a symbol in the first round does not prevent generation across multiple
   * subsequent rounds.
   *
   * Input Sources: 1 R1: Generate 1 file + Defer 1 symbol. R2: Generate 1 file. R3: Generate 1
   * file. R4: Do nothing. Expectation: Completes after 4 rounds.
   */
  @Test
  fun e2e__deferral_cascade__input_head_chain_long__runs_four_rounds() =
      runKspTest(setOf(SOURCE_WITH_ANNOTATION)) { roundCount ->
        deferTargetAnnotation()
        publish(SOURCE_GENERATED_1, emptyList())
        advanceThroughCurrentRound()

        publish(createGeneratedSource(2), emptyList())
        advanceThroughCurrentRound()

        publish(createGeneratedSource(3), emptyList())
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(4)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that deferring a symbol in the first round does not prevent generation in the next
   * round.
   *
   * Input Sources: 1 R1: Generate 1 file + Defer 1 symbol. R2: Generate 1 file. R3: Do nothing.
   * Expectation: Completes after 3 rounds.
   */
  @Test
  fun e2e__deferral_cascade__input_head_chain_short__runs_three_rounds() =
      runKspTest(setOf(SOURCE_WITH_ANNOTATION)) { roundCount ->
        deferTargetAnnotation()
        publish(SOURCE_GENERATED_1, emptyList())
        advanceThroughCurrentRound()

        publish(createGeneratedSource(2), emptyList())
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(3)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that deferring a symbol and generating a file in every round triggers exactly one
   * additional round after the final generated file.
   *
   * Input Sources: 1 R1: Generate 1 file + Defer 1 symbol. R2: Generate 1 file + Defer 1 symbol.
   * R3: Generate 1 file + Defer 1 symbol. R4: Do nothing. Expectation: Completes after 4 rounds.
   */
  @Test
  fun e2e__deferral_cascade__input_iterative_deep__runs_four_rounds() =
      runKspTest(setOf(SOURCE_WITH_ANNOTATION)) { roundCount ->
        lateinit var deferredTarget: KSAnnotated

        withContext { context ->
          val resolver = context.resolver
          deferredTarget =
              resolver.getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).single()
        }
        subject().defer(deferredTarget)
        subject().publish(SOURCE_GENERATED_1, emptyList())
        subject().advanceThroughCurrentRound()

        subject().withContext { context ->
          val resolver = context.resolver
          deferredTarget =
              resolver.getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).single()
        }
        subject().defer(deferredTarget)
        subject().publish(createGeneratedSource(2), emptyList())
        subject().advanceThroughCurrentRound()

        subject().withContext { context ->
          val resolver = context.resolver
          deferredTarget =
              resolver.getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).single()
        }
        subject().defer(deferredTarget)
        subject().publish(createGeneratedSource(3), emptyList())
        subject().advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(4)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that deferring a symbol after one round of generation triggers exactly one more round.
   *
   * Input Sources: 0 R1: Generate 1 file. R2: Generate 1 file + Defer 1 symbol. R3: Do nothing.
   * Expectation: Completes after 3 rounds.
   */
  @Test
  fun e2e__deferral_cascade__spontaneous_delayed_r2__runs_three_rounds() =
      runKspTest(emptySet()) { roundCount ->
        publish(SOURCE_GENERATED_2, emptyList())
        advanceThroughCurrentRound()

        deferNewFile()
        publish(SOURCE_GENERATED_1, emptyList())
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(3)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that deferring a symbol at the end of a multi-round generation cascade does not
   * independently trigger an additional round.
   *
   * Input Sources: 0 R1: Generate 1 file. R2: Generate 1 file. R3: Defer 1 symbol. Expectation:
   * Completes after 3 rounds.
   */
  @Test
  fun e2e__deferral_cascade__spontaneous_halt_delayed__aborts_in_r3() =
      runKspTest(emptySet()) { roundCount ->
        publish(SOURCE_GENERATED_2, emptyList())
        advanceThroughCurrentRound()

        publish(SOURCE_GENERATED_1, emptyList())
        advanceThroughCurrentRound()

        deferNewFile()

        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(3)
        assertTerminatedWithoutError()
        assertThat(getGeneratedSources()).hasSize(2)
      }

  /**
   * Checks that deferring a symbol that was spontaneously generated in the previous round does not
   * trigger a third round.
   *
   * Input Sources: 0 R1: Generate 1 file. R2: Defer 1 symbol. Expectation: Completes after 2
   * rounds.
   */
  @Test
  fun e2e__deferral_cascade__spontaneous_halt_r1__aborts_in_r2() =
      runKspTest(emptySet()) { roundCount ->
        publish(SOURCE_GENERATED_2, emptyList())
        advanceThroughCurrentRound()

        deferNewFile()

        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(2)
        assertTerminatedWithoutError()
        assertThat(getGeneratedSources()).hasSize(1)
      }

  /**
   * Checks that generating a file and deferring a symbol sequentially across multiple rounds
   * naturally stops triggering subsequent rounds when file generation ceases.
   *
   * Input Sources: 0 R1: Generate 1 file. R2: Generate 1 file + Defer 1 symbol. R3: Generate 1
   * file + Defer 1 symbol. R4: Do nothing. Expectation: Completes after 4 rounds.
   */
  @Test
  fun e2e__deferral_cascade__spontaneous_iterative_cascade__runs_four_rounds() =
      runKspTest(emptySet()) { roundCount ->
        publish(SOURCE_GENERATED_2, emptyList())
        advanceThroughCurrentRound()

        deferNewFile()
        publish(SOURCE_GENERATED_1, emptyList())
        advanceThroughCurrentRound()

        deferNewFile()
        publish(createGeneratedSource(2), emptyList())

        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(4)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that deferring a symbol after one round of generation triggers exactly one more round
   * and does not prevent generation in the next round.
   *
   * Input Sources: 0 R1: Generate 1 file. R2: Generate 1 file + Defer 1 symbol. R3: Generate 1
   * file. R4: Do nothing. Expectation: Completes after 4 rounds.
   */
  @Test
  fun e2e__deferral_cascade__spontaneous_mid_chain__runs_four_rounds() =
      runKspTest(emptySet()) { roundCount ->
        publish(SOURCE_GENERATED_2, emptyList())
        advanceThroughCurrentRound()

        deferNewFile()
        publish(createGeneratedSource(2), emptyList())
        advanceThroughCurrentRound()

        publish(createGeneratedSource(3), emptyList())
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(4)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that failing with a generic String halts the KSP pipeline and logs the error.
   *
   * Input Sources: N R1: Fail (String). Expectation: Halts pipeline and records the string to the
   * error log output.
   */
  @Test
  fun e2e__error__multi_sources_fail_string__halts_pipeline() =
      runKspUnitTest(setOf(SOURCE_UNANNOTATED, SOURCE_WITHOUT_ANNOTATION)) {
        fail("Foo", null)
        finishExtraneousProcessing()

        assertThat(getLogs().filter { it.first == null && it.second.contains("Foo") }).isNotEmpty()
      }

  /**
   * Checks that publishing a Throwable halts the KSP pipeline and records the exception.
   *
   * Input Sources: 1 R1: Fail (Throwable). Expectation: Exception halts pipeline and records
   * directly to getError().
   */
  @Test
  fun e2e__error__one_source_fail_runtime__halts_pipeline() =
      runKspUnitTest(setOf(SOURCE_UNANNOTATED)) {
        val exception = IllegalStateException("Foo")
        fail(exception)
        finishExtraneousProcessing()

        assertThat(getError()).isEqualTo(exception)
      }

  /**
   * Checks that an error halts the KSP pipeline, suppressing any subsequent rounds that would have
   * been triggered by file generation occurring in the same round.
   *
   * Input Sources: 0 R1: Generate 1 file + Fail (String). Expectation: Halts pipeline after 1
   * round, despite producing a file.
   */
  @Test
  fun e2e__error__zero_sources_generate_and_fail__halts_pipeline() =
      runBlocking<Unit> {
        setupSubject(emptySet())
        val service = subject()
        val roundCount = countRoundsAsync()
        subject().advanceToFirstRound()

        subject().publish(SOURCE_GENERATED_1, emptyList())
        subject().fail("Foo", null)
        finishExtraneousProcessing()

        assertThat(getLogs().filter { it.first == null && it.second.contains("Foo") }).isNotEmpty()
        assertThat(roundCount.await()).isEqualTo(1)
      }

  /**
   * Checks that if an error is thrown within withContext but caught locally, the pipeline
   * continues.
   *
   * Input Sources: 1 R1: withContext throws (caught). Expectation: Completes cleanly without
   * errors.
   */
  @Test
  fun e2e__error__with_resolver_throws_caught_locally__allows_continuation() =
      runKspTest(setOf(SOURCE_UNANNOTATED)) { roundCount ->
        try {
          withContext { throw IllegalStateException("My error") }
        } catch (e: Throwable) {}

        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(1)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that if publish throws natively (e.g. file already exists) but is caught locally, the
   * pipeline continues.
   *
   * Input Sources: 1 R1: publish throws (caught). Expectation: Completes cleanly and advances to
   * R2.
   */
  @Test
  fun e2e__error__publish_throws_caught_locally__allows_continuation() =
      runKspTest(setOf(SOURCE_UNANNOTATED)) { roundCount ->
        publish(SOURCE_GENERATED_1, emptyList())
        try {
          publish(SOURCE_GENERATED_1, emptyList())
        } catch (e: Throwable) {}

        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(2)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that generating a new file sequentially across 5 rounds triggers a new round on each
   * generation.
   *
   * Input Sources: 1 R1-R5: Generate 1 file. R6: Do nothing. Expectation: Completes after 6 rounds.
   */
  @Test
  fun e2e__generation__deep_iterative__runs_six_rounds() =
      runKspTest(setOf(SOURCE_UNANNOTATED)) { roundCount ->
        for (i in 1..5) {
          publish(createGeneratedSource(i), emptyList())
          advanceThroughCurrentRound()
        }

        subject().advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(6)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that generating multiple files in the first round triggers one subsequent round when
   * multiple sources are provided.
   *
   * Input Sources: N R1: Generate N files. R2: Do nothing. Expectation: Completes after 2 rounds.
   */
  @Test
  fun e2e__generation__multi_sources_generate_batch__runs_two_rounds() =
      runKspTest(setOf(SOURCE_UNANNOTATED, SOURCE_WITHOUT_ANNOTATION)) { roundCount ->
        publish(SOURCE_GENERATED_1, emptyList())
        publish(SOURCE_GENERATED_2, emptyList())
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(2)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that generating a single file in the first round triggers one subsequent round when
   * multiple sources are provided.
   *
   * Input Sources: N R1: Generate 1 file. R2: Do nothing. Expectation: Completes after 2 rounds.
   */
  @Test
  fun e2e__generation__multi_sources_generate_one__runs_two_rounds() =
      runKspTest(setOf(SOURCE_UNANNOTATED, SOURCE_WITHOUT_ANNOTATION)) { roundCount ->
        publish(SOURCE_GENERATED_1, emptyList())
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(2)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that generating multiple files in the first round triggers one subsequent round when an
   * initial source exists.
   *
   * Input Sources: 1 R1: Generate N files. R2: Do nothing. Expectation: Completes after 2 rounds.
   */
  @Test
  fun e2e__generation__one_source_generate_batch__runs_two_rounds() =
      runKspTest(setOf(SOURCE_UNANNOTATED)) { roundCount ->
        publish(SOURCE_GENERATED_1, emptyList())
        publish(SOURCE_GENERATED_2, emptyList())
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(2)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that generating a single file in the first round triggers one subsequent round when an
   * initial source exists.
   *
   * Input Sources: 1 R1: Generate 1 file. R2: Do nothing. Expectation: Completes after 2 rounds.
   */
  @Test
  fun e2e__generation__one_source_generate_one__runs_two_rounds() =
      runKspTest(setOf(SOURCE_UNANNOTATED)) { roundCount ->
        publish(SOURCE_GENERATED_1, emptyList())
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(2)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that generating multiple files in the first round triggers one subsequent round, even
   * when zero initial sources exist.
   *
   * Input Sources: 0 R1: Generate N files. R2: Do nothing. Expectation: Completes after 2 rounds.
   */
  @Test
  fun e2e__generation__zero_sources_generate_batch__runs_two_rounds() =
      runKspTest(emptySet()) { roundCount ->
        publish(SOURCE_GENERATED_1, emptyList())
        publish(SOURCE_GENERATED_2, emptyList())
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(2)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that generating a single file in the first round triggers one subsequent round, even
   * when zero initial sources exist.
   *
   * Input Sources: 0 R1: Generate 1 file. R2: Do nothing. Expectation: Completes after 2 rounds.
   */
  @Test
  fun e2e__generation__zero_sources_generate_one__runs_two_rounds() =
      runKspTest(emptySet()) { roundCount ->
        publish(SOURCE_GENERATED_1, emptyList())
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(2)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that recording a WARNING log does not interfere with subsequent rounds triggered by file
   * generation.
   *
   * Input Sources: 0 R1: Generate 1 file + Log WARN. R2: Do nothing. Expectation: Completes after 2
   * rounds.
   */
  @Test
  fun e2e__log__zero_sources_cascading_warn__outputs_cleanly() =
      runKspTest(emptySet()) { roundCount ->
        publish(SOURCE_GENERATED_1, emptyList())
        log("Warn Msg Cascaded", LogLevel.WARNING, null)
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(2)
        val logs = getLogs()
        assertThat(logs.filter { it.first == LogLevel.WARNING }).hasSize(1)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that recording a WARNING log in a secondary round behaves identical to recording it
   * initially.
   *
   * Input Sources: 0 R1: Generate 1 file. R2: Log WARN. Expectation: Completes after 2 rounds.
   */
  @Test
  fun e2e__log__zero_sources_delayed_warn__outputs_cleanly() =
      runKspTest(emptySet()) { roundCount ->
        publish(SOURCE_GENERATED_1, emptyList())
        advanceThroughCurrentRound()

        log("Warn Msg Delayed", LogLevel.WARNING, null)

        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(2)
        val logs = getLogs()
        assertThat(logs.filter { it.first == LogLevel.WARNING }).hasSize(1)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that recording an INFO log does not disrupt normal processing.
   *
   * Input Sources: 0 R1: Log INFO. Expectation: Completes after 1 round and the log is recorded.
   */
  @Test
  fun e2e__log__zero_sources_silent_info__outputs_cleanly() =
      runKspTest(emptySet()) { roundCount ->
        log("Info Msg", LogLevel.INFO, null)
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(1)
        val logs = getLogs()
        assertThat(logs.filter { it.first == LogLevel.INFO }).hasSize(1)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that recording a WARNING log does not disrupt normal processing.
   *
   * Input Sources: 0 R1: Log WARN. Expectation: Completes after 1 round and the log is recorded.
   */
  @Test
  fun e2e__log__zero_sources_silent_warn__outputs_cleanly() =
      runKspTest(emptySet()) { roundCount ->
        log("Warn Msg", LogLevel.WARNING, null)
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(1)
        val logs = getLogs()
        assertThat(logs.filter { it.first == LogLevel.WARNING }).hasSize(1)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that KSP gracefully completes a single round when multiple sources are provided and no
   * actions are taken.
   *
   * Input Sources: N R1: Do nothing. Expectation: Completes after 1 round.
   */
  @Test
  fun e2e__passive__multi_sources__runs_single_round_and_terminates() =
      runKspTest(setOf(SOURCE_UNANNOTATED, SOURCE_WITHOUT_ANNOTATION)) { roundCount ->
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(1)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that KSP gracefully completes a single round when one source is provided and no actions
   * are taken.
   *
   * Input Sources: 1 R1: Do nothing. Expectation: Completes after 1 round.
   */
  @Test
  fun e2e__passive__one_source__runs_single_round_and_terminates() =
      runKspTest(setOf(SOURCE_UNANNOTATED)) { roundCount ->
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(1)
        assertTerminatedWithoutError()
      }

  /**
   * Checks that KSP gracefully completes a single round when zero sources are provided and no
   * actions are taken.
   *
   * Input Sources: 0 R1: Do nothing. Expectation: Completes after 1 round.
   */
  @Test
  fun e2e__passive__zero_sources__runs_single_round_and_terminates() =
      runKspTest(emptySet()) { roundCount ->
        advanceThroughKspExecution()

        assertThat(roundCount.await()).isEqualTo(1)
        assertTerminatedWithoutError()
        assertThat(getGeneratedSources()).isEmpty()
      }

  /**
   * Checks that allowing termination gracefully processes round outputs synchronously when
   * triggered before execution begins.
   *
   * Action: `allowTermination()`. Action: Start 1 round. Expectation: Resolves successfully.
   */
  @Test
  fun e2e__termination__early_termination__sweeps_execution_safely() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()

        subject().allowTermination()

        subject().advanceToFirstRound()
        subject().advanceThroughKspExecution()
        assertTerminatedWithoutError()
      }

  /**
   * Checks that allowing termination securely authorizes shutdown only after completion events
   * propagate entirely.
   *
   * Action: Complete all rounds. Action: `allowTermination()`. Expectation: Resolves successfully.
   */
  @Test
  fun e2e__termination__late_termination__sweeps_execution_safely() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()
        val drainRounds =
            CoroutineScope(testDispatcher()).launch {
              subject().onEachRoundStart().flow.collect { subject().completeRound() }
            }
        taskBarrier().awaitAllIdle()
        subject().allowProcessing()
        drainRounds.join()
        subject().onFinalRoundComplete().flow.first()
        subject().allowTermination()
        finishExtraneousProcessing()

        assertTerminatedWithoutError()
      }

  /**
   * Checks that allowing termination dynamically during active rounds maintains stable execution
   * state without crashing.
   *
   * Action: Start 1 round. Action: `allowTermination()`. Expectation: Resolves successfully.
   */
  @Test
  fun e2e__termination__mid_termination__sweeps_execution_safely() =
      runKspUnitTest(setOf(SOURCE_UNANNOTATED)) {
        allowTermination()

        advanceThroughKspExecution()
        assertTerminatedWithoutError()
      }

  /**
   * Checks that allowing termination multiple times at different execution stages does not disrupt
   * processing cleanly.
   *
   * Action: `allowTermination()` before execution. Action: `allowTermination()` during execution.
   * Action: `allowTermination()` after execution. Expectation: Resolves successfully.
   */
  @Test
  fun e2e__termination__multiple_terminations_at_different_stages__sweeps_execution_safely() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()

        subject().allowTermination()

        subject().advanceToFirstRound()
        subject().allowTermination()

        subject().advanceThroughKspExecution()
        subject().allowTermination()

        assertTerminatedWithoutError()
      }

  /** Checks that completeRound cannot be called after the final round has completed. */
  @Test
  fun partial__functionBoundaryValidation__afterFinalRound__completeRound() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()
        subject().advanceThroughFinalRound()

        val exception = assertFailsWith<IllegalStateException> { subject().completeRound() }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo(
                "Cannot complete round because there is no active round. The final round has already completed.")
      }

  /**
   * Checks that defer cannot be called after the final round has completed.
   *
   * Mockito mocking is usually not used throughout this repository in preference of using real
   * objects by default, and test doubles where necessary; however, this is an exception, because a
   * deferred value is striclty required, and a real instance cannot be safely provided by any other
   * means.
   */
  @Test
  fun partial__functionBoundaryValidation__afterFinalRound__defer() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()
        val deferred = org.mockito.Mockito.mock(KSAnnotated::class.java)
        subject().advanceThroughFinalRound()

        val exception = assertFailsWith<IllegalStateException> { subject().defer(deferred) }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("Cannot invoke defer after the final round.")
      }

  /** Checks that fail cannot be called after the final round has completed. */
  @Test
  fun partial__functionBoundaryValidation__afterFinalRound__failString() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()
        subject().advanceThroughFinalRound()

        val exception = assertFailsWith<IllegalStateException> { subject().fail("Foo", null) }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("Cannot invoke fail after the final round.")
      }

  /** Checks that fail cannot be called after the final round has completed. */
  @Test
  fun partial__functionBoundaryValidation__afterFinalRound__failThrowable() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()
        subject().advanceThroughFinalRound()

        val exception = assertFailsWith<IllegalStateException> { subject().fail(Exception("Foo")) }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("Cannot invoke fail after the final round.")
      }

  /** Checks that log cannot be called after the final round has completed. */
  @Test
  fun partial__functionBoundaryValidation__afterFinalRound__log() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()
        subject().advanceThroughFinalRound()

        val exception =
            assertFailsWith<IllegalStateException> { subject().log("Test", LogLevel.INFO, null) }
        assertThat(exception).hasMessageThat().isEqualTo("Cannot invoke log after the final round.")
      }

  /** Checks that publish cannot be called after the final round has completed. */
  @Test
  fun partial__functionBoundaryValidation__afterFinalRound__publish() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()
        subject().advanceThroughFinalRound()

        val exception =
            assertFailsWith<IllegalStateException> {
              subject().publish(SOURCE_GENERATED_2, emptyList())
            }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("Cannot invoke publish after the final round.")
      }

  /** Checks that withContext cannot be called after the final round has completed. */
  @Test
  fun partial__functionBoundaryValidation__afterFinalRound__withContext() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()
        subject().advanceThroughFinalRound()

        val exception = assertFailsWith<IllegalStateException> { subject().withContext {} }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("Cannot invoke withContext after the final round.")
      }

  /** Checks that allowProcessing cannot be called after processing has started. */
  @Test
  fun partial__functionBoundaryValidation__afterStart__allowProcessing() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()
        subject().allowProcessing()

        val exception = assertFailsWith<IllegalStateException> { subject().allowProcessing() }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("Cannot start processing because it was started already.")
      }

  /** Checks that completeRound cannot be called before processing has started. */
  @Test
  fun partial__functionBoundaryValidation__beforeProcessing__completeRound() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()

        val exception = assertFailsWith<IllegalStateException> { subject().completeRound() }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("Cannot complete round before processing has started.")
      }

  /** Checks that fail cannot be called before processing has started. */
  @Test
  fun partial__functionBoundaryValidation__beforeProcessing__failString() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()

        val exception = assertFailsWith<IllegalStateException> { subject().fail("Foo", null) }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("Cannot invoke fail before processing has started.")
      }

  /** Checks that fail cannot be called before processing has started. */
  @Test
  fun partial__functionBoundaryValidation__beforeProcessing__failThrowable() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()

        val exception = assertFailsWith<IllegalStateException> { subject().fail(Exception("Foo")) }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("Cannot invoke fail before processing has started.")
      }

  /** Checks that log cannot be called before processing has started. */
  @Test
  fun partial__functionBoundaryValidation__beforeProcessing__log() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()

        val exception =
            assertFailsWith<IllegalStateException> { subject().log("Test", LogLevel.INFO, null) }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("Cannot invoke log before processing has started.")
      }

  /** Checks that publish cannot be called before processing has started. */
  @Test
  fun partial__functionBoundaryValidation__beforeProcessing__publish() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()

        val exception =
            assertFailsWith<IllegalStateException> {
              subject().publish(SOURCE_GENERATED_2, emptyList())
            }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("Cannot invoke publish before processing has started.")
      }

  /** Checks that withContext cannot be called before processing has started. */
  @Test
  fun partial__functionBoundaryValidation__beforeProcessing__withContext() =
      runBlocking<Unit> {
        setupSubject(setOf(SOURCE_UNANNOTATED))
        val service = subject()

        val exception = assertFailsWith<IllegalStateException> { subject().withContext {} }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("Cannot invoke withContext before processing has started.")
      }

  /** Checks that pending withContext calls are cancelled on round completion. */
  @Test
  fun partial__resolution__completingRoundCancelsPendingCalls() =
      runKspUnitTest(setOf(SOURCE_UNANNOTATED)) {
        val resolver1blocker = newLatch()
        val resolver1Run = MutableStateFlow(false)
        val resolverJob1 =
            CoroutineScope(testDispatcher()).async {
              withContext {
                resolver1Run.value = true
                resolver1blocker.await()
              }
            }

        var resolver2Run = false
        val resolverJob2 =
            CoroutineScope(testDispatcher()).async { subject().withContext { resolver2Run = true } }

        // Wait to ensure resolverJob1 has started executing and resolverJob2 is queued.
        // We cannot use awaitAllIdle here because resolverJob1 is actively holding the Quinn
        // thread,
        // which intentionally leaves Quinn in a non-idle state.
        resolver1Run.first { it }

        // completeRound() blocks until the active block finishes, then evicts pending blocks.
        // We must launch it concurrently to let resolverJob1 finish.
        val roundJob =
            CoroutineScope(testDispatcher()).launch {
              subject()
                  .completeRound() // completing without releasing the blocker should preempt job2
            }

        // Wait for resolverJob2 to finish! Since completeRound() drops pending blocks, resolverJob2
        // will complete.
        resolverJob2.await()

        // NOW we can release resolver1blocker!
        resolver1blocker.countDown()

        resolverJob1.await()
        roundJob.join()

        // We must abort to cleanly close out the background processes
        subject().allowTermination()
        subject().abortProcessing()

        assertThat(resolver1Run.value).isTrue()
        assertThat(resolver2Run).isFalse()
      }

  /**
   * Checks that the resolver in the first round natively evaluates nothing when there are precisely
   * zero sources.
   */
  @Test
  fun partial__resolution__firstRoundNoSources__evaluatesNothing() =
      runKspUnitTest(emptySet()) {
        withContext { context ->
          val resolver = context.resolver
          assertThat(resolver.getAllFiles().toList()).isEmpty()
        }
      }

  /**
   * Checks that the resolver cleanly maps exactly one input source successfully inside the first
   * round.
   */
  @Test
  fun partial__resolution__firstRoundWithSources__evaluatesSources() =
      runKspUnitTest(setOf(SOURCE_UNANNOTATED)) {
        withContext { context ->
          val resolver = context.resolver
          assertThat(resolver.getAllFiles().toList()).hasSize(1)
        }
      }

  /**
   * Checks that the resolver seamlessly re-loads explicitly deferred symbols deep within the cache
   * into the exact subsequent round.
   */
  @Test
  fun partial__resolution__secondRound__evaluatesDeferredSources() =
      runKspUnitTest(setOf(SOURCE_WITH_ANNOTATION)) {
        deferTargetAnnotation()
        publish(SOURCE_GENERATED_2, emptyList())
        advanceThroughCurrentRound()

        withContext { context ->
          val resolver = context.resolver
          val symbols =
              resolver.getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).toList()
          assertThat(symbols).hasSize(1)
        }
      }

  /**
   * Checks that the active resolver natively catches generated artifacts injected dynamically into
   * Round 2.
   */
  @Test
  fun partial__resolution__secondRound__evaluatesGeneratedSources() =
      runKspUnitTest(emptySet()) {
        publish(SOURCE_GENERATED_1, emptyList())
        advanceThroughCurrentRound()

        withContext { context ->
          val resolver = context.resolver
          val files = resolver.getNewFiles().toList()
          assertThat(files).hasSize(1)
          assertThat(files.first().fileName).isEqualTo("Out1.kt")
        }
      }

  /**
   * Prepares [subject].
   *
   * When this function completes, [subject] must return an instance, and the host KSP process must
   * be ready to start the first round (i.e. calling [KspSerivce.allowProcessing] will function).
   */
  abstract suspend fun setupSubject(sources: Set<Source> = emptySet())

  /**
   * Returns the subject under test.
   *
   * Must not be invoked until [setupSubject] completes. Must return the same instance on each call.
   */
  abstract fun subject(): KspService

  /** The task barrier used to coordinate asynchronous operations in the test environment. */
  abstract fun taskBarrier(): TestingTaskBarrier

  /** The coroutine dispatcher used for test execution. */
  abstract fun testDispatcher(): CoroutineDispatcher

  /** Gets all events logged by [subject]. */
  abstract fun getLogs(): List<Pair<LogLevel?, String>>

  /** Gets all source files generated by [subject]. */
  abstract fun getGeneratedSources(): Set<Source>

  /** Returns the terminal error thrown during processing, or null if execution was successful. */
  abstract fun getError(): Throwable?

  /** Returns true if processing completed successfully without any unhandled exceptions. */
  abstract fun isSuccessful(): Boolean

  /** Asynchronously drains any remaining processor execution rounds. */
  abstract suspend fun finishExtraneousProcessing()

  /**
   * Completes the current round and advances until the next has started (i.e. `onEachRoundStart`
   * has emitted again). Fails if there is no next round. Must only be called after the first round
   * has started.
   */
  private suspend fun KspService.advanceThroughCurrentRound() {
    val round = CoroutineScope(testDispatcher()).async { onEachRoundStart().flow.first() }
    completeRound()
    round.await()
  }

  /**
   * Starts processing and advances through all rounds until the final round is complete (i.e.
   * `onFinalRoundComplete` has emitted). Must only be called before processing has started.
   */
  private suspend fun KspService.advanceThroughFinalRound() {
    val drain =
        CoroutineScope(testDispatcher()).launch {
          onEachRoundStart().flow.collect { completeRound() }
        }
    taskBarrier().awaitAllIdle()
    allowProcessing()
    drain.join()
    onFinalRoundComplete().flow.first()
  }

  /**
   * Completes the current round, advances through all remaining rounds, allows termination, and
   * advances through extraneous processing. Must only be called after the first round has started.
   */
  private suspend fun KspService.advanceThroughKspExecution() {
    val drainRounds =
        CoroutineScope(testDispatcher()).launch {
          onEachRoundStart().flow.collect { completeRound() }
        }
    completeRound()
    drainRounds.join()
    allowTermination()
    finishExtraneousProcessing()
  }

  /** Asserts that processing completed successfully and no terminal errors were thrown. */
  private fun assertTerminatedWithoutError() {
    if (!isSuccessful()) {}
    assertThat(getError()).isNull()
    assertThat(isSuccessful()).isTrue()
  }

  /**
   * Configures [subject] to target [sources],begins counting rounds asynchronously, and advances
   * into the first round. The final round count is exposed as the returneed Deferred<Int> and is
   * available when processing completes.
   */
  private suspend fun beginProcessingWithRoundCount(
      sources: Set<Source>
  ): kotlinx.coroutines.Deferred<Int> {
    setupSubject(sources)
    val roundCount = countRoundsAsync()
    taskBarrier().awaitAllIdle()
    subject().advanceToFirstRound()
    return roundCount
  }

  /** Configures [subject] to target [sources] and advances into the first round. */
  private suspend fun beginProcessingWithoutRoundCount(sources: Set<Source>) {
    setupSubject(sources)
    taskBarrier().awaitAllIdle()
    subject().advanceToFirstRound()
  }

  /**
   * Defer a newly generated file symbol directly from the resolver. Assumes exactly one new file
   * has been generated in the current round.
   */
  private suspend fun KspService.deferNewFile() {
    lateinit var deferredTarget: KSAnnotated
    withContext { context ->
      val resolver = context.resolver
      deferredTarget = resolver.getNewFiles().single()
    }
    defer(deferredTarget!!)
  }

  /**
   * Defer a target symbol annotated with [TEST_ANNOTATION_NAME_FULLY_QUALIFIED] directly from the
   * resolver. Assumes exactly one such symbol exists.
   */
  private suspend fun KspService.deferTargetAnnotation() {
    lateinit var deferredTarget: KSAnnotated
    withContext { context ->
      val resolver = context.resolver
      deferredTarget =
          resolver.getSymbolsWithAnnotation(TEST_ANNOTATION_NAME_FULLY_QUALIFIED).single()
    }
    defer(deferredTarget!!)
  }

  private fun newLatch(): CountDownLatch {
    val latch = java.util.concurrent.CountDownLatch(1)
    latches.add(latch)
    return latch
  }

  /**
   * Starts processing and advances until the first round has started (i.e. `onEachRoundStart` has
   * emitted). Fails if there is no first round. Must only be called before processing has started.
   */
  private suspend fun KspService.advanceToFirstRound() {
    val round = CoroutineScope(testDispatcher()).async { onEachRoundStart().flow.first() }
    taskBarrier().awaitAllIdle()
    allowProcessing()
    round.await()
  }

  /** Asynchronously counts the number of rounds emitted by [onEachRoundStart]. */
  private fun countRoundsAsync(): kotlinx.coroutines.Deferred<Int> =
      CoroutineScope(testDispatcher()).async {
        var count = 0
        subject().onEachRoundStart().flow.collect { count++ }
        count
      }

  /** Helper function to reduce boilerplate across KspService tests that track rounds. */
  private fun runKspTest(
      sources: Set<Source> = emptySet(),
      block: suspend KspService.(kotlinx.coroutines.Deferred<Int>) -> Unit
  ) =
      runBlocking<Unit> {
        val roundCount = beginProcessingWithRoundCount(sources)
        subject().block(roundCount)
      }

  /** Helper function to reduce boilerplate across KspService tests that do not track rounds. */
  private fun runKspUnitTest(
      sources: Set<Source> = emptySet(),
      block: suspend KspService.() -> Unit
  ) =
      runBlocking<Unit> {
        beginProcessingWithoutRoundCount(sources)
        subject().block()
      }

  private companion object {
    /** The default package to use for KSP testing. */
    private const val TEST_PACKAGE = "com.jackbradshaw"

    /** Simple name of an annotation used in some test sources. */
    private const val TEST_ANNOTATION_NAME = "TargetAnnotation"

    /** Fully qualified name of an annotation used in some test sources. */
    private const val TEST_ANNOTATION_NAME_FULLY_QUALIFIED = "$TEST_PACKAGE.$TEST_ANNOTATION_NAME"

    /** Basic source file with no annotations. */
    private val SOURCE_UNANNOTATED =
        Source(packageName = TEST_PACKAGE, fileName = "File", contents = "class Target")

    /**
     * Source file that defines the target annotation and contains a class with the annotation
     * applied.
     */
    private val SOURCE_WITH_ANNOTATION =
        Source(
            packageName = TEST_PACKAGE,
            fileName = "File",
            contents =
                "package $TEST_PACKAGE\n\nannotation class $TEST_ANNOTATION_NAME\n\n@$TEST_ANNOTATION_NAME\nclass Target")

    /**
     * Source file that contains a class with the target annotation applied (but no annotation
     * definition).
     */
    private val SOURCE_WITHOUT_ANNOTATION =
        Source(
            packageName = TEST_PACKAGE,
            fileName = "ExtraFile",
            contents = "package $TEST_PACKAGE\n\n@$TEST_ANNOTATION_NAME\nclass ExtraTarget")

    /** Source file representing a generated output. */
    private val SOURCE_GENERATED_1 = createGeneratedSource(1)

    /** Source file representing a generated output. */
    private val SOURCE_GENERATED_2 =
        createGeneratedSource(2, TEST_PACKAGE, "package $TEST_PACKAGE\nclass Out2")

    /** Creates a source file representing a generated output. */
    private fun createGeneratedSource(
        index: Int,
        packageName: String = "com.out",
        contents: String = "class Out$index"
    ) =
        Source(
            packageName = packageName,
            fileName = "Out$index",
            extension = "kt",
            contents = contents)
  }
}
