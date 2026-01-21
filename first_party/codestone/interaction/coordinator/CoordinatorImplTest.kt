package com.jackbradshaw.codestone.interaction.coordinator

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.codestone.interaction.usable.Usable.Ui
import com.jackbradshaw.codestone.interaction.destination.Destination
import com.jackbradshaw.codestone.interaction.navigator.Navigator
import com.jackbradshaw.codestone.lifecycle.platforms.startstop.StartStopOperation
import com.jackbradshaw.codestone.lifecycle.platforms.startstop.startStopWork
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.startstop.StartStopImpl
import com.jackbradshaw.codestone.lifecycle.startstop.ExecutionState
import com.jackbradshaw.codestone.lifecycle.orchestrator.factory.WorkOrchestrators
import com.jackbradshaw.codestone.lifecycle.worker.Worker
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlinx.coroutines.delay
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Before
import org.junit.After
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CoordinatorImplTest {

  private var omniSustainerHandle: StartStop<*, *>? = null

  private val omniSustainer = WorkOrchestrators.create<Work<StartStop<StartStop<*, *>, Throwable>>>(StartStopOperation.WORK_TYPE, GlobalScope)
  
  private val coordinator =
      CoordinatorImpl<
          String,
          String,
          Ui,
          FakeDestination,
          FakeNavigator>()

  @Before
  fun setup() = runBlocking {
    omniSustainerHandle = omniSustainer.work.handle
    omniSustainer.orchestrate(coordinator)
    delay(ASYNC_DELAY_MS)
  }

  @After
  fun tearDown() = runBlocking {
    omniSustainerHandle!!.abort()
    omniSustainerHandle = null
  }

  @Test
  fun navigatorSet_once_whileSustained_navigatorSustained() = runBlocking {
    omniSustainerHandle!!.start()

    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    delay(ASYNC_DELAY_MS)

    assertThat(navigator.work.handle.state.value.isPostStart).isTrue()
  }

  @Test
  fun navigatorSet_once_whileReleased_navigatorNotSustained()= runBlocking {
    omniSustainerHandle!!.abort()

    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    delay(ASYNC_DELAY_MS)

    assertThat(navigator.work.handle.state.value.isPostStart).isFalse()
    assertThat(navigator.work.handle.state.value.isPostStop).isFalse()
  }

  @Test
  fun navigatorSet_twice_sustainedBeforeBoth_latestNavigatorSustained()= runBlocking {
    omniSustainerHandle!!.start()

    val navigator1 = FakeNavigator()
    coordinator.navigator.value = navigator1
    delay(ASYNC_DELAY_MS)
    val navigator2 = FakeNavigator()
    coordinator.navigator.value = navigator2
    delay(ASYNC_DELAY_MS)

    assertThat(navigator1.work.handle.state.value.isPostStart).isFalse()
    assertThat(navigator1.work.handle.state.value.isPostStop).isTrue()
    assertThat(navigator2.work.handle.state.value.isPostStart).isTrue()
  }

  @Test
  fun navigatorSet_twice_sustainedBetweenSets_latestNavigatorSustained()= runBlocking {
    //omniSustainerHandle!!.abort()

    val navigator1 = FakeNavigator()
    coordinator.navigator.value = navigator1
    delay(ASYNC_DELAY_MS)
    omniSustainerHandle!!.start()
    val navigator2 = FakeNavigator()
    coordinator.navigator.value = navigator2
    delay(ASYNC_DELAY_MS)

    assertThat(navigator1.work.handle.state.value.isPostStart).isFalse()
    assertThat(navigator1.work.handle.state.value.isPostStop).isFalse()
    assertThat(navigator2.work.handle.state.value.isPostStart).isTrue()
  }

  @Test
  fun navigatorSet_twice_sustainedAfterSets_latestNavigatorSustained()= runBlocking {
    //omniSustainerHandle!!.abort()

    val navigator1 = FakeNavigator()
    coordinator.navigator.value = navigator1
    delay(ASYNC_DELAY_MS)
    val navigator2 = FakeNavigator()
    coordinator.navigator.value = navigator2
    delay(ASYNC_DELAY_MS)
    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)

    assertThat(navigator1.work.handle.state.value.isPostStart).isFalse()
    assertThat(navigator1.work.handle.state.value.isPostStop).isFalse()
    assertThat(navigator2.work.handle.state.value.isPostStart).isTrue()
  }

  @Test
  fun navigatorSet_twice_releasedBetweenSets_neitherNavigatorSustained()= runBlocking {
    omniSustainerHandle!!.start()
    
    val navigator1 = FakeNavigator()
    coordinator.navigator.value = navigator1
    delay(ASYNC_DELAY_MS)
    omniSustainerHandle!!.abort()
    val navigator2 = FakeNavigator()
    coordinator.navigator.value = navigator2
    delay(ASYNC_DELAY_MS)

    assertThat(navigator1.work.handle.state.value.isPostStart).isFalse()
    assertThat(navigator1.work.handle.state.value.isPostStop).isTrue()
    assertThat(navigator2.work.handle.state.value.isPostStart).isFalse()
    assertThat(navigator2.work.handle.state.value.isPostStop).isFalse()
  }

  @Test
  fun onSignalFromApplication_once_whileSustained_destinationUpdated() = runBlocking {
    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination = FakeDestination()
    navigator.nextApplicationTranslation = destination
    delay(ASYNC_DELAY_MS)

    coordinator.onSignalFromApplication("anything")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination)
    assertThat(destination.work.handle.state.value.isPostStart).isTrue()
  }

  @Test
  fun onSignalFromApplication_once_whileReleased_destinationNotUpdated() = runBlocking {
    //omniSustainerHandle!!.abort()
    delay(ASYNC_DELAY_MS)
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination = FakeDestination()
    navigator.nextApplicationTranslation = destination
    delay(ASYNC_DELAY_MS)

    coordinator.onSignalFromApplication("anything")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isNull()
    assertThat(destination.work.handle.state.value.isPostStart).isFalse()
  }

  @Test
  fun onSignalsFromApplication_twice_sustainedBeforeBoth_destinationUpdatedToSecond() = runBlocking {
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination1 = FakeDestination()
    navigator.nextApplicationTranslation = destination1

    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)
    coordinator.onSignalFromApplication("anything1")
    delay(ASYNC_DELAY_MS)
    val destination2 = FakeDestination()
    navigator.nextApplicationTranslation = destination2
    coordinator.onSignalFromApplication("anything2")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination2)
    assertThat(destination1.work.handle.state.value.isPostStart).isFalse()
    assertThat(destination1.work.handle.state.value.isPostStop).isTrue()
    assertThat(destination2.work.handle.state.value.isPostStart).isTrue()
  }

  @Test
  fun onSignalsFromApplication_twice_sustainedBetweenSignals_destinationUpdatedToSecond() = runBlocking {
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination1 = FakeDestination()
    navigator.nextApplicationTranslation = destination1
    delay(ASYNC_DELAY_MS)

    coordinator.onSignalFromApplication("anything1")
    delay(ASYNC_DELAY_MS)
    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)
    val destination2 = FakeDestination()
    navigator.nextApplicationTranslation = destination2
    coordinator.onSignalFromApplication("anything2")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination2)
    assertThat(destination1.work.handle.state.value.isPostStart).isFalse()
    assertThat(destination1.work.handle.state.value.isPostStop).isTrue()
    assertThat(destination2.work.handle.state.value.isPostStart).isTrue()
  }

  @Test
  fun onSignalsFromApplication_twice_sustainedAfterSignals_destinationNotUpdated() = runBlocking {
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination1 = FakeDestination()
    navigator.nextApplicationTranslation = destination1
    delay(ASYNC_DELAY_MS)

    coordinator.onSignalFromApplication("anything1")
    delay(ASYNC_DELAY_MS)
    val destination2 = FakeDestination()
    navigator.nextApplicationTranslation = destination2
    coordinator.onSignalFromApplication("anything2")
    delay(ASYNC_DELAY_MS)
    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination2)
    assertThat(destination1.work.handle.state.value.isPostStart).isFalse()
    assertThat(destination1.work.handle.state.value.isPostStop).isFalse()
    assertThat(destination2.work.handle.state.value.isPostStart).isTrue()
  }

  @Test
  fun onSignalsFromApplication_twice_releasedBetweenSignals_destinationUpdatedToFirst() = runBlocking {
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination1 = FakeDestination()
    navigator.nextApplicationTranslation = destination1
    delay(ASYNC_DELAY_MS)

    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)
    coordinator.onSignalFromApplication("anything1")
    delay(ASYNC_DELAY_MS)
    omniSustainerHandle!!.abort()
    delay(ASYNC_DELAY_MS)
    val destination2 = FakeDestination()
    navigator.nextApplicationTranslation = destination2
    coordinator.onSignalFromApplication("anything2")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination1)
    assertThat(destination1.work.handle.state.value.isPostStart).isFalse()
    assertThat(destination1.work.handle.state.value.isPostStop).isTrue()
    assertThat(destination2.work.handle.state.value.isPostStart).isFalse()
    assertThat(destination2.work.handle.state.value.isPostStop).isFalse()
  }

  @Test
  fun onSignalFromEnvironment_once_whileSustained_destinationUpdated() = runBlocking {
    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination = FakeDestination()
    navigator.nextEnvironmentTranslation = destination
    delay(ASYNC_DELAY_MS)

    coordinator.onSignalFromEnvironment("anything")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination)
    assertThat(destination.work.handle.state.value.isPostStart).isTrue()
  }

  @Test
  fun onSignalFromEnvironment_once_whileReleased_destinationNotUpdated() = runBlocking {
    //omniSustainerHandle!!.abort()
    delay(ASYNC_DELAY_MS)
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination = FakeDestination()
    navigator.nextEnvironmentTranslation = destination
    delay(ASYNC_DELAY_MS)

    coordinator.onSignalFromEnvironment("anything")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isNull()
    assertThat(destination.work.handle.state.value.isPostStart).isFalse()
  }

  @Test
  fun onSignalsFromEnvironment_twice_sustainedBeforeBoth_destinationUpdatedToSecond() = runBlocking {
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination1 = FakeDestination()
    navigator.nextEnvironmentTranslation = destination1

    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)
    coordinator.onSignalFromEnvironment("anything1")
    delay(ASYNC_DELAY_MS)
    val destination2 = FakeDestination()
    navigator.nextEnvironmentTranslation = destination2
    coordinator.onSignalFromEnvironment("anything2")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination2)
    assertThat(destination1.work.handle.state.value.isPostStart).isFalse()
    assertThat(destination1.work.handle.state.value.isPostStop).isTrue()
    assertThat(destination2.work.handle.state.value.isPostStart).isTrue()
  }

  @Test
  fun onSignalsFromEnvironment_twice_sustainedBetweenSignals_destinationUpdatedToSecond() = runBlocking {
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination1 = FakeDestination()
    navigator.nextEnvironmentTranslation = destination1
    delay(ASYNC_DELAY_MS)

    coordinator.onSignalFromEnvironment("anything1")
    delay(ASYNC_DELAY_MS)
    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)
    val destination2 = FakeDestination()
    navigator.nextEnvironmentTranslation = destination2
    coordinator.onSignalFromEnvironment("anything2")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination2)
    assertThat(destination1.work.handle.state.value.isPostStart).isFalse()
    assertThat(destination1.work.handle.state.value.isPostStop).isTrue()
    assertThat(destination2.work.handle.state.value.isPostStart).isTrue()
  }

  @Test
  fun onSignalsFromEnvironment_twice_sustainedAfterSignals_destinationNotUpdated() = runBlocking {
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination1 = FakeDestination()
    navigator.nextEnvironmentTranslation = destination1
    delay(ASYNC_DELAY_MS)

    coordinator.onSignalFromEnvironment("anything1")
    delay(ASYNC_DELAY_MS)
    val destination2 = FakeDestination()
    navigator.nextEnvironmentTranslation = destination2
    coordinator.onSignalFromEnvironment("anything2")
    delay(ASYNC_DELAY_MS)
    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination2)
    assertThat(destination1.work.handle.state.value.isPostStart).isFalse()
    assertThat(destination1.work.handle.state.value.isPostStop).isFalse()
    assertThat(destination2.work.handle.state.value.isPostStart).isTrue()
  }

  @Test
  fun onSignalsFromEnvironment_twice_releasedBetweenSignals_destinationUpdatedToFirst() = runBlocking {
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination1 = FakeDestination()
    navigator.nextEnvironmentTranslation = destination1
    delay(ASYNC_DELAY_MS)

    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)
    coordinator.onSignalFromEnvironment("anything1")
    delay(ASYNC_DELAY_MS)
    omniSustainerHandle!!.abort()
    delay(ASYNC_DELAY_MS)
    val destination2 = FakeDestination()
    navigator.nextEnvironmentTranslation = destination2
    coordinator.onSignalFromEnvironment("anything2")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination1)
    assertThat(destination1.work.handle.state.value.isPostStart).isFalse()
    assertThat(destination1.work.handle.state.value.isPostStop).isTrue()
    assertThat(destination2.work.handle.state.value.isPostStart).isFalse()
    assertThat(destination2.work.handle.state.value.isPostStop).isFalse()
  }


  companion object {
    /* Delay to give async processes enough time to complete. */
    private const val ASYNC_DELAY_MS = 500L
  }
}

