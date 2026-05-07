package com.jackbradshaw.oksp.service

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSNode
import com.jackbradshaw.oksp.model.KspContext
import com.jackbradshaw.oksp.model.LogLevel
import com.jackbradshaw.oksp.model.Resource
import com.jackbradshaw.oksp.model.Source
import kotlinx.coroutines.flow.Flow

// todo need to updae docs to specify that using symbols outside with ocntext is ONLY safe if those
// symbols are passed back to OKSP unmodified, effectively as references, within the same round
// e.g. getting a symbol from withContext and passing to to defer a little while later (so long as
// the round has NOT moved on)

/**
 * Provides asynchronous access to KSP processing.
 *
 * All functions are thread-safe, meaning they can be accessed by multiple threads in parallel;
 * however, external synchronization is necessary to ensure valid state when each call is made. For
 * example, calling [publishDeferred] after [completeRound] but before the next emission from
 * [onEachRoundStart] will fail because there is no active round to publish into, so callers must
 * ensure they coordinate calls.
 *
 * Processing is suspended in a pre-processing state until [allowProcessing] is called to begin
 * processing, and after all rounds have been completed, processing is suspended in a
 * post-processing state until [allowTermination] is called to allow KSP to exit. After starting,
 * KSP moves through multi-round processing as usual; however, when all processing is complete for a
 * round, the round suspends until [completeRound] is called. Until such time, the various
 * publication functions (e.g. [publishSource], [publishError], etc.) and the [withContext]
 * functions can be used. In the time between rounds (i.e. after calling [completeRound] but before
 * [onEachRoundStart] emits), the various publication functions cannot be used and will fail.
 */
interface KspService {

  /**
   * Starts KSP processing.
   *
   * Should only be called once and will fail if called repeatedly.
   */
  suspend fun allowProcessing()

  /**
   * Allows KSP to complete and terminate the process, but does not explicitly trigger termination,
   * as other tasks and systems could still be holding KSP open. This function simply gives the
   * consumer an opportunity to act between the final round and KSP termination then indicate when
   * the work is complete.
   *
   * Can be called repeatedly (idempotent; subsequent calls have no effect). Should only be called
   * after [onFinalRoundComplete] and will fail if called earlier.
   */
  suspend fun allowTermination()

  /**
   * Aborts processing and allows KSP to terminate the process.
   *
   * Can be called at any time, including before and after processing, and calls are idempotent
   * (e.g. repeated calls do not fail).
   *
   * Semantics during an aborted state:
   * - [onEachRoundStart]: Existing flows close immediately. New requests throw
   *   [IllegalStateException].
   * - [onFinalRoundComplete]: Existing flows close immediately. New requests throw
   *   [IllegalStateException].
   * - [completeRound]: Throws [IllegalStateException].
   * - [withContext]: Existing suspended calls resume without evaluating their block. New calls
   *   throw [IllegalStateException].
   * - [publish]: Existing calls throw [kotlinx.coroutines.CancellationException]. New calls throw
   *   [IllegalStateException].
   * - [defer]: Existing/new calls throw [IllegalStateException].
   *
   * This is intended exclusively for test teardown and error-recovery scenarios where KSP execution
   * is suspended awaiting a signal that will never arrive (e.g. [allowProcessing] or
   * [allowTermination]).
   */
  suspend fun abortProcessing()

  /**
   * Creates a new cold flow that emits at the beginning of each round. The flow does not replay
   * past events (i.e. does not emit for past rounds if opened after they have started). The flow
   * remains open until the last emission then closes. If opened after the final round has started,
   * closes immediately without emission.
   */
  suspend fun onEachRoundStart(): Flow<Unit>

  /**
   * Creates a new cold flow that emits at the conclusion of the final round. The flow replays (i.e.
   * emits upon collection if opened after the final round has completed). The flow closes after
   * emission.
   */
  suspend fun onFinalRoundComplete(): Flow<Unit>

  /**
   * Allows the active round to complete.
   *
   * Will fail if called before [allowProcessing], between rounds, or after the final round has
   * completed.
   */
  suspend fun completeRound()

  /**
   * Executes the provided [block] with the [KspContext] for the active round. This context contains
   * both the `Resolver` and the `SymbolProcessorEnvironment` supplied natively by KSP.
   *
   * Will fail if called before [allowProcessing], between rounds, or after the final round has
   * completed.
   */
  suspend fun withContext(block: (KspContext) -> Unit)

  /**
   * Publishes a generated [source] file to the KSP code generator, anchored to the given [anchors].
   *
   * Will fail if called before [allowProcessing], between rounds, or after the final round has
   * completed.
   */
  suspend fun publish(source: Source, anchors: List<KSNode> = emptyList())

  /**
   * Publishes a generated [resource] file to the KSP code generator, anchored to the given
   * [anchors].
   *
   * Will fail if called before [allowProcessing], between rounds, or after the final round has
   * completed.
   */
  suspend fun publish(resource: Resource, anchors: List<KSNode> = emptyList())

  /**
   * Publishes a [message] with a specific [level] to the KSP logger, optionally anchored to
   * [anchor].
   *
   * Will fail if called before [allowProcessing], between rounds, or after the final round has
   * completed.
   */
  suspend fun log(message: String, level: LogLevel?, anchor: KSNode? = null)

  /**
   * Publishes an [error] to the KSP logger, optionally anchored to a specific [anchor] node.
   *
   * Will fail if called before [allowProcessing], between rounds, or after the final round has
   * completed.
   */
  suspend fun fail(error: Throwable, anchor: KSNode? = null)

  /**
   * Publishes an [error] message to the KSP logger, optionally anchored to a specific [anchor]
   * node.
   *
   * Will fail if called before [allowProcessing], between rounds, or after the final round has
   * completed.
   */
  suspend fun fail(error: String, anchor: KSNode? = null)

  /**
   * Defers processing of the given [node] until the next KSP round.
   *
   * Will fail if called before [allowProcessing], between rounds, or after the final round has
   * completed.
   */
  suspend fun defer(node: KSAnnotated)
}
