package services.unpack.operation

import dataaccess.packed.PackedFirmTofu
import dataaccess.unpacked.UnpackedFirmTofu

interface Unpacker {
  fun unpack(firmTofu: PackedFirmTofu): UnpackedFirmTofu
}