private class FakeNavigator : Navigator<String, String, Ui, FakeDestination, Work<StartStop<StartStop<*, *>, Throwable>>>, Worker<Work<StartStop<StartStop<*, *>, Throwable>>> {
  
  var nextApplicationTranslation: FakeDestination? = null

  var nextEnvironmentTranslation: FakeDestination? = null
  
  
  /**
   * The work performed by this [Navigator]. Exposed as a public member so tests can make assertions
   * about whether the navigator is sustained.
   */
  @Suppress("UNCHECKED_CAST")
  override val work: Work<StartStop<StartStop<*, *>, Throwable>> = 
    startStopWork(StartStopImpl<Unit, Throwable>()) as Work<StartStop<StartStop<*, *>, Throwable>>

  override fun translateSignalFromApplication(signal: String) = nextApplicationTranslation

  override fun translateSignalFromEnvironment(signal: String) = nextEnvironmentTranslation
}

private class FakeDestination : Destination<Ui, Work<StartStop<StartStop<*, *>, Throwable>>>, Worker<Work<StartStop<StartStop<*, *>, Throwable>>> {
  /**
   * The work performed by this [Destination]. Exposed as a public member so tests can make
   * assertions about whether the navigator is sustained.
   */
  @Suppress("UNCHECKED_CAST")
  override val work: Work<StartStop<StartStop<*, *>, Throwable>> = 
    startStopWork(StartStopImpl<Unit, Throwable>()) as Work<StartStop<StartStop<*, *>, Throwable>>

  override val ui: Ui by lazy { TODO() }
}

