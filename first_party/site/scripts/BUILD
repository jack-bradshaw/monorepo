load("@rules_shell//shell:sh_library.bzl", "sh_library")
load("//first_party/bash_runfiles:shell.bzl", "sh_binary_with_runfiles")

sh_library(
    name = "webp_converter",
    srcs = ["webp_converter.sh"],
    visibility = ["//first_party/site/scripts/tests:__pkg__"],
)

sh_binary_with_runfiles(
    name = "generate_gallery_item_webp",
    src = "generate_gallery_item_webp.sh",
    data = [":webp_converter"],
    visibility = ["//first_party/site/scripts/tests:__pkg__"],
    deps = [":webp_converter"],
)

sh_binary_with_runfiles(
    name = "generate_decoration_webp",
    src = "generate_decoration_webp.sh",
    data = [":webp_converter"],
    visibility = ["//first_party/site/scripts/tests:__pkg__"],
    deps = [":webp_converter"],
)
