interface NativeTofu {
  fun executeCommand(args: Array<String>, environmentalVariables: Map<String, String>): String
}

enum class Operation(private val rootModuleLocation) {
  data class Init(private val rootModuleLocation: Path) : SupportedOperation()
  data class Plan(private val rootModuleLocation: Path) : SupportedOperation(),
  APPLY,
  TEST,
  VALIDATE
}