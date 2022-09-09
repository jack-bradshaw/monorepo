load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_test(
    name = "ConfigExtTest",
    srcs = [
        "ConfigExtTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/omnixr/config:proto_extensions",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
