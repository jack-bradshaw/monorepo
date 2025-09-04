load("//lib:commands.bzl", "commands", _COMMANDS = "ATTRS")
load("//lib:download_and_extract.bzl", "download_and_extract", _DOWNLOAD_AND_EXTRACT = "ATTRS")
load("//lib:patch.bzl", "patch", _PATCH = "ATTRS")
load("//lib:build.bzl", "build", _BUILD = "ATTRS")
load("//lib:links.bzl", "links", _LINKS = "ATTRS")
load("//lib:workspace.bzl", "write")

visibility("//download/...")

DOC = """Downloads an unpacks the nested data from a Debian package.

To download and unpack the Debian package, use `download_archive`.  This would provide access to the control information for the package.

```py
download_deb = use_repo_rule("@download_utils//download/deb:defs.bzl", "download_deb")
download_deb(
    name = "deb",
    integrity = "sha256-vMiq8kFBwoSrVEE+Tcs08RvaiNp6MsboWlXS7p1clO0=",
    urls = ["https://some.thing/test_1.0-1_all.deb"],
)
```
"""

ATTRS = _COMMANDS | _DOWNLOAD_AND_EXTRACT | _PATCH | _BUILD | _LINKS

def implementation(rctx):
    canonical = {a: getattr(rctx.attr, a) for a in ATTRS} | {"name": rctx.name}

    canonical |= download_and_extract(rctx, nested = ("data.tar.xz", "data.tar.zst"), extension = ".deb")
    canonical |= build(rctx)
    canonical |= patch(rctx)
    canonical |= links(rctx)
    canonical |= commands(rctx)

    return write(rctx, canonical)

deb = repository_rule(
    doc = DOC,
    implementation = implementation,
    attrs = ATTRS,
)
