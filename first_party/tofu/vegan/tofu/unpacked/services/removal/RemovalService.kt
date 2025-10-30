/**
 * Removes modules/providers from firmtofu and updates modules/metadata accordingly.
 *
 * After removal the removed module/provider will not exist in content addressable storage or the
 * source-address/content-address mappings of the manifest, and all references in metadata will be
 * bidirectional. These guarantees only apply to references in the inserted module/providers that
 * have been updated to use content addressing, and all native references will be ignored.
 *
 * No errors are raised when inserting modules/providers that reference modules/providers that are
 * not present, as this is necessary for progressive insertion, but doing so will create an
 * incomplete archive, and there may be implications when using the resultant firmtofu with other
 * systems.
 */
interface RemovalService {
  fun removeModule(target: UnpackedTofu, module: Module, retainMetadata: Boolean = false)

  fun removeModule(target: UnpackedTofu, address: ContentAddress, retainMetadata: Boolean = false)

  fun removeProvider(target: UnpackedTofu, module: Provider, retainMetadata: Boolean = false)
}
