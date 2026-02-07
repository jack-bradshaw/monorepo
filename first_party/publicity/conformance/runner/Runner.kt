package com.jackbradshaw.publicity.conformance.runner

/**
 * Runs the conformance test.
 *
 * The implementation is responsible for sourcing the workspace.
 *
 * The API only requires the result to be returned as a binary success/failure. All other details
 * should be logged to STDIO.
 */
interface Runner {

  /** Executes the conformance audit on the workspace and returns the [Result]. */
  suspend fun run(): Result

  /** The result of a conformance audit. */
  enum class Result {
    /** The audit passed. */
    SUCCESS,

    /** The audit failed. */
    FAIL
  }
}
