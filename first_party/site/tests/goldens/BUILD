load("//first_party/publicity:defs.bzl", "internal")

PUBLICITY = internal()

filegroup(
    name = "goldens",
    testonly = True,
    srcs = glob(["*.png"]),
    visibility = PUBLICITY,
)
