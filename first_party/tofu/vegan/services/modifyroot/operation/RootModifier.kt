package services.modifyroot.operation

import dataaccess.unpacked.UnpackedFirmTofu
import model.address.ContentAddress

interface RootModifier {
  fun setRoot(firmTofu: UnpackedFirmTofu, root: ContentAddress)

  fun removeRoot(firmTofu: UnpackedFirmTofu)
}
