load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

filegroup(
    name = "goldens",
    testonly = True,
    srcs = glob(["goldens/*.jpg"]),
)

kt_jvm_test(
    name = "secondary_menu_appearance_test",
    size = "medium",
    srcs = ["SecondaryMenuAppearanceTest.kt"],
    data = [
        ":goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 44,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.menu.secondary.SecondaryMenuAppearanceTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/menu:appearance_base",
    ],
)

kt_jvm_test(
    name = "secondary_menu_behaviour_test",
    size = "medium",
    srcs = ["SecondaryMenuBehaviourTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 24,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.menu.secondary.SecondaryMenuBehaviourTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/menu:behaviour_base",
    ],
)

kt_jvm_test(
    name = "secondary_menu_presence_test",
    size = "medium",
    srcs = ["SecondaryMenuPresenceTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.menu.secondary.SecondaryMenuPresenceTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests:page_subject",
        "//first_party/site/tests/menu:presence_base",
    ],
)
