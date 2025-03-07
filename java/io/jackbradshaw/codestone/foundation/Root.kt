package io.jackbradshaw.codestone.foundation
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.ui.primitives.Usable
import io.jackbradshaw.codestone.ui.primitives.Usable.Ui

/** The root of the application. Always available in memory while the application is running. */
interface Root<I, U : Ui, D : Destination<U, *>, N : Navigator<I, U, D, *>, C : Coordinator<I, U, D, N, *>> {
  fun getCoordinator(): C
}