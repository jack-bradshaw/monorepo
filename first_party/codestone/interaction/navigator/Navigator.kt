package com.jackbradshaw.codestone.interaction.navigator
import com.jackbradshaw.codestone.interaction.destination.Destination
import com.jackbradshaw.codestone.lifecycle.worker.Worker
import com.jackbradshaw.codestone.lifecycle.work.Work
import com.jackbradshaw.codestone.interaction.usable.Usable
import com.jackbradshaw.codestone.interaction.usable.Usable.Ui

/** Translates inputs from the system (external) and the application (internal) into destinations to
 * launch. */
interface Navigator<in A, in E, U : Ui, out D: Destination<U, *>, W : Work<*>> : Worker<W> {
  fun translateSignalFromApplication(signal: A): D?
  fun translateSignalFromEnvironment(signal: E): D?
}