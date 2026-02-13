load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "appearance_base",
    testonly = True,
    srcs = ["BaseMenuAppearanceTest.kt"],
    visibility = ["//first_party/site/tests:__subpackages__"],
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests:page_extensions",
        "//first_party/site/tests:screendiffer",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_library(
    name = "behaviour_base",
    testonly = True,
    srcs = ["BaseMenuBehaviourTest.kt"],
    visibility = ["//first_party/site/tests:__subpackages__"],
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests:page_extensions",
        "//first_party/site/tests:page_subject",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_library(
    name = "presence_base",
    testonly = True,
    srcs = ["BaseMenuPresenceTest.kt"],
    visibility = ["//first_party/site/tests:__subpackages__"],
    deps = [
        "//first_party/site/tests:instrumentation",
        "//first_party/site/tests:page_extensions",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_microsoft_playwright_playwright",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
