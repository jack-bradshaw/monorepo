load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

filegroup(
    name = "goldens",
    testonly = True,
    srcs = glob(["goldens/*.jpg"]),
)

kt_jvm_test(
    name = "primary_menu_appearance_test",
    size = "medium",
    srcs = ["PrimaryMenuAppearanceTest.kt"],
    data = [
        ":goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 50,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.menu.primary.PrimaryMenuAppearanceTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/menu:appearance_base",
    ],
)

kt_jvm_test(
    name = "primary_menu_behaviour_test",
    size = "medium",
    srcs = ["PrimaryMenuBehaviourTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 10,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.menu.primary.PrimaryMenuBehaviourTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests/menu:behaviour_base",
    ],
)

kt_jvm_test(
    name = "primary_menu_presence_test",
    size = "medium",
    srcs = ["PrimaryMenuPresenceTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.menu.primary.PrimaryMenuPresenceTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests:page_subject",
        "//first_party/site/tests/menu:presence_base",
    ],
)
