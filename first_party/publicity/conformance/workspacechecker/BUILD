load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "checker",
    srcs = ["WorkspaceChecker.kt"],
    visibility = [
        "//first_party/publicity/conformance/runner:__pkg__",
    ],
    deps = ["//first_party/publicity/conformance/model"],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["WorkspaceCheckerImpl.kt"],
    deps = [
        ":checker",
        "//first_party/publicity/conformance/model",
        "//first_party/publicity/conformance/packagechecker:checker",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl_module",
    srcs = ["WorkspaceCheckerImplModule.kt"],
    visibility = [
        "//first_party/publicity/conformance:__subpackages__",
    ],
    deps = [
        ":checker",
        ":impl",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["WorkspaceCheckerTest.kt"],
    deps = [
        ":checker",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["WorkspaceCheckerImplTest.kt"],
    test_class = "com.jackbradshaw.publicity.conformance.workspacechecker.WorkspaceCheckerImplTest",
    deps = [
        ":impl",
        ":impl_module",
        ":test",
        "//first_party/publicity/conformance/model",
        "//first_party/publicity/conformance/packagechecker:impl_module",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)
