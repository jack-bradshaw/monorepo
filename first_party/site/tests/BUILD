load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "test_harness",
    testonly = True,
    srcs = ["TestHarness.kt"],
    data = [
        "//first_party/site",
    ],
    deps = [
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
    name = "menu_navigation_test",
    size = "medium",
    srcs = ["MenuNavigationTest.kt"],
    shard_count = 34,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.MenuNavigationTest",
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
    name = "appearance_test",
    size = "medium",
    srcs = ["AppearanceTest.kt"],
    data = [
        "//first_party/site/tests/goldens",
    ],
    shard_count = 47,
    tags = ["requires-network"],
    test_class = "com.jackbradshaw.site.tests.AppearanceTest",
    deps = [
        ":test_harness",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
