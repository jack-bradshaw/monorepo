package io.jackbradshaw.queen.foundation.omniform

import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.queen.ui.primitives.Usable.Ui
import io.jackbradshaw.queen.foundation.Destination
import io.jackbradshaw.queen.foundation.Navigator
import io.jackbradshaw.queen.sustainment.operations.StartStopOperation
import io.jackbradshaw.queen.sustainment.operations.startStopOperation
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.sustainment.startstop.StartStop
import io.jackbradshaw.queen.sustainment.startstop.StartStopSimplex
import io.jackbradshaw.queen.sustainment.omnisustainer.factoring.OmniSustainers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Before
import org.junit.After
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CoordinatorImplTest {

  private var omniSustainerHandle: StartStop? = null

  private val omniSustainer = OmniSustainers.create<Operation<StartStop>>(StartStopOperation.WORK_TYPE)
  
  private val coordinator =
      CoordinatorImpl<
          String,
          String,
          Ui,
          FakeDestination,
          FakeNavigator>()

  @Before
  fun setup() = runBlocking {
    omniSustainerHandle = omniSustainer.operation.work()
    omniSustainer.sustain(coordinator)
    delay(ASYNC_DELAY_MS)
  }

  @After
  fun tearDown() = runBlocking {
    omniSustainerHandle!!.stop()
    omniSustainerHandle = null
  }

  @Test
  fun navigatorSet_once_whileSustained_navigatorSustained() = runBlocking {
    omniSustainerHandle!!.start()

    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    delay(ASYNC_DELAY_MS)

    assertThat(navigator.work.isStarted()).isTrue()
  }

  @Test
  fun navigatorSet_once_whileReleased_navigatorNotSustained()= runBlocking {
    omniSustainerHandle!!.stop()

    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    delay(ASYNC_DELAY_MS)

    assertThat(navigator.work.isStarted()).isFalse()
    assertThat(navigator.work.wasStarted()).isFalse()
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

    assertThat(navigator1.work.isStarted()).isFalse()
    assertThat(navigator1.work.wasStarted()).isTrue()
    assertThat(navigator2.work.isStarted()).isTrue()
  }

  @Test
  fun navigatorSet_twice_sustainedBetweenSets_latestNavigatorSustained()= runBlocking {
    //omniSustainerHandle!!.stop()

    val navigator1 = FakeNavigator()
    coordinator.navigator.value = navigator1
    delay(ASYNC_DELAY_MS)
    omniSustainerHandle!!.start()
    val navigator2 = FakeNavigator()
    coordinator.navigator.value = navigator2
    delay(ASYNC_DELAY_MS)

    assertThat(navigator1.work.isStarted()).isFalse()
    assertThat(navigator1.work.wasStarted()).isFalse()
    assertThat(navigator2.work.isStarted()).isTrue()
  }

  @Test
  fun navigatorSet_twice_sustainedAfterSets_latestNavigatorSustained()= runBlocking {
    //omniSustainerHandle!!.stop()

    val navigator1 = FakeNavigator()
    coordinator.navigator.value = navigator1
    delay(ASYNC_DELAY_MS)
    val navigator2 = FakeNavigator()
    coordinator.navigator.value = navigator2
    delay(ASYNC_DELAY_MS)
    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)

    assertThat(navigator1.work.isStarted()).isFalse()
    assertThat(navigator1.work.wasStarted()).isFalse()
    assertThat(navigator2.work.isStarted()).isTrue()
  }

  @Test
  fun navigatorSet_twice_releasedBetweenSets_neitherNavigatorSustained()= runBlocking {
    omniSustainerHandle!!.start()
    
    val navigator1 = FakeNavigator()
    coordinator.navigator.value = navigator1
    delay(ASYNC_DELAY_MS)
    omniSustainerHandle!!.stop()
    val navigator2 = FakeNavigator()
    coordinator.navigator.value = navigator2
    delay(ASYNC_DELAY_MS)

    assertThat(navigator1.work.isStarted()).isFalse()
    assertThat(navigator1.work.wasStarted()).isTrue()
    assertThat(navigator2.work.isStarted()).isFalse()
    assertThat(navigator2.work.wasStarted()).isFalse()
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
    assertThat(destination.work.isStarted()).isTrue()
  }

  @Test
  fun onSignalFromApplication_once_whileReleased_destinationNotUpdated() = runBlocking {
    //omniSustainerHandle!!.stop()
    delay(ASYNC_DELAY_MS)
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination = FakeDestination()
    navigator.nextApplicationTranslation = destination
    delay(ASYNC_DELAY_MS)

    coordinator.onSignalFromApplication("anything")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isNull()
    assertThat(destination.work.isStarted()).isFalse()
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
    assertThat(destination1.work.isStarted()).isFalse()
    assertThat(destination1.work.wasStarted()).isTrue()
    assertThat(destination2.work.isStarted()).isTrue()
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
    assertThat(destination1.work.isStarted()).isFalse()
    assertThat(destination1.work.wasStarted()).isTrue()
    assertThat(destination2.work.isStarted()).isTrue()
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
    assertThat(destination1.work.isStarted()).isFalse()
    assertThat(destination1.work.wasStarted()).isFalse()
    assertThat(destination2.work.isStarted()).isTrue()
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
    omniSustainerHandle!!.stop()
    delay(ASYNC_DELAY_MS)
    val destination2 = FakeDestination()
    navigator.nextApplicationTranslation = destination2
    coordinator.onSignalFromApplication("anything2")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination1)
    assertThat(destination1.work.isStarted()).isFalse()
    assertThat(destination1.work.wasStarted()).isTrue()
    assertThat(destination2.work.isStarted()).isFalse()
    assertThat(destination2.work.wasStarted()).isFalse()
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
    assertThat(destination.work.isStarted()).isTrue()
  }

  @Test
  fun onSignalFromEnvironment_once_whileReleased_destinationNotUpdated() = runBlocking {
    //omniSustainerHandle!!.stop()
    delay(ASYNC_DELAY_MS)
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    val destination = FakeDestination()
    navigator.nextEnvironmentTranslation = destination
    delay(ASYNC_DELAY_MS)

    coordinator.onSignalFromEnvironment("anything")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isNull()
    assertThat(destination.work.isStarted()).isFalse()
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
    assertThat(destination1.work.isStarted()).isFalse()
    assertThat(destination1.work.wasStarted()).isTrue()
    assertThat(destination2.work.isStarted()).isTrue()
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
    assertThat(destination1.work.isStarted()).isFalse()
    assertThat(destination1.work.wasStarted()).isTrue()
    assertThat(destination2.work.isStarted()).isTrue()
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
    assertThat(destination1.work.isStarted()).isFalse()
    assertThat(destination1.work.wasStarted()).isFalse()
    assertThat(destination2.work.isStarted()).isTrue()
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
    omniSustainerHandle!!.stop()
    delay(ASYNC_DELAY_MS)
    val destination2 = FakeDestination()
    navigator.nextEnvironmentTranslation = destination2
    coordinator.onSignalFromEnvironment("anything2")
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination1)
    assertThat(destination1.work.isStarted()).isFalse()
    assertThat(destination1.work.wasStarted()).isTrue()
    assertThat(destination2.work.isStarted()).isFalse()
    assertThat(destination2.work.wasStarted()).isFalse()
  }


  companion object {
    /* Delay to give async processes enough time to complete. */
    private const val ASYNC_DELAY_MS = 500L
  }
}

private class FakeNavigator : Navigator<String, String, Ui, FakeDestination, Operation<StartStop>> {
  
  var nextApplicationTranslation: FakeDestination? = null

  var nextEnvironmentTranslation: FakeDestination? = null
  
  
  /**
   * The work performed by this [Navigator]. Exposed as a public member so tests can make assertions
   * about whether the navigator is sustained.
   */
  val work = StartStopSimplex()

  override fun translateSignalFromApplication(signal: String) = nextApplicationTranslation

  override fun translateSignalFromEnvironment(signal: String) = nextEnvironmentTranslation

  override val operation = startStopOperation { work }
}

private class FakeDestination : Destination<Ui, Operation<StartStop>> {
  /**
   * The work performed by this [Destination]. Exposed as a public member so tests can make
   * assertions about whether the navigator is sustained.
   */
  val work = StartStopSimplex()

  override val ui: Ui by lazy { TODO() }

  override val operation = startStopOperation { work }
}

