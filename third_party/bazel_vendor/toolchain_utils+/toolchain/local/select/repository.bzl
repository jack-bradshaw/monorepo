load("//toolchain/repository:root.bzl", "root")
load(":resolve.bzl", resolve = "value")

visibility("//toolchain/...")

DOC = "Selects and symlinks a repository based on the local machine triplet."

ATTRS = {
    "map": attr.label_keyed_string_dict(
        doc = """A map of repository labels against the host triplet:

```py
toolchain_local_select(
    name = "abc",
    map = {
        "@abc-arm64-linux-gnu": "arm64-linux-gnu",
        "@abc-arm64-linux-musl": "arm64-linux-musl",
    },
)
```
""",
        mandatory = True,
        allow_empty = False,
    ),
    "triplet": attr.string(
        doc = "Overrides local machine triplet.",
    ),
    "no_match_error": attr.string(
        doc = """Error message to raise when no match is found in map.

    Can use the `{triplet}` replacement to show the resolved local triplet.""",
        default = "No repository match found for `{triplet}`: {map}",
    ),
}

def implementation(rctx):
    label = resolve(rctx.attr.map, no_match_error = rctx.attr.no_match_error)
    path = root(rctx, label)
    if not path:
        fail("Missing `{}` for `{}`".format(label, rctx.name))

    rctx.delete(".")
    rctx.symlink(path, ".")

select = repository_rule(
    doc = DOC,
    implementation = implementation,
    attrs = ATTRS,
    local = True,
    configure = True,
)
