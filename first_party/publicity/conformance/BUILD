load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")
load(":conformance.bzl", "conformance_test")

# The macro in conformance.bzl is the primary API for this package.
exports_files(
    ["conformance.bzl"],
)

kt_jvm_library(
    name = "scope",
    testonly = True,
    srcs = ["ConformanceScope.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
    deps = ["@com_jackbradshaw_maven//:javax_inject_javax_inject"],
)

kt_jvm_library(
    name = "qualifiers",
    testonly = True,
    srcs = ["Qualifiers.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
    deps = ["@com_jackbradshaw_maven//:javax_inject_javax_inject"],
)

kt_jvm_test_with_dagger(
    name = "conformance_integration_test",
    srcs = ["ConformanceIntegrationTest.kt"],
    test_class = "com.jackbradshaw.publicity.conformance.ConformanceIntegrationTest",
    deps = [
        ":conformance_impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)

# Defined as a library, not a test, so the macro can use it.
kt_jvm_library_with_dagger(
    name = "conformance_impl",
    testonly = True,
    srcs = [
        "ConformanceTest.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        ":qualifiers",
        ":scope",
        "//first_party/publicity/conformance/packagechecker:impl",
        "//first_party/publicity/conformance/packagechecker:impl_module",
        "//first_party/publicity/conformance/workspacechecker:checker",
        "//first_party/publicity/conformance/workspacechecker:impl",
        "//first_party/publicity/conformance/workspacechecker:impl_module",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)
