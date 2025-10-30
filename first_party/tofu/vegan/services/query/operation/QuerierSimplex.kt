package services.query.operation

import dataaccess.unpacked.UnpackedFirmTofu
import model.address.ContentAddress
import model.address.ModuleSourceAddress
import model.address.ProviderSourceAddress

class QuerierSimplex : Querier {
  override fun getContentAddressForModule(
      target: UnpackedFirmTofu,
      source: ModuleSourceAddress
  ): ContentAddress? {
    TODO("Not yet implemented")
  }

  override fun getContentAddressForProvider(
      target: UnpackedFirmTofu,
      source: ProviderSourceAddress
  ): ContentAddress? {
    TODO("Not yet implemented")
  }

  override fun getSourceAddressForModule(
      target: UnpackedFirmTofu,
      address: ContentAddress
  ): ModuleSourceAddress? {
    TODO("Not yet implemented")
  }

  override fun getSourceAddressForProvider(
      target: UnpackedFirmTofu,
      address: ContentAddress
  ): ProviderSourceAddress? {
    TODO("Not yet implemented")
  }

  override fun getAllModuleContentAddresses(target: UnpackedFirmTofu): Set<ContentAddress> {
    TODO("Not yet implemented")
  }

  override fun getAllProviderContentAddresses(target: UnpackedFirmTofu): Set<ContentAddress> {
    TODO("Not yet implemented")
  }

  override fun getAllModuleSourceAddresses(
      target: UnpackedFirmTofu,
      topLevelOnly: Boolean
  ): Set<ModuleSourceAddress> {
    TODO("Not yet implemented")
  }

  override fun getAllProviderSourceAddresses(target: UnpackedFirmTofu): Set<ProviderSourceAddress> {
    TODO("Not yet implemented")
  }

  override fun getSubmodules(
      target: UnpackedFirmTofu,
      module: ContentAddress
  ): Set<ContentAddress> {
    TODO("Not yet implemented")
  }

  override fun getSupermodules(
      target: UnpackedFirmTofu,
      module: ContentAddress
  ): Set<ContentAddress> {
    TODO("Not yet implemented")
  }

  override fun getModulesRequiringProvider(
      target: UnpackedFirmTofu,
      provider: ContentAddress
  ): Set<ContentAddress> {
    TODO("Not yet implemented")
  }

  override fun getProvidersRequiredByModule(
      target: UnpackedFirmTofu,
      module: ContentAddress
  ): Set<ContentAddress> {
    TODO("Not yet implemented")
  }

  override fun getModuleCallerReferences(
      target: UnpackedFirmTofu,
      module: ContentAddress
  ): Set<ContentAddress> {
    TODO("Not yet implemented")
  }

  override fun getModuleCalledReferences(
      target: UnpackedFirmTofu,
      module: ContentAddress
  ): Set<ContentAddress> {
    TODO("Not yet implemented")
  }

  override fun getArchiveVersion(target: UnpackedFirmTofu): Integer {
    TODO("Not yet implemented")
  }

  override fun getManifestAsJson(target: UnpackedFirmTofu): String {
    TODO("Not yet implemented")
  }

  override fun getManifestAsXml(target: UnpackedFirmTofu): String {
    TODO("Not yet implemented")
  }

  override fun getEmergentProperties(target: UnpackedFirmTofu): EmergentProperties {
    TODO("Not yet implemented")
  }
}
