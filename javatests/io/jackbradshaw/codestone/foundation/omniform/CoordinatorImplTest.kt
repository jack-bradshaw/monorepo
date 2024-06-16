package io.jackbradshaw.codestone.foundation.omniform

import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.codestone.ui.primitives.Usable.Ui
import io.jackbradshaw.codestone.foundation.Destination
import io.jackbradshaw.codestone.foundation.Navigator
import io.jackbradshaw.codestone.sustainment.operations.StartStopOperation
import io.jackbradshaw.codestone.sustainment.operations.startStopOperation
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
import io.jackbradshaw.codestone.sustainment.startstop.StartStopSimplex
import io.jackbradshaw.codestone.sustainment.omnisustainer.factoring.OmniSustainers
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
  
  private var uiLaunchCount = 0
  
  private val coordinator =
      CoordinatorImpl<
          String,
          String,
          Ui,
          FakeDestination,
          FakeNavigator>(initialNavigator = null, uiLauncher = { uiLaunchCount++ } )

  @Before
  fun setup() = runBlocking {
    omniSustainerHandle = omniSustainer.operation.work()
    omniSustainerHandle!!.start()
    delay(ASYNC_DELAY_MS)
  }

  @After
  fun tearDown() = runBlocking {
    omniSustainerHandle?.stop()
    omniSustainerHandle = null
  }

  @Test
  fun initialize_withInitialNavigator_initialNavigatorBecomesNavigator() = runBlocking {
    val navigator = FakeNavigator()
    
    val coordinator = CoordinatorImpl<
          String,
          String,
          Ui,
          FakeDestination,
          FakeNavigator>(initialNavigator = navigator, uiLauncher = {})

    assertThat(coordinator.navigator.value === navigator).isTrue()
  }

  @Test
  fun initialize_withoutInitialNavigator_navigatorIsEmpty() = runBlocking {
    val coordinator = CoordinatorImpl<
          String,
          String,
          Ui,
          FakeDestination,
          FakeNavigator>(initialNavigator = null, uiLauncher = {})

    assertThat(coordinator.navigator.value).isNull()
  }

  @Test
  fun navigatorSet_whileSustained_withNavigator_firstNavigatorBecomesReleased() = runBlocking {
    omniSustainer.sustain(coordinator)
    delay(ASYNC_DELAY_MS)
    val firstNavigator = FakeNavigator()
    coordinator.navigator.value = firstNavigator
    delay(ASYNC_DELAY_MS)

    coordinator.navigator.value = FakeNavigator()
    delay(ASYNC_DELAY_MS)

    assertThat(firstNavigator.work.isStopped()).isTrue()
  }

  @Test
  fun navigatorSet_whileSustained_withNavigator_replacementNavigatorBecomesSustained() = runBlocking {
    omniSustainer.sustain(coordinator)
    delay(ASYNC_DELAY_MS)
    coordinator.navigator.value = FakeNavigator()
    delay(ASYNC_DELAY_MS)

    val replacementNavigator = FakeNavigator()
    coordinator.navigator.value = replacementNavigator
    delay(ASYNC_DELAY_MS)


    assertThat(replacementNavigator.work.isStarted()).isTrue()
  }

  @Test
  fun navigatorSet_whileSustained_withoutNavigator_navigatorBecomesSustained() = runBlocking {
    omniSustainer.sustain(coordinator)
    delay(ASYNC_DELAY_MS)

    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    delay(ASYNC_DELAY_MS)

    assertThat(navigator.work.isStarted()).isTrue()
  }

  @Test
  fun navigatorSet_whileSustained_withDestination_destinationUnchanged() = runBlocking {
    omniSustainer.sustain(coordinator)
    delay(ASYNC_DELAY_MS)
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    delay(ASYNC_DELAY_MS)
    val destination = FakeDestination()
    navigator.nextApplicationSignalTranslation = destination
    coordinator.acceptSignalFromApplication("")
    delay(ASYNC_DELAY_MS)

    coordinator.navigator.value = FakeNavigator()
    delay(ASYNC_DELAY_MS)

    assertThat(coordinator.destination.value).isEqualTo(destination)
  }

  @Test
  fun navigatorSet_whileSustained_withoutDestination_noFailuresOccur() = runBlocking {
    omniSustainer.sustain(coordinator)
    delay(ASYNC_DELAY_MS)

    coordinator.navigator.value = FakeNavigator()
  }

  @Test
  fun navigatorSet_whileReleased_withNavigator_replacementNavigatorDoesNotBecomeSustained() = runBlocking {
    val firstNavigator = FakeNavigator()
    coordinator.navigator.value = firstNavigator
    delay(ASYNC_DELAY_MS)

    val replacementNavigator = FakeNavigator()
    coordinator.navigator.value = replacementNavigator
    delay(ASYNC_DELAY_MS)

    assertThat(replacementNavigator.work.isStarted()).isFalse()
    assertThat(replacementNavigator.work.wasStarted()).isFalse()
  }

  @Test
  fun navigatorSet_whileReleased_withoutNavigator_replacementNavigatorDoesNotBecomeSustained() = runBlocking {
    val navigator = FakeNavigator()
    coordinator.navigator.value = navigator
    delay(ASYNC_DELAY_MS)

    assertThat(navigator.work.isStarted()).isFalse()
    assertThat(navigator.work.wasStarted()).isFalse()
  }

  @Test
  fun navigatorSet_whileReleased_withDestination_destinationUnchanged() = runBlocking {
    omniSustainer.sustain(coordinator)
    delay(ASYNC_DELAY_MS)
    val navigator = FakeNavigator()
    val destination = FakeDestination()
    navigator.nextApplicationSignalTranslation = destination
    coordinator.navigator.value = navigator
    delay(ASYNC_DELAY_MS)
    coordinator.acceptSignalFromApplication("")
    delay(ASYNC_DELAY_MS)
    omniSustainer.release(coordinator)
    delay(ASYNC_DELAY_MS)
    
    val replacementNavigator = FakeNavigator()
    coordinator.navigator.value = replacementNavigator
    delay(ASYNC_DELAY_MS)

    assertThat(replacementNavigator.work.isStarted()).isFalse()
    assertThat(replacementNavigator.work.wasStarted()).isFalse()
  }

  @Test
  fun navigatorSet_whileReleased_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun navigatorCleared_whileSustained_withNavigator_existingNavigatorBecomesReleased() = runBlocking {

  }

  @Test
  fun navigatorCleared_whileSustained_withoutNavigator_noFailuresOccur() = runBlocking {

  }



  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withPendingSignalFromApplication_latestSignalTranslatesToNull_withDestination_existingDestinationReleased() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withPendingSignalFromApplication_latestSignalTranslatesToNull_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withPendingSignalFromApplication_latestSignalTranslatesToNull_uiLaunched() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withPendingSignalFromApplication_latestSignalTranslatesToNonNull_withDestination_existingDestinationReleased() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withPendingSignalFromApplication_latestSignalTranslatesToNonNull_withDestination_newDestinationSustained() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withPendingSignalFromApplication_latestSignalTranslatesToNonNull_uiNotLaunched() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withPendingSignalFromEnvironment_latestSignalTranslatesToNull_withDestination_existingDestinationReleased() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withPendingSignalFromEnvironment_latestSignalTranslatesToNull_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withPendingSignalFromEnvironment_latestSignalTranslatesToNull_uiLaunched() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withPendingSignalFromEnvironment_latestSignalTranslatesToNonNull_withDestination_existingDestinationReleased() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withPendingSignalFromEnvironment_latestSignalTranslatesToNonNull_withDestination_newDestinationSustained() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withPendingSignalFromEnvironment_latestSignalTranslatesToNonNull_uiNotLaunched() = runBlocking {

  }

  
  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withoutPendingSignal_latestSignalTranslatesToNull_withDestination_existingDestinationReleased() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withoutPendingSignal_latestSignalTranslatesToNull_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withoutPendingSignal_latestSignalTranslatesToNull_uiLaunched() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withoutPendingSignal_latestSignalTranslatesToNonNull_withDestination_existingDestinationReleased() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withoutPendingSignal_latestSignalTranslatesToNonNull_withDestination_newDestinationSustained() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withNavigator_withoutPendingSignal_latestSignalTranslatesToNonNull_uiNotLaunched() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withoutNavigator_withDestination_existingDestinationRetained() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withoutNavigator_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withoutNavigator_withPendingSignalFromApplication_bothSignalsIgnored() = runBlocking {

  }

  @Test
  fun signalFromApplicationReceived_whileSustained_withoutNavigator_withPendingSignalFromEnvironment_bothSignalsIgnored() = runBlocking {

  }
  
  @Test
  fun signalFromApplicationReceived_whileSustained_withoutNavigator_withoutPendingSignal_signalIgnored() = runBlocking {
    
  }

  @Test
  fun signalFromApplicationReceived_whileReleased_withDestination_existingDestinationRetained() = runBlocking {

  }
  
  @Test
  fun signalFromApplicationReceived_whileReleased_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withPendingSignalFromApplication_latestSignalTranslatesToNull_withDestination_existingDestinationReleased() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withPendingSignalFromApplication_latestSignalTranslatesToNull_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withPendingSignalFromApplication_latestSignalTranslatesToNull_uiLaunched() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withPendingSignalFromApplication_latestSignalTranslatesToNonNull_withDestination_existingDestinationReleased() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withPendingSignalFromApplication_latestSignalTranslatesToNonNull_withDestination_newDestinationSustained() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withPendingSignalFromApplication_latestSignalTranslatesToNonNull_uiNotLaunched() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withPendingSignalFromEnvironment_latestSignalTranslatesToNull_withDestination_existingDestinationReleased() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withPendingSignalFromEnvironment_latestSignalTranslatesToNull_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withPendingSignalFromEnvironment_latestSignalTranslatesToNull_uiLaunched() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withPendingSignalFromEnvironment_latestSignalTranslatesToNonNull_withDestination_existingDestinationReleased() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withPendingSignalFromEnvironment_latestSignalTranslatesToNonNull_withDestination_newDestinationSustained() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withPendingSignalFromEnvironment_latestSignalTranslatesToNonNull_uiNotLaunched() = runBlocking {

  }

  
  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withoutPendingSignal_latestSignalTranslatesToNull_withDestination_existingDestinationReleased() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withoutPendingSignal_latestSignalTranslatesToNull_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withoutPendingSignal_latestSignalTranslatesToNull_uiLaunched() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withoutPendingSignal_latestSignalTranslatesToNonNull_withDestination_existingDestinationReleased() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withoutPendingSignal_latestSignalTranslatesToNonNull_withDestination_newDestinationSustained() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withNavigator_withoutPendingSignal_latestSignalTranslatesToNonNull_uiNotLaunched() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withoutNavigator_withDestination_existingDestinationRetained() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withoutNavigator_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withoutNavigator_withPendingSignalFromApplication_bothSignalsIgnored() = runBlocking {

  }

  @Test
  fun signalFromEnvironmentReceived_whileSustained_withoutNavigator_withPendingSignalFromEnvironment_bothSignalsIgnored() = runBlocking {

  }
  
  @Test
  fun signalFromEnvironmentReceived_whileSustained_withoutNavigator_withoutPendingSignal_signalIgnored() = runBlocking {
    
  }

  @Test
  fun signalFromEnvironmentReceived_whileReleased_withDestination_existingDestinationRetained() = runBlocking {

  }
  
  @Test
  fun signalFromEnvironmentReceived_whileReleased_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun sustained_withNavigator_navigatorBecomesSustained() = runBlocking {
    
  }

  @Test
  fun sustained_withNavigator_withPendingSignalFromApplication_latestSignalProcessed() = runBlocking {

  }

  @Test
  fun sustained_withNavigator_withPendingSignalFromApplication_uiLaunched() = runBlocking {

  }

  @Test
  fun sustained_withNavigator_withPendingSignalFromEnvironment_latestSignalProcessed() = runBlocking {

  }

  @Test
  fun sustained_withNavigator_withPendingSignalFromEnvironment_uiLaunched() = runBlocking {

  }

  @Test
  fun sustained_withNavigator_withoutPendingSignal_noFailuresOccur() = runBlocking {

  }

  @Test
  fun sustained_withoutNavigator_noFailuresOccur() = runBlocking {
    
  }

  @Test
  fun sustained_withoutNavigator_withUnprocessedSignalFromApplication_signalIgnored() = runBlocking {

  }

  @Test
  fun sustained_withoutNavigator_withUnprocessedSignalFromEnvironment_signalIgnored() = runBlocking {

  }

  @Test
  fun sustained_withoutNavigator_withoutNoUnprocessedSignal_noFailuresOccur() = runBlocking {

  }

  @Test
  fun sustained_withDestination_destinationBecomesSustained() = runBlocking {

  }

  @Test
  fun sustained_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun released_withNavigator_navigatorBecomesReleased() = runBlocking {
    
  }

  @Test
  fun released_withoutNavigator_noFailuresOccur() = runBlocking {
    
  }  

  @Test
  fun released_withDestiation_destinationBecomesReleased() = runBlocking {

  }

  @Test
  fun released_withoutDestination_noFailuresOccur() = runBlocking {

  }

  @Test
  fun released_withPendingSignalFromApplication_signalRemainsPending() = runBlocking {

  }

  @Test
  fun released_withPendingSignalFromEnvironment_signalRemainsPending() = runBlocking {

  }

  @Test
  fun released_withoutPendingSignal_noFailuresOccur() = runBlocking {

  }

  /** The destination cannot be directly set, but if the coordinator is functioning correctly then
   * the destination can be set by passing in a signal to be translated.
   */
  private suspend fun setDestination(navigator: FakeNavigator, destination: FakeDestination) {
    navigator.nextApplicationSignalTranslation = destination
    coordinator.acceptSignalFromApplication("anything")
    delay(ASYNC_DELAY_MS)
  }

  companion object {
    // Delay to give async processes enough time to complete.
    private const val ASYNC_DELAY_MS = 500L
  }
}

private class FakeNavigator : Navigator<String, String, Ui, FakeDestination, Operation<StartStop>> {
  
  var nextApplicationSignalTranslation: FakeDestination? = null

  var nextEnvironmentSignalTranslation: FakeDestination? = null
  
  
  /**
   * The work performed by this [Navigator]. Exposed as a public member so tests can make assertions
   * about whether the navigator is sustained.
   */
  val work = StartStopSimplex()

  override fun translateSignalFromApplication(signal: String) = nextApplicationSignalTranslation

  override fun translateSignalFromEnvironment(signal: String) = nextEnvironmentSignalTranslation

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

