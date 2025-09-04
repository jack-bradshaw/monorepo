visibility("//toolchain/...")

DOC = """Creates a executable symlink to a binary target file.

This rule can be used to symlink a executable target and change the basename. Useful for multi-call binaries.

Often used with downloaded binary targets:

```py
toolchain_symlink_target(
    name = "something",
    basename = "ls",
    target = ":busybox",
)
```
"""

ATTRS = {
    "target": attr.label(
        doc = "The binary file to symlink.",
        mandatory = True,
        allow_files = True,
        executable = True,
        cfg = "exec",
    ),
    "basename": attr.string(
        doc = "The basename for the symlink, which defaults to `name`",
    ),
    "_windows": attr.label(
        providers = [platform_common.ConstraintValueInfo],
        default = "//toolchain/constraint/os:windows",
    ),
}

def implementation(ctx):
    basename = ctx.attr.basename or ctx.label.name
    windows = ctx.attr._windows[platform_common.ConstraintValueInfo]

    target = ctx.executable.target
    extension = target.extension
    if extension in (".bat", ".cmd", ".exe"):
        basename = basename + extension
    elif extension in ("bat", "cmd", "exe"):
        basename = "{}.{}".format(basename, extension)
    elif not extension and "." not in basename and ctx.target_platform_has_constraint(windows):
        basename = "{}.exe".format(basename)

    executable = ctx.actions.declare_file("{}/{}".format(ctx.label.name, basename))
    ctx.actions.symlink(
        output = executable,
        target_file = target,
        is_executable = True,
    )

    runfiles = ctx.runfiles([executable, ctx.executable.target])
    runfiles = runfiles.merge(ctx.attr.target.default_runfiles)

    return DefaultInfo(
        executable = executable,
        files = depset([executable]),
        runfiles = runfiles,
    )

toolchain_symlink_target = rule(
    doc = DOC,
    attrs = ATTRS,
    implementation = implementation,
    executable = True,
)

target = toolchain_symlink_target
