load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/klu:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "concurrency",
    srcs = ["Once.kt"],
    visibility = PUBLICITY,
    deps = ["@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core"],
)

kt_jvm_test(
    name = "test",
    srcs = ["OnceTest.kt"],
    test_class = "com.jackbradshaw.klu.concurrency.OnceTest",
    deps = [
        "//first_party/klu/concurrency",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
