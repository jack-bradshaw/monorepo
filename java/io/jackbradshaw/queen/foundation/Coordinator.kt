package io.jackbradshaw.queen.foundation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.Flow
import io.jackbradshaw.queen.sustainment.primitives.Sustainable
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.ui.primitives.Usable
import io.jackbradshaw.queen.ui.primitives.Usable.Ui

/** Accepts signals from the system (external) and the application (internal), routes them to 
 * [navigator] for translation, then sustains and/or displays the resulting destination. */
interface Coordinator<in A, in E, U : Ui, D : Destination<U, *>, N : Navigator<A, E, U, D, *>, O : Operation<*>> : Sustainable<O> {

  /** The current navigator. Follows the coordinator's sustainment. */
  val navigator: MutableStateFlow<N?>

  /** The current destination. Follows the coordinator's sustainment. */
  val destination: StateFlow<D?>

  /** Declares a signal from within the application for translation to a destination. Returns
   * immediately without waiting for processing to complete. */
  fun onSignalFromApplication(signal: A)

  /** Declares a signal from the environment for translation to a destination. Returns
   * immediately without waiting for processing to complete.  */
  fun onSignalFromEnvironment(signal: E)
}