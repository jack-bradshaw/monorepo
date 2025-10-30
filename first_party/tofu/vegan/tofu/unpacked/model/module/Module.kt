interface Module {
  val content: ModuleContent
  val metadata: ModuleMetadata
}

data class ModuleContent(val content: Map<String, Array<Byte>>)
