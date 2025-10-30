interface Provider {
  val content: Map<Platform, Array<Byte>>?
  val metadata: ProviderMetadata
}

data class ProviderContent(val content: Map<Platform, Array<Byte>>) {
  init {
    for (platform in Platform.values()) {
      require(content.containsKey(platform)) {
        "Provider content must contain data for all platforms, missing: $platform"
      }
    }
  }
}
