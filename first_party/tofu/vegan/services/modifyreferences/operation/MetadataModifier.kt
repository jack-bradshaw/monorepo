package services.modifyreferences.operation

import dataaccess.unpacked.UnpackedFirmTofu
import model.address.ContentAddress

interface MetadataModifier {
  fun addRequiredProvider(
      target: UnpackedFirmTofu,
      module: ContentAddress,
      provider: ContentAddress,
  )

  fun removeRequiredProvider(
      target: UnpackedFirmTofu,
      module: ContentAddress,
      provider: ContentAddress,
  )

  fun addSubmodule(
      target: UnpackedFirmTofu,
      supermoduleAddress: ContentAddress,
      submoduleAddress: ContentAddress,
      submoduleName: String,
  )

  fun removeSubmodule(
      target: UnpackedFirmTofu,
      supermoduleAddress: ContentAddress,
      submoduleAddress: ContentAddress,
      submoduleName: String,
  )

  fun addModuleCall(
      target: UnpackedFirmTofu,
      callerModule: ContentAddress,
      calledModule: ContentAddress,
  )

  fun removeModuleCall(
      target: UnpackedFirmTofu,
      callerModule: ContentAddress,
      calledModule: ContentAddress,
  )
}
