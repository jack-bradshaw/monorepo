load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

filegroup(
    name = "goldens",
    testonly = True,
    srcs = glob(["goldens/*.jpg"]),
)

kt_jvm_test(
    name = "repository_content_appearance_test",
    size = "medium",
    srcs = ["RepositoryContentAppearanceTest.kt"],
    data = [
        ":goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 15,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.content.repository.RepositoryContentAppearanceTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/content:appearance_base",
    ],
)

kt_jvm_test(
    name = "repository_content_behaviour_test",
    size = "medium",
    srcs = ["RepositoryContentBehaviourTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.content.repository.RepositoryContentBehaviourTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/content:behaviour_base",
    ],
)

kt_jvm_test(
    name = "repository_content_expansion_test",
    size = "medium",
    srcs = ["RepositoryContentExpansionTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 2,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.content.repository.RepositoryContentExpansionTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/content:expansion_base",
    ],
)
