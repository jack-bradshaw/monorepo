package services.modifysource.operation

import dataaccess.unpacked.UnpackedFirmTofu
import model.address.ContentAddress
import model.address.ModuleSourceAddress
import model.address.ProviderSourceAddress

interface SourceModifier {
  fun setModuleSource(
      target: UnpackedFirmTofu,
      module: ContentAddress,
      source: ModuleSourceAddress,
  )

  fun setProviderSource(
      target: UnpackedFirmTofu,
      provider: ContentAddress,
      source: ProviderSourceAddress
  )
}
