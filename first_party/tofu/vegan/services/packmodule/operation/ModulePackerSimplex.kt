package services.packmodule.operation

import dataaccess.unpacked.UnpackedFirmTofu
import model.module.ModuleContent

class ModulePackerSimplex : ModulePacker {
  override fun packModule(
      content: ModuleContent,
      dependencies: Set<UnpackedFirmTofu>
  ): UnpackedFirmTofu {
    TODO("Not yet implemented")
  }
}
