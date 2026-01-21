package com.jackbradshaw.codestone.interaction.destination

import com.jackbradshaw.codestone.lifecycle.worker.Worker
import com.jackbradshaw.codestone.lifecycle.work.Work
import com.jackbradshaw.codestone.interaction.usable.Usable
import com.jackbradshaw.codestone.interaction.usable.Usable.Ui

/** A location in the application that can be given focus. */
interface Destination<U : Ui, W: Work<*>> : Usable<U>, Worker<W>
