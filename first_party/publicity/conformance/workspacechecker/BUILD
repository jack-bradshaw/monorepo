load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "checker",
    testonly = True,
    srcs = ["WorkspaceChecker.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
)

kt_jvm_library_with_dagger(
    name = "impl",
    testonly = True,
    srcs = ["WorkspaceCheckerImpl.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
    deps = [
        ":checker",
        "//first_party/publicity/conformance:qualifiers",
        "//first_party/publicity/conformance/packagechecker:checker",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl_module",
    testonly = True,
    srcs = ["WorkspaceCheckerImplModule.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
    deps = [
        ":checker",
        ":impl",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["WorkspaceCheckerTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":checker",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    testonly = True,
    srcs = ["WorkspaceCheckerImplTest.kt"],
    test_class = "com.jackbradshaw.publicity.conformance.workspacechecker.WorkspaceCheckerImplTest",
    deps = [
        ":impl",
        ":impl_module",
        ":test",
        "//first_party/publicity/conformance:qualifiers",
        "//first_party/publicity/conformance:scope",
        "//first_party/publicity/conformance/packagechecker:impl_module",
        "//first_party/publicity/conformance/packagechecker:test",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)
