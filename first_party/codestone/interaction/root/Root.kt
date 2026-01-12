package com.jackbradshaw.codestone.interaction.root
import com.jackbradshaw.codestone.interaction.coordinator.Coordinator
import com.jackbradshaw.codestone.interaction.destination.Destination
import com.jackbradshaw.codestone.interaction.navigator.Navigator
import com.jackbradshaw.codestone.lifecycle.worker.Worker
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.interaction.usable.Usable
import com.jackbradshaw.codestone.interaction.usable.Usable.Ui

/** The root of the application. Always available in memory while the application is running. */
interface Root<A, E, U : Ui, D : Destination<U, *>, N : Navigator<A, E, U, D, *>, C : Coordinator<A, E, U, D, N, *>> {
  val coordinator: C
}