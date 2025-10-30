package 

import dataaccess.unpacked.UnpackedFirmTofu
import model.address.ContentAddress

interface RootService {
  fun setRoot(firmTofu: UnpackedFirmTofu, root: ContentAddress)

  fun clearRoot(firmTofu: UnpackedFirmTofu)
}
