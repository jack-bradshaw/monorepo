package workflows.checkintegrity

import dataaccess.packed.PackedFirmTofu
import model.address.ContentAddress

interface IntegrityChecker {
  fun check(firmTofu: PackedFirmTofu, stopOnFirstError: Boolean = false): Set<Error>
}

sealed class Error {
  object NotAZip : Error()

  object MissingManifest : Error()

  object MissingModulesDirectory : Error()

  object MissingProvidersDirectory : Error()

  class InvalidManifest(protoError: RuntimeException) : Error()

  class InvalidModuleMetadata(protoError: RuntimeException) : Error()

  class InvalidProviderMetadata(protoError: RuntimeException) : Error()

  class ExtraneousDirectoryInRoot(name: String) : Error()

  class ExtraneousFileInRoot(name: String) : Error()

  class ExtraneousFileInModules(name: String) : Error()

  class ExtraneousFileInProviders(name: String) : Error()

  class ExtraneousModuleSubdirectory(contentAddress: ContentAddress) : Error()

  class ExtraneousProviderSubdirectory(contentAddress: ContentAddress) : Error()

  class ModuleContentWithoutMetadata(contentAddress: ContentAddress) : Error()
}
