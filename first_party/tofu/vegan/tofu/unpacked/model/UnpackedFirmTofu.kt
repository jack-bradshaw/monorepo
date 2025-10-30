package dataaccess.unpacked

import model.address.ContentAddress
import model.manifest.Manifest
import model.module.Module
import model.provider.Provider

/**
 * A single FirmTofu program.
 *
 * All operations are independent and idempotent, and no checks are performed to ensure the manifest
 * remains in sync with the modules/providers. The caller must ensure consistency when modifying
 * modules/providers.
 */
interface UnpackedFirmTofu {
  fun hasManifest(): Boolean

  fun getManifest(): Manifest

  fun setManifest(manifest: Manifest)

  fun clearManifest()

  fun hasModule(address: ContentAddress): Boolean

  fun retrieveModule(address: ContentAddress): Module?

  fun insertModule(address: ContentAddress, module: Module)

  fun removeModule(address: ContentAddress)

  fun hasProvider(address: ContentAddress): Boolean

  fun getProvider(address: ContentAddress): Provider?

  fun setProvider(address: ContentAddress, provider: Provider)

  fun clearProvider(address: ContentAddress)
}
