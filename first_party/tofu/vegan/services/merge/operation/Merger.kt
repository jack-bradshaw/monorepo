package services.merge.operation

import dataaccess.unpacked.UnpackedFirmTofu

interface Merger {
  fun merge(firmTofus: Set<UnpackedFirmTofu>): UnpackedFirmTofu
}
