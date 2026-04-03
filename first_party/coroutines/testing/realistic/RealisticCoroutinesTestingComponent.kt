package com.jackbradshaw.coroutines.testing.realistic

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.testing.Coroutines

/**
 * A [CoroutinesComponent] backed by a realistic multi-threaded execution system.
 * 
 * The underlying execution system uses executors backed by JVM thread pools for true concurrency
 * and asynchronicity. It uses threads that are completely decoupled from the main test thread,
 * meaning launched work does not block test thread by default. Blocking the test thread while
 * background work completes is possible via the provided [taskBarrier]. Virtual time advancement
 * is not supported in this environment due to the realistic nature of the execution system.
 * 
 * This component is ideal for testing systems that rely on a multi-threaded environment and
 * would experience thread starvation and deadlock if tested in a single-threaded test environment. 
 */
interface RealisticCoroutinesTestingComponent : CoroutinesComponent {
  @Coroutines fun taskBarrier(): TestingTaskBarrier
}
