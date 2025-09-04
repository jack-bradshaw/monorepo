load("@bazel_skylib//lib:paths.bzl", "paths")

visibility("//toolchain/...")

DOC = """Creates an executable symlink to a binary path.

This rule can be used to symlink a executable file outside of the workspace.

The external executable become part of the Bazel target graph.

```
toolchain_type(
    name = "type",
)

toolchain_symlink_path(
    name = "gcc-local",
    path = "/usr/bin/gcc",
)

toolchain_symlink_target(
    name = "gcc",
    target = "gcc-local",
)
```

_Commonly_, this target is not used directly and the `local.which` extension is used that looks up a binary on a path.
"""

ATTRS = {
    "path": attr.string(
        doc = "The path to a binary to symlink.",
        mandatory = True,
    ),
    "basename": attr.string(
        doc = "The basename for the symlink, which defaults to `name`",
    ),
    "variable": attr.string(
        doc = "The variable name for Make or the execution environment. Defaults to `basename.upper()`",
    ),
    "data": attr.label_list(
        doc = "Extra files that are needed at runtime.",
        allow_files = True,
    ),
    "_windows": attr.label(
        providers = [platform_common.ConstraintValueInfo],
        default = "//toolchain/constraint/os:windows",
    ),
}

def implementation(ctx):
    basename = ctx.attr.basename or ctx.label.name
    variable = ctx.attr.variable or basename.upper()
    windows = ctx.attr._windows[platform_common.ConstraintValueInfo]

    _, extension = paths.split_extension(ctx.attr.path)
    if extension in (".bat", ".cmd", ".exe"):
        basename = basename + extension
    elif not extension and "." not in basename and ctx.target_platform_has_constraint(windows):
        basename = "{}.exe".format(basename)

    filepath = "{}/{}".format(ctx.label.name, basename)
    if ctx.target_platform_has_constraint(windows):
        executable = ctx.actions.declare_file(filepath)

        args = ctx.actions.args()
        args.add("/c")
        args.add("mklink")
        args.add("/h")
        args.add(executable.path, format = '"%s"')
        args.add(ctx.attr.path, format = '"%s"')

        ctx.actions.run(
            outputs = [executable],
            executable = "cmd.exe",
            arguments = [args],
        )
    else:
        executable = ctx.actions.declare_symlink(filepath)
        ctx.actions.symlink(
            output = executable,
            target_path = ctx.attr.path,
        )

    runfiles = ctx.runfiles([executable])
    runfiles.merge_all([d[DefaultInfo].default_runfiles for d in ctx.attr.data])

    return DefaultInfo(
        executable = executable,
        files = depset([executable]),
        runfiles = runfiles,
    )

toolchain_symlink_path = rule(
    doc = DOC,
    attrs = ATTRS,
    implementation = implementation,
    provides = [DefaultInfo],
    executable = True,
)

path = toolchain_symlink_path
