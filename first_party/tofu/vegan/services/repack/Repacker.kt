interface Repacker {
  fun repack(unzipped: UnpackedFirmTofu): PackedFirmTofu
}
