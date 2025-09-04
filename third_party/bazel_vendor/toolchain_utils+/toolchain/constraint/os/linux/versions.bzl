load("@local//:triplet.bzl", "TRIPLET")

visibility("//toolchain/...")

LOCAL = TRIPLET.os.version and TRIPLET.os.version.value

# TODO: figure out a way to generate Linux versions
# Manually updating from https://en.wikipedia.org/wiki/Linux_kernel_version_history
# Only need the _latest_ patch version for each
LTS = (
    "4.4.302",
    "4.14.325",
    "4.19.294",
    "5.4.256",
    "5.10.194",
    "5.15.131",
)

VERSIONS = LTS + tuple([LOCAL] if LOCAL != None else [])
