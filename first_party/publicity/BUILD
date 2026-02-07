load("//first_party/publicity:defs.bzl", "public")

PUBLICITY = public()

# Exported because defs.bzl represents the public API of this package.
exports_files(
    ["defs.bzl"],
    visibility = PUBLICITY,
)

alias(
    name = "conformance_check",
    actual = "//first_party/publicity/conformance/entrypoint:conformance_audit",
    visibility = PUBLICITY,
)
