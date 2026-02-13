load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

filegroup(
    name = "goldens",
    testonly = True,
    srcs = glob(["goldens/*.jpg"]),
)

kt_jvm_test(
    name = "gallery_content_appearance_test",
    size = "medium",
    srcs = ["GalleryContentAppearanceTest.kt"],
    data = [
        ":goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 27,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.content.gallery.GalleryContentAppearanceTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/content:appearance_base",
    ],
)

kt_jvm_test(
    name = "gallery_content_behaviour_test",
    size = "medium",
    srcs = ["GalleryContentBehaviourTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.content.gallery.GalleryContentBehaviourTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/content:behaviour_base",
    ],
)

kt_jvm_test(
    name = "gallery_content_expansion_test",
    size = "medium",
    srcs = ["GalleryContentExpansionTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 3,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.content.gallery.GalleryContentExpansionTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/content:expansion_base",
    ],
)
