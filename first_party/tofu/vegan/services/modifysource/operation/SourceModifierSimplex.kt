package services.modifysource.operation

import dataaccess.unpacked.UnpackedFirmTofu
import model.address.ContentAddress
import model.address.ModuleSourceAddress
import model.address.ProviderSourceAddress

class SourceModifierSimplex : SourceModifier {
  override fun setModuleSource(
      target: UnpackedFirmTofu,
      module: ContentAddress,
      source: ModuleSourceAddress
  ) {
    TODO("Not yet implemented")
  }

  override fun setProviderSource(
      target: UnpackedFirmTofu,
      provider: ContentAddress,
      source: ProviderSourceAddress
  ) {
    TODO("Not yet implemented")
  }
}
