load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "checker",
    testonly = True,
    srcs = ["PackageChecker.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
    deps = ["@com_jackbradshaw_maven//:com_google_truth_truth"],
)

kt_jvm_library_with_dagger(
    name = "impl",
    testonly = True,
    srcs = ["PackageCheckerImpl.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
    deps = [
        ":checker",
        "//first_party/publicity/conformance:qualifiers",
        "@com_jackbradshaw_maven//:com_eed3si9n_starlark_starlark",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl_module",
    testonly = True,
    srcs = ["PackageCheckerImplModule.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
    deps = [
        ":checker",
        ":impl",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["PackageCheckerTest.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
    deps = [
        ":checker",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    testonly = True,
    srcs = ["PackageCheckerImplTest.kt"],
    test_class = "com.jackbradshaw.publicity.conformance.packagechecker.PackageCheckerImplTest",
    deps = [
        ":checker",
        ":impl_module",
        ":test",
        "//first_party/publicity/conformance:qualifiers",
        "//first_party/publicity/conformance:scope",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
