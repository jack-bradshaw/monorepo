using System;
using System.IO;
using System.Diagnostics;

class Entrypoint {
    static int Main() {
        string[] args = Environment.GetCommandLineArgs();

        // Determine the Batch script
        string stem = Path.GetFileName(args[0]);
        string directory = Path.GetDirectoryName(args[0]);
        string batch = Path.Combine(directory, string.Format("{0}.bat", stem));

        // Run the Batch script
        using (Process process = new Process()) {
            process.StartInfo.UseShellExecute = false;
            process.StartInfo.FileName = "cmd.exe";
            process.StartInfo.Arguments = string.Format("/C {0}", batch);
            process.Start();
            process.WaitForExit();
            return process.ExitCode;
        }
    }
}