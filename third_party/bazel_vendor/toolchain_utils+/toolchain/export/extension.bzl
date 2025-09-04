load("@bazel_features//:features.bzl", "bazel_features")
load("//toolchain/export/symlink:repository.bzl", _ATTRS = "ATTRS", _DOC = "DOC", _symlink = "symlink")

visibility("//toolchain/export/...")

DOC = """An extension to export local repositories to other Bazel modules.

A common pattern for Bazel modules is to download hermetic tooling, often with `download_utils`.

The downloaded repositories are local in scope to the current Bazel module.

The locally downloaded repositories can provide hermetic, executable files that are useful for repository rules.

The hermetic, executable files are often useful for other Bazel modules to perform hermetic repositories.

This module provides a way to "export" those locally visible repositories to other Bazel modules.

For example, a `@python` Bazel module could provide:

```py
download_archive = use_repo_rule("@download_utils//download:defs.bzl", "download_archive")
download_archive(
    name = "python-amd64-linux-gnu",
    ...,
)

toolchain_select = use_extension("@toolchain_utils//toolchain/select:defs.bzl", "toolchain_select")
toolchain_select(
    name = "python-local",
    map = {
        "amd64-linux-gnu": "@python-amd64-linux-gnu",
    }
)

export = use_extension("@toolchain_utils//toolchain/export:defs.bzl", "export")
export.symlink(
    name = "python",
    target = "@python-local",
)
use_repo(export, "python")

some_hermetic_repository_rule(
    name = "placeholder",
    python = "@python//:entrypoint",
)
```

The _same_ downloaded Python interpreter can be used in another Bazel module:

```py
export = use_extension("@toolchain_utils//toolchain/export:defs.bzl", "export")
use_repo(export, "python")

some_other_hermetic_repository_rule(
    name = "placeholder",
    python = "@python//:entrypoint",
)
```

The de-facto `:entrypoint` label is often used for the executable file entrypoint that can be used with `rctx.execute`.

The exported repository names are global across the module extension.
"""

symlink = tag_class(
    doc = _DOC,
    attrs = {
        "name": attr.string(doc = "Name of the generated repository.", mandatory = True),
    } | _ATTRS,
)

TAGS = {
    "symlink": symlink,
}

def implementation(mctx):
    symlinks = {}

    for mod in mctx.modules:
        for tag in mod.tags.symlink:
            attrs = {a: getattr(tag, a) for a in _ATTRS}
            if tag.name not in symlinks:
                symlinks[tag.name] = (mod, attrs)
                continue

            m, a = symlinks[tag.name]
            if a != attrs:
                fail("`{}@{}` exports `{}` but it is already exported with different attributes by `{}@{}`".format(mod.name, mod.version or "<version>", tag.name, m.name, m.version or "<version>"))

    for name, (_, attrs) in symlinks.items():
        _symlink(name = name, **attrs)

    if bazel_features.external_deps.extension_metadata_has_reproducible:
        return mctx.extension_metadata(reproducible = True)
    else:
        return None

export = module_extension(
    doc = DOC,
    implementation = implementation,
    tag_classes = TAGS,
)
