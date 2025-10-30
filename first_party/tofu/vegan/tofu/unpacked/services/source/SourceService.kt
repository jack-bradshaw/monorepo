interface SourceService {

  fun getModuleSource(target: UnpackedFirmTofu, address: ContentAddress): ModuleSourceAddress?

  fun setModuleSource(
      target: UnpackedFirmTofu,
      module: ContentAddress,
      source: ModuleSourceAddress,
  )

  fun clearModuleSource(
      target: UnpackedFirmTofu,
      module: ContentAddress,
  )

  fun getAllModuleSources(
      target: UnpackedFirmTofu,
      topLevelOnly: Boolean = false
  ): Set<ModuleSourceAddress>

  fun getProviderSource(target: UnpackedFirmTofu, address: ContentAddress): ProviderSourceAddress?

  fun setProviderSource(
      target: UnpackedFirmTofu,
      provider: ContentAddress,
      source: ProviderSourceAddress
  )

  fun clearProviderSource(
      target: UnpackedFirmTofu,
      provider: ContentAddress,
  )

  fun getAllProviderSources(
      target: UnpackedFirmTofu,
      topLevelOnly: Boolean = false
  ): Set<ProviderSourceAddress>
}
