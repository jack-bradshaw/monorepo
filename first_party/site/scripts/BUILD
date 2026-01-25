sh_library(
    name = "webp_converter",
    srcs = ["webp_converter.sh"],
    visibility = ["//first_party/site/scripts/tests:__pkg__"],
)

sh_binary(
    name = "generate_gallery_item_webp",
    srcs = ["generate_gallery_item_webp.sh"],
    visibility = ["//first_party/site/scripts/tests:__pkg__"],
    deps = [":webp_converter"],
)

sh_binary(
    name = "generate_decoration_webp",
    srcs = ["generate_decoration_webp.sh"],
    visibility = ["//first_party/site/scripts/tests:__pkg__"],
    deps = [":webp_converter"],
)
