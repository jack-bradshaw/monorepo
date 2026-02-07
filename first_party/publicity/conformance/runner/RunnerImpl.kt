package com.jackbradshaw.publicity.conformance.runner

import com.jackbradshaw.publicity.conformance.model.Workspace
import com.jackbradshaw.publicity.conformance.workspacechecker.WorkspaceChecker
import com.jackbradshaw.sasync.outbound.transport.OutboundTransport
import com.jackbradshaw.sasync.standard.error.StandardError
import com.jackbradshaw.sasync.standard.output.StandardOutput
import javax.inject.Inject

/** [Runner] implementation. */
class RunnerImpl
@Inject
constructor(
    private val workspace: Workspace,
    private val workspaceChecker: WorkspaceChecker,
    @StandardOutput private val output: OutboundTransport,
    @StandardError private val error: OutboundTransport
) : Runner {

  override suspend fun run(): Runner.Result {
    val result = workspaceChecker.checkAllFirstPartyProperties(workspace)

    when (result) {
      is WorkspaceChecker.Result.AllConform -> {
        output.publishStringLine("All first-party properties are conformant.")
        return Runner.Result.SUCCESS
      }
      is WorkspaceChecker.Result.NonConformingPropertiesFound -> {
        error.publishStringLine("Non-conforming properties found:")
        result.properties.entries
            .sortedBy { it.key }
            .forEach { (path, err) -> error.publishStringLine("$path: $err") }
        return Runner.Result.FAIL
      }
    }
  }
}
