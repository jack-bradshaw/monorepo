package io.jackbradshaw.queen.foundation
import io.jackbradshaw.queen.sustainment.primitives.Sustainable
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.ui.primitives.Usable
import io.jackbradshaw.queen.ui.primitives.Usable.Ui

/** The root of the application. Always available in memory while the application is running. */
interface Root<A, E, U : Ui, D : Destination<U, *>, N : Navigator<A, E, U, D, *>, C : Coordinator<A, E, U, D, N, *>> {
  val coordinator: C
}