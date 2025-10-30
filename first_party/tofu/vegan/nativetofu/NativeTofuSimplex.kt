import com.google.devtools.build.runfiles.Runfiles;

class TofuCliSimplex(
  private val clock: Clock,
  private val workspaces: Workspaces,
  private val runfiles: Runfiles
) : TofuCli, ExternalResourceHost {

  /** The path to export the CLI to for this process. Uses the current instant to ensure
   * consequitive processes operate in different directories.*/
  private val exportedCliPath by lazy {
    workspaces.generateTemporaryDirectory().resolve(EXPORTED_CLI_FILENAME)
  }
  
  override fun executeCommand(
    args: Array<String>,
    environmentalVariables: Map<String, String>
  ): String {
    if (!cliExported()) exportCli()
  }

  override fun cleanUp() {
    if (cliExported()) removeExportedCli()
  }

  private fun isCliExported() {
    return Files.exists(exportedCliPath)
  }

  private fun exportCli() {
    val runfile = runfiles.rlocation(CLI_RUNFILE_PATH)
    Files.copy(runfile, exportedCliPath)
    exportedCliPath.toFile().setExecutable(true)
  }

  companion object {
    private val EXPORTED_CLI_FILENAME = "tofu"

    // TODO not right get the real one
    private val CLI_RUNFILE_PATH = "com_jackbradshaw/first_party/otter/openxr/manifest/testgoldens"
  }
}

sealed class SupportedOperation(private val rootModuleLocation: Path) {
  class Init ,
  PLAN,
  APPLY,
  TEST,
  VALIDATE
}