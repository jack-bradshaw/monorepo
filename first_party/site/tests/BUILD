load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/publicity:defs.bzl", "internal")

PUBLICITY = internal()

kt_jvm_library(
    name = "page_extensions",
    testonly = True,
    srcs = ["Page.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
    ],
)

kt_jvm_library(
    name = "test_harness",
    testonly = True,
    srcs = ["TestHarness.kt"],
    data = ["//first_party/site"],
    visibility = PUBLICITY,
    deps = [
        ":page_extensions",
        "@bazel_tools//tools/java/runfiles",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:io_ktor_ktor_http_jvm",
        "@com_jackbradshaw_maven//:io_ktor_ktor_server_core_jvm",
        "@com_jackbradshaw_maven//:io_ktor_ktor_server_host_common_jvm",
        "@com_jackbradshaw_maven//:io_ktor_ktor_server_netty_jvm",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_library(
    name = "page_subject",
    testonly = True,
    srcs = ["PageSubject.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
    ],
)

kt_jvm_test(
    name = "menu_test",
    size = "medium",
    srcs = ["MenuTest.kt"],
    data = [
        "//first_party/site/tests/goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 34,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.MenuTest",
    deps = [
        ":page_subject",
        ":test_harness",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "content_navigation_test",
    size = "medium",
    srcs = ["ContentNavigationTest.kt"],
    data = ["@playwright//:chromium-headless-shell"],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 14,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.ContentNavigationTest",
    deps = [
        ":page_subject",
        ":test_harness",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "content_appearance_test",
    size = "medium",
    srcs = ["ContentAppearanceTest.kt"],
    data = [
        "//first_party/site/tests/goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 15,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.ContentAppearanceTest",
    deps = [
        ":test_harness",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "cookie_consent_test",
    size = "medium",
    srcs = ["CookieConsentTest.kt"],
    data = [
        "//first_party/site/tests/goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.CookieConsentTest",
    deps = [
        ":test_harness",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
