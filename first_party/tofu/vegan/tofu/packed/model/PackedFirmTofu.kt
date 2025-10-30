package dataaccess.packed

/** A packed firm Tofu archive. */
interface PackedFirmTofu {
  fun setContent(content: Sequence<Byte>)

  fun getContent(): Sequence<Byte>
}
