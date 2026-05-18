load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/closet:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "observable",
    srcs = [
        "ObservableClosable.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "//first_party/closet/suspending",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["ObservableClosableTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":observable",
        "//first_party/closet/suspending:test",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
