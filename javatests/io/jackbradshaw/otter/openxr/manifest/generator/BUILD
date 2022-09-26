load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_test(
    name = "ManifestGeneratorTest",
    srcs = [
        "ManifestGeneratorTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/clearxr:component",
        "//java/io/jackbradshaw/otter/openxr/manifest/encoder",
        "//java/io/jackbradshaw/otter/openxr/model:proto_extensions",
        "//java/io/jackbradshaw/otter/openxr/standard",
        "//javatests/io/jackbradshaw/otter/clearxr/manifest/goldens",
        "@bazel_tools//tools/java/runfiles",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
