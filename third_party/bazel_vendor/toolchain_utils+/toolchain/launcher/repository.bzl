visibility("//toolchain/...")

DOC = """A launcher executable that will run a script via symlink or hardlink.

The `launcher` will take the zero argument, replace the extension with either `.bat` or `.sh` and run the corresponding script from the symlink/hardlink folder.

The purpose is to provide extension-less source file targets on Windows.
"""

ATTRS = {
    "cs": attr.label(
        doc = "C# source code for the launcher executable.",
        default = ":launcher.cs",
    ),
    "sh": attr.label(
        doc = "Shell script for the launcher executable.",
        default = ":launcher.sh",
    ),
    "build": attr.label(
        doc = "The `BUILD.bazel` source code.",
        default = ":BUILD.tmpl.bazel",
    ),
}

def csc(rctx):
    """Finds the C# compiler from common directories.

    Args:
      rctx: the repository context to perform local command execution with

    Returns:
      A Bazel path to the C# compiler
    """
    result = rctx.execute(("cmd.exe", "/c", "@echo.%SYSTEMROOT%"))
    if result.return_code != 0:
        fail("Failed to compile C# launcher: {}".format(result.stdout))
    root = rctx.path(result.stdout.strip())

    framework = root.get_child("Microsoft.NET/Framework64")
    if not framework.exists:
        fail("Failed to find .NET framework")

    for child in framework.readdir():
        csc = child.get_child("csc.exe")
        if csc.exists:
            return csc

    fail("Failed to find C# compiler")

def windows(rctx):
    compiler = csc(rctx)

    launcher = rctx.path("launcher.exe")
    source = rctx.path(rctx.attr.cs)
    source = str(source).replace("/", "\\")

    rctx.report_progress("Compiling C# launcher")
    result = rctx.execute((compiler, "/warnaserror", "/warn:4", "/nologo", "/out:{}".format(launcher), "/target:exe", "/optimize", source))
    if result.return_code != 0:
        fail("Failed to compile C# launcher: {}".format(result.stdout))

    rctx.symlink("launcher.exe", "launcher")

    rctx.file("launcher.bat", "@echo.Hello, world!", executable = False)

def posix(rctx):
    rctx.template("launcher", rctx.attr.sh, executable = True)
    rctx.file("launcher.sh", "echo 'Hello, world!'", executable = True)

def implementation(rctx):
    rctx.template("BUILD.bazel", rctx.attr.build, {
        "{{exports}}": repr(["launcher"]),
    }, executable = False)

    if "windows" in rctx.os.name:
        return windows(rctx)
    return posix(rctx)

launcher = repository_rule(
    doc = DOC,
    implementation = implementation,
    attrs = ATTRS,
    configure = True,
)
