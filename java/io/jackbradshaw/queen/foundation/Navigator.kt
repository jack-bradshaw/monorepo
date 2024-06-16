package io.jackbradshaw.queen.foundation
import io.jackbradshaw.queen.sustainment.primitives.Sustainable
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.ui.primitives.Usable
import io.jackbradshaw.queen.ui.primitives.Usable.Ui

/** Translates inputs from the system (external) and the application (internal) into destinations to
 * launch. */
interface Navigator<in A, in E, U : Ui, out D: Destination<U, *>, O : Operation<*>> : Sustainable<O> {
  fun translateSignalFromApplication(signal: A): D?
  fun translateSignalFromEnvironment(signal: E): D?
}