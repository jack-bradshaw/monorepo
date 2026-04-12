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

  @Inject internal lateinit var dispatcher: IdleableDispatcher

  private var workHandle: Job? = null

  @Volatile private var isWorkActive = false

  @Before
  fun setUp() {
    DaggerIdleableDispatcherTestComponent.create().inject(this)
  }

  override fun subject() = dispatcher

  override fun startLongRunningWork() {
    check(workHandle == null) { "Long running work already started. Cannot start again." }

    // Spin lock keeps the thread active without suspension to prevent idle-state.
    isWorkActive = true
    workHandle =
        CoroutineScope(dispatcher).launch {
          while (isWorkActive && isActive) {
            // Spin
          }
        }
  }

  override suspend fun stopLongRunningWork() {
    isWorkActive = false
    workHandle?.cancel()
    workHandle?.join()

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
