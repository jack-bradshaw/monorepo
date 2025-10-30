package services.modifyreferences.operation

import dataaccess.unpacked.UnpackedFirmTofu
import model.address.ContentAddress

class MetadataModifierSimplex : MetadataModifier {
  override fun addRequiredProvider(
      target: UnpackedFirmTofu,
      module: ContentAddress,
      provider: ContentAddress
  ) {
    TODO("Not yet implemented")
  }

  override fun removeRequiredProvider(
      target: UnpackedFirmTofu,
      module: ContentAddress,
      provider: ContentAddress
  ) {
    TODO("Not yet implemented")
  }

  override fun addSubmodule(
      target: UnpackedFirmTofu,
      supermoduleAddress: ContentAddress,
      submoduleAddress: ContentAddress,
      submoduleName: String
  ) {
    TODO("Not yet implemented")
  }

  override fun removeSubmodule(
      target: UnpackedFirmTofu,
      supermoduleAddress: ContentAddress,
      submoduleAddress: ContentAddress,
      submoduleName: String
  ) {
    TODO("Not yet implemented")
  }

  override fun addModuleCall(
      target: UnpackedFirmTofu,
      callerModule: ContentAddress,
      calledModule: ContentAddress
  ) {
    TODO("Not yet implemented")
  }

  override fun removeModuleCall(
      target: UnpackedFirmTofu,
      callerModule: ContentAddress,
      calledModule: ContentAddress
  ) {
    TODO("Not yet implemented")
  }
}
