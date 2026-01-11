package com.jackbradshaw.codestone.interaction.coordinator

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.Flow
import com.jackbradshaw.codestone.interaction.destination.Destination
import com.jackbradshaw.codestone.interaction.navigator.Navigator
import com.jackbradshaw.codestone.lifecycle.worker.Worker
import com.jackbradshaw.codestone.lifecycle.work.Work
import com.jackbradshaw.codestone.interaction.usable.Usable
import com.jackbradshaw.codestone.interaction.usable.Usable.Ui

/** Accepts signals from the system (external) and the application (internal), routes them to 
 * [navigator] for translation, then sustains and/or displays the resulting destination. */
interface Coordinator<in A, in E, U : Ui, D : Destination<U, *>, N : Navigator<A, E, U, D, *>, W : Work<*>> : Worker<W> {

  /** The current navigator. Follows the coordinator's lifecycle. */
  val navigator: MutableStateFlow<N?>

  /** The current destination. Follows the coordinator's lifecycle. */
  val destination: StateFlow<D?>

  /** Declares a signal from within the application for translation to a destination. Returns
   * immediately without waiting for processing to complete. */
  fun onSignalFromApplication(signal: A)

  /** Declares a signal from the environment for translation to a destination. Returns
   * immediately without waiting for processing to complete.  */
  fun onSignalFromEnvironment(signal: E)
}