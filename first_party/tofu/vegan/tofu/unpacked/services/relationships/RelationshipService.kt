interface RelationshipService {

  fun getRequiringModules(target: UnpackedFirmTofu, provider: ContentAddress): Set<ContentAddress>

  fun getRequiredProviders(target: UnpackedFirmTofu, module: ContentAddress): Set<ContentAddress>

  fun addRequiredProviderLink(
      target: UnpackedFirmTofu,
      module: ContentAddress,
      provider: ContentAddress,
  )

  fun removeRequiredProviderLink(
      target: UnpackedFirmTofu,
      module: ContentAddress,
      provider: ContentAddress,
  )

  fun getSubmodules(target: UnpackedFirmTofu, module: ContentAddress): Set<ContentAddress>

  fun getSupermodules(target: UnpackedFirmTofu, module: ContentAddress): Set<ContentAddress>

  fun addModuleNestingLink(
      target: UnpackedFirmTofu,
      supermoduleAddress: ContentAddress,
      submoduleAddress: ContentAddress,
      submoduleName: String,
  )

  fun removeModuleNestingLink(
      target: UnpackedFirmTofu,
      supermoduleAddress: ContentAddress,
      submoduleAddress: ContentAddress,
      submoduleName: String,
  )

  fun getCallerModules(target: UnpackedFirmTofu, module: ContentAddress): Set<ContentAddress>

  fun getCalledModules(target: UnpackedFirmTofu, module: ContentAddress): Set<ContentAddress>

  fun addModuleCallLink(
      target: UnpackedFirmTofu,
      callerModule: ContentAddress,
      calledModule: ContentAddress,
  )

  fun removeModuleCalllink(
      target: UnpackedFirmTofu,
      callerModule: ContentAddress,
      calledModule: ContentAddress,
  )
}
