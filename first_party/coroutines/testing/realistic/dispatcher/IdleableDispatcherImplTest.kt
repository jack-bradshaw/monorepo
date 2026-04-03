package com.jackbradshaw.coroutines.testing.realistic.dispatcher

import com.jackbradshaw.coroutines.CoroutinesDaggerScope
import dagger.Component
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class IdleableDispatcherImplTest : IdleableDispatcherTest() {

  @Inject internal lateinit var factory: IdleableDispatcher.Factory

  private lateinit var dispatcher: IdleableDispatcher
  private var workJob: Job? = null
  @Volatile private var spinLock = false

  @Before
  fun setUp() {
    DaggerIdleableDispatcherTestComponent.create().inject(this)
    dispatcher = factory.create(1)
  }

  override fun subject() = dispatcher

  override fun startLongRunningWork() {
    check(workJob == null) {
      "Long running work already started. Cannot start again."
    }

    spinLock = true
    workJob = CoroutineScope(dispatcher).launch {
      // Holds the underlying thread to prevent idle while work executes.
      while (spinLock && isActive) {
        // Spin
      }
    }
  }

  override suspend fun stopLongRunningWork() {
    spinLock = false
    workJob?.cancel()
    workJob?.join()
    
    // Dispatcher needs time after work is done to decrement counters and report idle.
    while (!dispatcher.isIdle()) {
      delay(1)
    }
  }
}

@CoroutinesDaggerScope
@Component(modules = [IdleableDispatcherModule::class])
interface IdleableDispatcherTestComponent {
  fun inject(test: IdleableDispatcherImplTest)
}
