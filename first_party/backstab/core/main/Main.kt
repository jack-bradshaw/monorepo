package com.jackbradshaw.backstab.core.main

/** Entrypoint for the Backstab annotation processor host-side logic. */
interface Main {
  /** Runs the main logic of the backstab processor. May suspend indefinitely. */
  suspend fun run()
}
