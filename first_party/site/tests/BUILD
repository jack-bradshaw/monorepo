load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_binary", "kt_jvm_library", "kt_jvm_test")

package(default_visibility = ["//first_party/site/tests:__subpackages__"])

kt_jvm_library(
    name = "page_extensions",
    testonly = True,
    srcs = ["Page.kt"],
    deps = [
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
    ],
)

kt_jvm_library(
    name = "instrumentation",
    testonly = True,
    srcs = [
        "Instrumentation.kt",
    ],
    data = ["//first_party/site"],
    deps = [
        ":page_extensions",
        "@bazel_tools//tools/java/runfiles",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:io_ktor_ktor_http_jvm",
        "@com_jackbradshaw_maven//:io_ktor_ktor_server_core_jvm",
        "@com_jackbradshaw_maven//:io_ktor_ktor_server_host_common_jvm",
        "@com_jackbradshaw_maven//:io_ktor_ktor_server_netty_jvm",
    ],
)

kt_jvm_library(
    name = "screendiffer",
    testonly = True,
    srcs = ["Screendiffer.kt"],
    visibility = ["//first_party/site/tests:__subpackages__"],
    deps = [
        "@bazel_tools//tools/java/runfiles",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
    ],
)

kt_jvm_library(
    name = "page_subject",
    testonly = True,
    srcs = ["PageSubject.kt"],
    deps = [
        ":page_extensions",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
    ],
)
