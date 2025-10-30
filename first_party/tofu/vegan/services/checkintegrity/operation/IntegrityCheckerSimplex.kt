package services.checkintegrity.operation

import dataaccess.packed.PackedFirmTofu

class IntegrityCheckerSimplex(
    private val workspaceGenerator: WorkspaceGenerator,
) : IntegrityChecker {
  override fun check(firmTofu: PackedFirmTofu, stopOnFirstError: Boolean): Set<Error> {
    exporter.export()
    tofuCli.executeCommand(Operation.Validate(exportpath))
  }
}
