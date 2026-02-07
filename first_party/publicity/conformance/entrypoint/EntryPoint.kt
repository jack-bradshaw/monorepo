package com.jackbradshaw.publicity.conformance.entrypoint

import com.jackbradshaw.concurrency.concurrencyComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.publicity.conformance.model.Workspace
import com.jackbradshaw.publicity.conformance.runner.runnerComponent
import com.jackbradshaw.sasync.inbound.config.defaultConfig as inboundConfig
import com.jackbradshaw.sasync.inbound.inboundComponent
import com.jackbradshaw.sasync.outbound.config.defaultConfig as outboundConfig
import com.jackbradshaw.sasync.outbound.outboundComponent
import com.jackbradshaw.sasync.standard.standardComponent
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.lang.System
import kotlin.system.exitProcess
import kotlinx.coroutines.runBlocking

/**
 * Entry point for the publicity conformance tests.
 *
 * Run the tests by calling [main], and control the tests by setting:
 * - BUILD_WORKSPACE_DIRECTORY to the absolute file path of the workspace to check.
 * - FIRST_PARTY_ROOT to the relative file path of the first party root package.
 *
 * The values must be present in either env or properties, both must be directories, both must
 * exist, and FIRST_PARTY_ROOT must be in BUILD_WORKSPACE_DIRECTORY.
 *
 * The test logs are written to STDIO, and main returns 0/1 for success/failure. Args are unused and
 * ignored.
 */
object EntryPoint {

  /**
   * Executes the conformance tests, writes the results to the streams currently set in [System],
   * then returns 0/1 for success/failure.
   */
  @JvmStatic
  fun main(args: Array<String>) {
    exitProcess(run())
  }

  /**
   * Executes the conformance tests, writes the results to the provided streams, then returns 0/1
   * for success/failure.
   */
  fun run(
      stdin: InputStream = System.`in`,
      stdout: OutputStream = System.`out`,
      stderr: OutputStream = System.err,
  ): Int = runBlocking {
    val workspace =
        try {
          val workspaceRoot = File(getControlValue(WORKSPACE_DIR_KEY))
          val firstPartyRoot = workspaceRoot.resolve(getControlValue(FIRST_PARTY_ROOT_DIR_KEY))
          Workspace(workspaceRoot, firstPartyRoot)
        } catch (e: Exception) {
          System.err.println(e.message)
          return@runBlocking 1
        }

    val coroutines = coroutinesComponent()
    val concurrency = concurrencyComponent()
    val standard =
        standardComponent(
            inbound = inboundComponent(inboundConfig, coroutines, concurrency),
            outbound = outboundComponent(outboundConfig, coroutines, concurrency),
            input = stdin,
            output = stdout,
            error = stderr)

    val result = runnerComponent(workspace, standard).runner().run()

    // Asynchronous output buffers may need time to flush.
    standard.standardOutputOutboundTransport().close()
    standard.standardErrorOutboundTransport().close()

    when (result) {
      com.jackbradshaw.publicity.conformance.runner.Runner.Result.SUCCESS -> 0
      com.jackbradshaw.publicity.conformance.runner.Runner.Result.FAIL -> 1
    }
  }

  private fun getControlValue(key: String): String {
    return requireNotNull(System.getenv(key) ?: System.getProperty(key)) {
      "Required control value $key is not set in env or properties."
    }
  }

  /** Key for loading the workspace directory from the environment. */
  private val WORKSPACE_DIR_KEY = "BUILD_WORKSPACE_DIRECTORY"

  /** Key for loading the first party root directory from the environment. */
  private val FIRST_PARTY_ROOT_DIR_KEY = "FIRST_PARTY_ROOT"
}
