package services.fetch.operation

import dataaccess.packed.PackedFirmTofu
import model.address.ModuleSourceAddress
import model.address.ProviderSourceAddress

interface Fetcher {
  fun fetch(
      modules: Set<ModuleSourceAddress>,
      providers: Set<ProviderSourceAddress>
  ): TransitiveFetch
}

data class TransitiveFetch(
    val moduleTranstiveClosures: Map<ModuleSourceAddress, PackedFirmTofu> = emptyMap(),
    val providerTransitiveClosures: Map<ProviderSourceAddress, PackedFirmTofu> = emptyMap(),
)
