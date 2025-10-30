package services.packmodule.operation

import dataaccess.unpacked.UnpackedFirmTofu
import model.module.ModuleContent

interface ModulePacker {
  /**
   * Creates an [UnpackedFirmTofu] containing [content].
   *
   * The [content] may use any module reference type, except for direct file references, including
   * remote source addresses and ephemeral addresses. All references must be resolvable in the
   * provided [dependencies] set, else a [MissingDependenciesException] will be thrown.
   */
  fun packModule(
      content: ModuleContent,
      dependencies: Set<UnpackedFirmTofu>,
  ): UnpackedFirmTofu
}

class MissingDependenciesException(val unresolvedReferences: Set<String>) : RuntimeException()
