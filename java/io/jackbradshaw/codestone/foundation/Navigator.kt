package io.jackbradshaw.codestone.foundation
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.ui.primitives.Usable
import io.jackbradshaw.codestone.ui.primitives.Usable.Ui

/** Translates inputs from the system (external) and the application (internal) into destinations to
 * launch. */
interface Navigator<in A, in E, U : Ui, out D: Destination<U, *>, O : Operation<*>> : Sustainable<O> {
  
  fun translateSignalFromApplication(signal: A): D?

  fun translateSignalFromEnvironment(signal: E): D?
}