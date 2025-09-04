load("//toolchain/repository:root.bzl", "root")

visibility("//toolchain/export/...")

DOC = "Symlinks a repository to another."

ATTRS = {
    "target": attr.label(
        doc = "The repository to symlink to.",
        mandatory = True,
    ),
}

def implementation(rctx):
    label = rctx.attr.target
    path = root(rctx, label)
    if not path:
        fail("Can only symlink repository labels.".format(label))
    rctx.delete(".")
    rctx.symlink(path, ".")

symlink = repository_rule(
    doc = DOC,
    attrs = ATTRS,
    implementation = implementation,
    configure = True,
    local = True,
)
