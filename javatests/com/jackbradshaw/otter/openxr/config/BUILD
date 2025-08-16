load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "ConfigExtTest",
    srcs = [
        "ConfigExtTest.kt",
    ],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/config:proto_extensions",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
