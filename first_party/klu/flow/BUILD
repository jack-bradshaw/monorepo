load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "flow",
    srcs = [
        "Annotations.kt",
        "Operators.kt",
    ],
    visibility = [
        "//first_party:__subpackages__",
    ],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "test",
    srcs = [
        "OperatorsTest.kt",
    ],
    test_class = "com.jackbradshaw.klu.flow.OperatorsTest",
    deps = [
        "//first_party/klu/flow",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
