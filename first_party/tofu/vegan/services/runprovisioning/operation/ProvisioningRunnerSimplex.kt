package services.runprovisioning.operation

import dataaccess.unpacked.UnpackedFirmTofu
import model.plan.Plan

class ProvisioningRunnerSimplex(
    private val exporter: Exporter,
    private val workspaceGenerator: WorkspaceGenerator,
    private val nativeTofu: NativeTofu
) : ProvisioningRunner {
  override fun makePlan(firmTofu: UnpackedFirmTofu): Plan {
    val workspace = workspaceGenerator.generateTemporaryDirectory()
    exporter.export(firmTofu, workspace)
    nativeTofu.init(workspace)
    return Plan(nativeTofu.plan(workspace))
  }

  override fun executePlan(firmTofu: UnpackedFirmTofu, plan: Plan) {
    val workspace = workspaceGenerator.generateTemporaryDirectory()
    exporter.export(firmTofu, workspace)
    nativeTofu.init(workspace)
    return Plan(nativeTofu.plan(workspace))
  }
}
