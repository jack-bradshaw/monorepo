load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "model",
    srcs = ["Workspace.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
)

kt_jvm_test(
    name = "model_test",
    srcs = ["WorkspaceTest.kt"],
    test_class = "com.jackbradshaw.publicity.conformance.model.WorkspaceTest",
    deps = [
        ":model",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)
