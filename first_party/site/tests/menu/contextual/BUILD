load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

filegroup(
    name = "goldens",
    testonly = True,
    srcs = glob(["goldens/*.jpg"]),
)

kt_jvm_test(
    name = "contextual_menu_appearance_test",
    size = "medium",
    srcs = ["ContextualMenuAppearanceTest.kt"],
    data = [
        ":goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 26,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.menu.contextual.ContextualMenuAppearanceTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/menu:appearance_base",
    ],
)

kt_jvm_test(
    name = "contextual_menu_behaviour_test",
    size = "medium",
    srcs = ["ContextualMenuBehaviourTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 16,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.menu.contextual.ContextualMenuBehaviourTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/menu:behaviour_base",
    ],
)

kt_jvm_test(
    name = "contextual_menu_presence_test",
    size = "medium",
    srcs = ["ContextualMenuPresenceTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.menu.contextual.ContextualMenuPresenceTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests:page_subject",
        "//first_party/site/tests/menu:presence_base",
    ],
)
