class WorkspaceGeneratorSimplex(
    private val random: Random,
) : WorkspaceGenerator {

  override suspend fun generateTemporaryDirectory(): Path {
    while (true) {
      val random = random.nextInt().toString()
      val path = Path(System.getProperty(TEMP_DIR_PROPERTY)).resolve(TEMP_DIR_SUBPATH)
      if (Files.exists(path)) continue
      Files.createDirectories(path)
      return path
    }
  }

  companion object {
    private val TEMP_DIR_PROPERTY = "java.io.tmpdir"
    private val TEMP_DIR_SUBPATH = Path("vegan")
  }
}
