package services.packprovider.operation

import dataaccess.unpacked.UnpackedFirmTofu
import model.provider.ProviderContent

interface ProviderPacker {
  /** Creates an [UnpackedFirmTofu] containing [provider]. */
  fun packProvider(content: ProviderContent): UnpackedFirmTofu
}
