package io.jackbradshaw.codestone.foundation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.ui.primitives.Usable
import io.jackbradshaw.codestone.ui.primitives.Usable.Ui

/** Accepts signals from the system (external) and the application (internal), routes them to 
 * [navigator] for translation, then sustains and displays the resulting destination. */
interface Coordinator<in A, in E, U : Ui, D : Destination<U, *>, N : Navigator<A, E, U, D, *>, O : Operation<*>> : Sustainable<O> {

  /** The navigator to translate internal and external signals. */
  val navigator: MutableStateFlow<N?>

  /** Creates a cold flow which emits each time the destination changes. */
  val destination: StateFlow<D?>

  /** Declares a signal from within the application for translation to a destination. Returns
   * immediately without waiting for processing to complete. */
  fun acceptSignalFromApplication(signal: A)

  /** Declares a signal from the environment for translation to a destination. Returns
   * immediately without waiting for processing to complete.  */
  fun acceptSignalFromEnvironment(signal: E)
}