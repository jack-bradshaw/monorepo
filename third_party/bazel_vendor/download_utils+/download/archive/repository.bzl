load("//lib:commands.bzl", "commands", _COMMANDS = "ATTRS")
load("//lib:download_and_extract.bzl", "download_and_extract", _DOWNLOAD_AND_EXTRACT = "ATTRS")
load("//lib:patch.bzl", "patch", _PATCH = "ATTRS")
load("//lib:build.bzl", "build", _BUILD = "ATTRS")
load("//lib:links.bzl", "links", _LINKS = "ATTRS")
load("//lib:workspace.bzl", "write")

visibility("//download/...")

DOC = """Download an archive and extract the contents.

```py
download_archive = use_repo_rule("@download_utils//download/archive:defs.bzl", "download_archive")
download_archive(
    name = "archive",
    urls = ["https://some.thing/archive.tar"],
)
```
"""

ATTRS = _COMMANDS | _DOWNLOAD_AND_EXTRACT | _PATCH | _BUILD | _LINKS | {
    "extension": attr.string(
        doc = "The extension of the archive when not available from the URL.",
        values = [".zip", ".tar", ".tar.gz", ".tar.bz2", ".tar.xz", ".tar.zst"],
    ),
}

def implementation(rctx):
    canonical = {a: getattr(rctx.attr, a) for a in ATTRS} | {"name": rctx.name}

    canonical |= download_and_extract(rctx, extension = rctx.attr.extension)
    canonical |= build(rctx)
    canonical |= patch(rctx)
    canonical |= links(rctx)
    canonical |= commands(rctx)

    return write(rctx, canonical)

archive = repository_rule(
    doc = DOC,
    implementation = implementation,
    attrs = ATTRS,
)
