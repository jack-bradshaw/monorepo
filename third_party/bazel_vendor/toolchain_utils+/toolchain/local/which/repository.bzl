load("//toolchain:separator.bzl", "SEPARATOR")
load("//toolchain/local/select:resolve.bzl", resolve = "value")

visibility("//toolchain/...")

DOC = """Creates a repository that provides a binary target wrapping a local binary found on `PATH`.

The resulting repository has a `toolchain_symlink_path` target which can be used with the native `toolchain` rule to expose the local binary as a toolchain.

Assuming a repository created as `name = "echo"`, by default the `echo` binary will be search for an the nested target will be named `:echo`.

Consuming this target as a toolchain is trivial:

```py
toolchain(
    name = "local",
    toolchain = "@echo",
)
```
"""

ATTRS = {
    "program": attr.string(
        doc = "The name of the binary to find on `PATH`.",
    ),
    "target": attr.string(
        doc = "The name of the Bazel target to expose around the binary.",
    ),
    "basename": attr.string(
        doc = "The basename for the symlink, which defaults to `program`",
    ),
    "variable": attr.string(
        doc = "The variable name for Make or the execution environment.",
    ),
    "build": attr.label(
        doc = "The template that is expanded into the `BUILD.bazel`.",
        default = ":BUILD.tmpl.bazel",
        allow_single_file = True,
    ),
    "entrypoint": attr.label_keyed_string_dict(
        doc = "An executable entrypoint template for hermetic rulesets.",
        default = {
            ":entrypoint.tmpl.bat": "windows",
            ":entrypoint.tmpl.sh": "//conditions:default",
        },
        allow_files = [".bat", ".sh"],
        allow_empty = False,
        cfg = "exec",
    ),
    "launcher": attr.label(
        doc = "An executable that can be symlinked and will launch an adjacent script.",
        default = "@launcher",
        allow_single_file = True,
        executable = True,
        cfg = "exec",
    ),
}

def implementation(rctx):
    name = rctx.attr.name.rsplit(SEPARATOR, 1)[1]
    program = rctx.attr.program or name.removeprefix("which-")
    target = rctx.attr.target or name
    basename = rctx.attr.basename or program
    variable = rctx.attr.variable or basename.upper()
    entrypoint = resolve(rctx.attr.entrypoint)

    path = rctx.which(program)
    if not path:
        fail("Cannot find `{}` on `PATH`".format(program))

    _, extension = rctx.path(entrypoint).basename.rsplit(".", 1)
    rctx.template("entrypoint.{}".format(extension), entrypoint, {
        "{{path}}": str(path.realpath),
    }, executable = True)

    rctx.symlink(rctx.attr.launcher, "entrypoint")

    rctx.template("BUILD.bazel", rctx.attr.build, {
        "{{target}}": target,
        "{{program}}": program,
        "{{basename}}": basename,
        "{{path}}": str(path.realpath),
        "{{variable}}": variable,
    }, executable = False)

which = repository_rule(
    doc = DOC,
    implementation = implementation,
    attrs = ATTRS,
    configure = True,
    environ = [
        "PATH",
    ],
)
