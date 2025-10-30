interface PropertyService {
  fun isCorrect(target: UnpackedFirmTofu): Boolean

  fun isComplete(target: UnpackedFirmTofu): Boolean

  fun isRunnable(target: UnpackedFirmTofu): Boolean

  fun isMinimal(target: UnpackedFirmTofu): Boolean
}
