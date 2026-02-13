load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

filegroup(
    name = "goldens",
    testonly = True,
    srcs = glob(["goldens/*.jpg"]),
)

kt_jvm_test(
    name = "journal_content_appearance_test",
    size = "medium",
    srcs = ["JournalContentAppearanceTest.kt"],
    data = [
        ":goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 33,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.content.journal.JournalContentAppearanceTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/content:appearance_base",
    ],
)

kt_jvm_test(
    name = "journal_content_behaviour_test",
    size = "medium",
    srcs = ["JournalContentBehaviourTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.content.journal.JournalContentBehaviourTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/content:behaviour_base",
    ],
)

kt_jvm_test(
    name = "journal_content_expansion_test",
    size = "medium",
    srcs = ["JournalContentExpansionTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 4,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.content.journal.JournalContentExpansionTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/content:expansion_base",
    ],
)
