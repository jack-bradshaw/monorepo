interface {
  fun getManifestAsJson(target: UnpackedFirmTofu): Json

  fun getManifestAsXml(target: UnpackedFirmTofu): Xml
}