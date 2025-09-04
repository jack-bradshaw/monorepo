load("@local//:triplet.bzl", "TRIPLET")

visibility("//toolchain/...")

LOCAL = TRIPLET.libc.version and TRIPLET.libc.version.value

# TODO: figure out a way to generate Darwin versions

VERSIONS = tuple([LOCAL] if LOCAL != None else [])
