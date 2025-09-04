load("//lib:commands.bzl", "commands", _COMMANDS = "ATTRS")
load("//lib:download.bzl", "download", _DOWNLOAD = "ATTRS")
load("//lib:patch.bzl", "patch", _PATCH = "ATTRS")
load("//lib:build.bzl", "build", _BUILD = "ATTRS")
load("//lib:links.bzl", "links", _LINKS = "ATTRS")
load("//lib:workspace.bzl", "write")

visibility("//download/...")

DOC = """Download a single file.

```py
download_file = use_repo_rule("@download_utils//download/file:defs.bzl", "download_file")
download_file(
    name = "file",
    output = "executable",
    executable = True,
    urls = ["https://some.thing/executable-amd64-linux"],
)
```
"""

ATTRS = _COMMANDS | _DOWNLOAD | _PATCH | _BUILD | _LINKS | {
    "build": attr.label(
        doc = "The template for the `BUILD.bazel` file.",
        default = ":BUILD.tmpl.bazel",
    ),
}

def implementation(rctx):
    canonical = {a: getattr(rctx.attr, a) for a in ATTRS} | {"name": rctx.name}

    canonical |= download(rctx)
    canonical |= build(rctx)
    canonical |= patch(rctx)
    canonical |= links(rctx)
    canonical |= commands(rctx)

    return write(rctx, canonical)

file = repository_rule(
    doc = DOC,
    implementation = implementation,
    attrs = ATTRS,
)
