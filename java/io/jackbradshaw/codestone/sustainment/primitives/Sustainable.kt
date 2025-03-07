package io.jackbradshaw.codestone.sustainment.primitives

import kotlin.reflect.KType

/** Performs ongoing work and provides a handle for controlling the work. */
interface Sustainable<out O : Sustainable.Operation<*>> {
  /** The ongoing piece of work. */
  val operation: O

  /** An ongoing piece of [work]. */
  interface Operation<out W> {
    /**
     * Creates the work handle, and starts it if the work type does not support a distinction
     * between creation and being started (Futures for example).
     */
    fun work(): W

    /** The reified type of the [work] handle. */
    val workType: KType
  }
}
