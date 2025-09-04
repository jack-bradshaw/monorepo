load("@local//:triplet.bzl", "TRIPLET")

visibility("//toolchain/...")

LOCAL = TRIPLET.os.version and TRIPLET.os.version.value

# TODO: figure out a way to generate MacOS versions

VERSIONS = tuple([LOCAL] if LOCAL != None else [])
