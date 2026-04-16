package com.jackbradshaw.kale.model

/**
 * The final result of a KSP run.
 *
 * Files and logs produced by the run are stored in [artifacts] and [logs] respectively.
 */
sealed class Result(val artifacts: Artifacts, val logs: List<Log>) {

  /** The run completed successfully (no fatal exceptions/errors occurred). */
  class Success(artifacts: Artifacts, logs: List<Log> = emptyList()) : Result(artifacts, logs)

  /** The run failed due to a fatal exception/error (stored in [error] if available). */
  class Failure(artifacts: Artifacts, logs: List<Log> = emptyList(), val error: Throwable? = null) :
      Result(artifacts, logs)
}
