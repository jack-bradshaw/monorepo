package com.jackbradshaw.kale.resolver.chassis

import com.google.devtools.ksp.processing.Resolver
import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.kale.model.Source
import com.jackbradshaw.kale.model.Versions

/**
 * Runs Kotlin Symbol Processing (KSP) and provides access to the [Resolver]s produced by the run.
 */
interface ResolverChassis : ObservableClosable {

  /**
   * Runs KSP on [sources] in a KSP environment configured with [versions] and [options], and
   * returns a [ResolverHarness] that provides access to the run's [Resolver]s.
   */
  suspend fun open(
      sources: Set<Source>,
      versions: Versions = Versions(),
      options: Map<String, String> = emptyMap()
  ): ResolverHarness

  /** Convenience function to invoke [open] with a single [source]. */
  suspend fun open(
      source: Source,
      versions: Versions = Versions(),
      options: Map<String, String> = emptyMap()
  ): ResolverHarness

  /** Provides access to [Resolver]s of a KSP run. */
  interface ResolverHarness : ObservableClosable {
    /**
     * Invokes [block] in the context of the associated KSP run, passing in the run's present
     * [Resolver], and suspending until the block exits or the harness is closed.
     *
     * If invoked repeatedly, the same [Resolver] instance is not guaranteed to be provided to each
     * call.
     *
     * Throws an [IllegalStateException] if invoked after closure. If closure occurs after this
     * method is invoked but before [block] is evaluated, no exception is raised, and [block] is
     * discarded without evaluation.
     *
     * WARNING: Using the supplied [Resolver] (or any other KSP types it provides) outside [block]
     * is error prone and unsupported.
     */
    suspend fun withResolver(block: (Resolver) -> Unit)
  }
}
