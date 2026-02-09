load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "checker",
    srcs = ["PackageChecker.kt"],
    visibility = [
        "//first_party/publicity/conformance/workspacechecker:__pkg__",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["PackageCheckerImpl.kt"],
    deps = [
        ":checker",
        "//first_party/publicity/conformance/model",
        "@com_jackbradshaw_maven//:com_eed3si9n_starlark_starlark",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl_module",
    srcs = ["PackageCheckerImplModule.kt"],
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
    srcs = ["PackageCheckerTest.kt"],
    deps = [
        ":checker",
        "//first_party/publicity/conformance/model",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["PackageCheckerImplTest.kt"],
    test_class = "com.jackbradshaw.publicity.conformance.packagechecker.PackageCheckerImplTest",
    deps = [
        ":checker",
        ":impl_module",
        ":test",
        "//first_party/publicity/conformance/model",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
