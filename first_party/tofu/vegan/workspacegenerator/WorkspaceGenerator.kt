/** Generates temporary paths. */
interface WorkspaceGenerator {
  /**
   * Generates a new temporary directory.
   *
   * The returned directory is guaranteed to exist and contain no files.
   */
  suspend fun generateTemporaryDirectory(): Path
}
