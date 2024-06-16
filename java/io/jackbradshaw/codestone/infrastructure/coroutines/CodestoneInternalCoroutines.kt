package io.jackbradshaw.codestone.infrastructure.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

// TODO: Delete this class, it shouldn't exist. Move definitions to each platforms.

/** Coroutine elements for use within codestone. */
object CodestoneInternalCoroutines {

  /** Coroutine scope for use in sustainment internals. */
  val forSustainment =
      object : CoroutineScope {
        override val coroutineContext = Dispatchers.Default + SupervisorJob()
      }
}
