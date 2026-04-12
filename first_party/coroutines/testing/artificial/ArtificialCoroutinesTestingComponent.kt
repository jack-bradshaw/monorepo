package com.jackbradshaw.coroutines.testing.artificial

import com.jackbradshaw.chronosphere.testingtaskdriver.TestingTaskDriver
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.testing.Coroutines

/**
 * A [CoroutinesComponent] that uses artificial execution systems (i.e. single-threaded event loops)
 * and provides access to a [taskDriver] to control them.
 *
 * The underlying execution system runs all work on the main test thread (via the standard test
 * scheduler) so is not actually multithreaded; however, asynchronous work is possible and does not
 * block the test thread unless a deadlock occurs. Advancing virtual time is possible via the
 * provided [taskDriver]. Blocking the test thread while background work completes is not supported
 * with an artificial execution system as only one thread exists.
 *
 * This component is ideal for testing systems that do not require a multi-threading or explicit
 * granular time advancement; however, care must be taken to avoid deadlocks since the system is
 * ultimately single-threaded.
 */
interface ArtificialCoroutinesTestingComponent : CoroutinesComponent {
  @Coroutines fun taskDriver(): TestingTaskDriver
}
