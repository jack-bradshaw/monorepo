package services.query.operation

import dataaccess.unpacked.UnpackedFirmTofu

interface Querier {

  fun getManifestAsJson(target: UnpackedFirmTofu): String

  fun getManifestAsXml(target: UnpackedFirmTofu): String

  fun getArchiveVersion(target: UnpackedFirmTofu): Integer
}

data class EmergentProperties(
    val isCorrect: Boolean,
    val isComplete: Boolean,
    val isRunnable: Boolean,
    val isMinimal: Boolean,
)
