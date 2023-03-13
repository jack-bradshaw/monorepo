load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_test(
    name = "DispatchersTest",
    srcs = [
        "DispatchersTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/kmonkey/coroutines",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlin_kotlin_test",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
