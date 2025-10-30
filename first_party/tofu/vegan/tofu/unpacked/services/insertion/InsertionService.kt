/**
 * Inserts modules/providers into firmtofu and updates the accordingly.
 *
 * After insertion, the inserted module/provider will exist in content addressable storage and the
 * source-address/content-address mappings of the manifest, but no metadata references will be
 * updated. These guarantees only apply to references in the inserted module/providers that have
 * been updated to use content addressing, and all native references will be ignored.
 *
 * No errors are raised when inserting modules/providers that reference modules/providers that are
 * not present, as this is necessary for progressive insertion, but doing so will create an
 * incomplete archive, and there may be implications when using the resultant firmtofu with other
 * systems.
 */
interface ContentService {
  /** Inserts [module] into [target], and updates metadata/manifest values to reflect the change. */
  fun insertModule(target: UnpackedTofu, module: Module)

  /**
   * Inserts [provider] into [target], and updates metadata/manifest values to reflect the change.
   */
  fun insertProvider(target: UnpackedTofu, module: Provider)
}
