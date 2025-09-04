load(":separator.bzl", "SEPARATOR")

visibility("//download/...")

ATTRS = {
    "commands": attr.string_list_dict(
        doc = """A collection of commands to run on the downloaded resources.

```py
download_archive(
    commands = {
        # Make a script executable in the unpacked archive
        "chmod": [
            "$(location @coreutils//:entrypoint)",
            "chmod",
            "u+x",
            "some-script.sh",
        ],
    },
    tools = [
        # A hermetically downloaded `coreutils` multi-call executable
        "@coreutils//:entrypoint",
    ],
)
```

It is **strongly** recommended to use hermetically provided executables in the commands.
""",
    ),
    "tools": attr.label_list(
        doc = """Labels to hermetic executable files.

The labels often point to downloaded executables.

Each tool can be resolved in the `commands` attribute via the `$(location )` helper.
""",
        cfg = "exec",
    ),
    "timeout": attr.int(
        doc = "Seconds before a command execution is killed.",
        default = 600,
    ),
}

def commands(rctx):
    """
    A mixin for `download` repository rules to run the post-download commands.

    Args:
        rctx: The download repository context.

    Returns:
        A map of canonical arguments
    """
    if not rctx.attr.commands:
        return {}

    def _digits(s):
        for c in s.elems():
            if c < "0" or "9" < c:
                return False
        return True

    def _workspace(label):
        name, _ = rctx.name.rsplit(SEPARATOR, 1)
        workspace = label.workspace_name
        if not workspace:
            return workspace

        # Bazel 8-
        prefix = name + SEPARATOR
        if workspace.startswith(prefix):
            return workspace.removeprefix(prefix)

        # Bazel 8+
        prefix, _ = workspace.rsplit(SEPARATOR, 1)
        prefix += SEPARATOR
        if _digits(prefix.removeprefix(name).removesuffix(SEPARATOR)):
            return workspace.removeprefix(prefix)

        fail("Failed to calculate `commands` label workspace for {} within {}".format(label, rctx.name))

    def _label(label):
        workspace = _workspace(label)
        package = label.package
        name = label.name
        if not package and name == workspace:
            return "@{}".format(workspace)
        elif package == name:
            return "//{}".format(package)
        elif workspace:
            return "@{}//{}:{}".format(workspace, package, name)
        else:
            return "//{}:{}".format(package, name)

    tools = {_label(t): rctx.path(t) for t in rctx.attr.tools}

    def _location(arg):
        if not arg.startswith("$(location "):
            return arg

        arg = arg.removeprefix("$(location ")
        arg = arg.removesuffix(")")

        if arg not in tools:
            fail("No hermetic tool provided for {}. Add the tool to the download repository rule `tools. Available tools are: {}".format(arg, ",".join(tools.keys())))

        return tools[arg]

    # Run the commands
    for progress, command in rctx.attr.commands.items():
        rctx.report_progress(progress)

        command = [_location(arg) for arg in command]

        result = rctx.execute(command, timeout = rctx.attr.timeout, environment = {"PATH": ""})
        if result.return_code != 0:
            fail("Failed to {}: {}\n{}".format(progress, " ".join([str(c) for c in command]), result.stderr))

    return {}
