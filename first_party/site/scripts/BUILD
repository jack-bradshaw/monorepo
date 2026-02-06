load("@rules_shell//shell:sh_library.bzl", "sh_library")
load("//first_party/bash_runfiles:shell.bzl", "sh_binary_with_runfiles")
load("//first_party/publicity:defs.bzl", "internal")

PUBLICITY = internal()

sh_library(
    name = "webp_converter",
    srcs = ["webp_converter.sh"],
    visibility = PUBLICITY,
)

sh_binary_with_runfiles(
    name = "generate_gallery_item_webp",
    src = "generate_gallery_item_webp.sh",
    data = [":webp_converter"],
    visibility = PUBLICITY,
    deps = [":webp_converter"],
)

sh_binary_with_runfiles(
    name = "generate_decoration_webp",
    src = "generate_decoration_webp.sh",
    data = [":webp_converter"],
    visibility = PUBLICITY,
    deps = [":webp_converter"],
)
