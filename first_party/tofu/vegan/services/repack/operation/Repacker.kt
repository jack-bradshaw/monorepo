package services.repack.operation

import dataaccess.packed.PackedFirmTofu
import dataaccess.unpacked.UnpackedFirmTofu

interface Repacker {
  fun repack(unzipped: UnpackedFirmTofu): PackedFirmTofu
}
