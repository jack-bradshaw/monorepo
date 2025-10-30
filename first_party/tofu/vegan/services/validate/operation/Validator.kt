package services.validate.operation

import dataaccess.unpacked.UnpackedFirmTofu

interface Validator {
  fun validate(firmTofu: UnpackedFirmTofu): Error?
}

class Error(val mesage: String)
