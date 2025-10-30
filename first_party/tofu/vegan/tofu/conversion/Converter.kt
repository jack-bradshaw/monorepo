interface Compiler {
  fun pack(unpackedTofu: UnpackedTofu): PackedTofu

  fun unpack(packedTofu: PackedTofu): UnpackedTofu

  fun restructure(unpackedTofu: UnpackedTofu): RestructuredTofu

  fun restructure(packedTofu: PackedTofu): RestructuredTofu
}
