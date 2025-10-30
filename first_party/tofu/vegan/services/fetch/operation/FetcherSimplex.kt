package services.fetch.operation

import model.address.ModuleSourceAddress
import model.address.ProviderSourceAddress

class FetcherSimplex : Fetcher {
  override fun fetch(
      modules: Set<ModuleSourceAddress>,
      providers: Set<ProviderSourceAddress>
  ): TransitiveFetch {
    TODO("Not yet implemented")
  }
}
