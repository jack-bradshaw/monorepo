package com.jackbradshaw.codestone.lifecycle.worker

import com.jackbradshaw.codestone.lifecycle.work.Work

/** Performs ongoing work and provides a handle for controlling the work. */
interface Worker<out W : Work<*>> {
  /** The sustained work. */
  val work: W
}
