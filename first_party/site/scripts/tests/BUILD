load("//first_party/bash_runfiles:bats_test.bzl", "bats_test_with_runfiles")

package(default_visibility = ["//visibility:public"])

bats_test_with_runfiles(
    name = "webp_converter_test",
    src = "webp_converter_test.bats",
    args = [],
    data = [
        "input_image.jpg",
        "//first_party/site/scripts:webp_converter",
        "//first_party/site/scripts/tests/goldens",
    ],
)

bats_test_with_runfiles(
    name = "generate_gallery_item_webp_test",
    src = "generate_gallery_item_webp_test.bats",
    args = [],
    data = [
        "input_image.jpg",
        "//first_party/site/scripts:generate_gallery_item_webp",
        "//first_party/site/scripts:webp_converter",
        "//first_party/site/scripts/tests/goldens",
    ],
)

bats_test_with_runfiles(
    name = "generate_decoration_webp_test",
    src = "generate_decoration_webp_test.bats",
    args = [],
    data = [
        "input_image.jpg",
        "//first_party/site/scripts:generate_decoration_webp",
        "//first_party/site/scripts:webp_converter",
        "//first_party/site/scripts/tests/goldens",
    ],
)
