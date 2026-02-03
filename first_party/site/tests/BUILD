load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "page_extensions",
    testonly = True,
    srcs = ["Page.kt"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
    ],
)

kt_jvm_library(
    name = "test_harness",
    testonly = True,
    srcs = ["TestHarness.kt"],
    data = [
        "//first_party/site",
    ],
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
    deps = [
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
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
    shard_count = 50,
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
    name = "content_behaviour_test",
    size = "medium",
    srcs = ["ContentBehaviourTest.kt"],
    data = [
        "//first_party/site/tests/goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 15,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.ContentBehaviourTest",
    deps = [
        ":page_subject",
        ":test_harness",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "menu_appearance_test",
    size = "medium",
    srcs = ["MenuAppearanceTest.kt"],
    data = [
        "//first_party/site/tests/goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 10,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.MenuAppearanceTest",
    deps = [
        ":page_subject",
        ":test_harness",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "menu_behaviour_test",
    size = "medium",
    srcs = ["MenuBehaviourTest.kt"],
    data = [
        "//first_party/site/tests/goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    shard_count = 10,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.MenuBehaviourTest",
    deps = [
        ":page_subject",
        ":test_harness",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "cookie_banner_appearance_test",
    size = "medium",
    srcs = ["CookieBannerAppearanceTest.kt"],
    data = [
        "//first_party/site/tests/goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.CookieBannerAppearanceTest",
    deps = [
        ":test_harness",
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
        "//first_party/site/tests/goldens",
        "@playwright//:chromium-headless-shell",
    ],
    env = {
        "PLAYWRIGHT_BROWSERS_PATH": "$(rootpath @playwright//:chromium-headless-shell)/../",
    },
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.CookieBannerBehaviourTest",
    deps = [
        ":test_harness",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
