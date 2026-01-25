load("//first_party/rules_bats:defs.bzl", "bats_test")

package(default_visibility = ["//visibility:public"])

bats_test(
    name = "webp_converter_test",
    srcs = ["webp_converter_test.bats"],
    args = [],
    data = [
        "input_image.jpg",
        "//first_party/site/scripts:webp_converter",
        "//first_party/site/scripts/tests/goldens",
    ],
)

bats_test(
    name = "generate_gallery_item_webp_test",
    srcs = ["generate_gallery_item_webp_test.bats"],
    args = [],
    data = [
        "input_image.jpg",
        "//first_party/site/scripts:generate_gallery_item_webp",
        "//first_party/site/scripts/tests/goldens",
    ],
)

bats_test(
    name = "generate_decoration_webp_test",
    srcs = ["generate_decoration_webp_test.bats"],
    args = [],
    data = [
        "input_image.jpg",
        "//first_party/site/scripts:generate_decoration_webp",
        "//first_party/site/scripts/tests/goldens",
    ],
)
