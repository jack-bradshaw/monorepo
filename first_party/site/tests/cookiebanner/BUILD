load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

filegroup(
    name = "goldens",
    testonly = True,
    srcs = glob(["goldens/*.jpg"]),
)

kt_jvm_test(
    name = "cookie_banner_appearance_test",
    size = "medium",
    srcs = ["CookieBannerAppearanceTest.kt"],
    data = [
        ":goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 5,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.cookiebanner.CookieBannerAppearanceTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests:page_extensions",
        "//first_party/site/tests:screendiffer",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "cookie_banner_behaviour_test",
    size = "medium",
    srcs = ["CookieBannerBehaviourTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 2,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.cookiebanner.CookieBannerBehaviourTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests:page_extensions",
        "//first_party/site/tests:page_subject",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "cookie_banner_presence_test",
    size = "medium",
    srcs = ["CookieBannerPresenceTest.kt"],
    data = [
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 3,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.cookiebanner.CookieBannerPresenceTest",
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests:page_extensions",
        "//first_party/site/tests:page_subject",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
