interface Exporter {
  fun export(firmTofu: PackedFirmTofu, destination: Path)
}
