load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

filegroup(
    name = "goldens",
    testonly = True,
    srcs = glob(["goldens/*.jpg"]),
)

kt_jvm_test(
    name = "other_content_appearance_test",
    size = "medium",
    srcs = ["OtherContentAppearanceTest.kt"],
    data = [
        ":goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 12,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.content.other.OtherContentAppearanceTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/content:appearance_base",
    ],
)

kt_jvm_test(
    name = "other_content_behaviour_test",
    size = "medium",
    srcs = ["OtherContentBehaviourTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.content.other.OtherContentBehaviourTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/content:behaviour_base",
    ],
)

kt_jvm_test(
    name = "other_content_expansion_test",
    size = "medium",
    srcs = ["OtherContentExpansionTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 1,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.content.other.OtherContentExpansionTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/content:expansion_base",
    ],
)
