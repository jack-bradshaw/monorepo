interface AddressService {
  fun getContentAddress(target: UnpackedFirmTofu, source: ModuleSourceAddress): ContentAddress?

  fun getContentAddress(target: UnpackedFirmTofu, source: ProviderSourceAddress): ContentAddress?

  fun getAllModuleContentAddresses(target: UnpackedFirmTofu): Set<ContentAddress>

  fun getAllProviderContentAddresses(target: UnpackedFirmTofu): Set<ContentAddress>
}
