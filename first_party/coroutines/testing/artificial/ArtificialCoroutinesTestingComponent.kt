package com.jackbradshaw.coroutines.testing.artificial

import com.jackbradshaw.chronosphere.testingtaskdriver.TestingTaskDriver
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.testing.Coroutines

/**
 * A [CoroutinesComponent] that uses artificial execution systems (i.e. single-threaded event loops)
 * and provides access to a [taskDriver] to control them.
 * 
 * The underlying execution system uses the main test thread (via the standard test scheduler) for
 * false concurrency and asynchronicity. It runs all work on the main test thread and is not
 * actually multithreaded; however, asynchronous work does not block the test thread unless a
 * deadlock occurs. Advancing virtual time is possible via the provided [taskDriver]. Blocking the
 * test thread while background work completes is not supported in this environment as the test
 * thread is the only thread.
 * 
 * * This component is ideal for testing systems that do not require a multi-threaded test
 * environment or explicitly require granular time advancement; however, care must be taken to avoid
 * deadlocks since the environment is ultimately single-threaded.
 */
interface ArtificialCoroutinesTestingComponent : CoroutinesComponent {
  @Coroutines fun taskDriver(): TestingTaskDriver
}
