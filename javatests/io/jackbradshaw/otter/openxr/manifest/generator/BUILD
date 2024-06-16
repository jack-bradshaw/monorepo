load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

dagger_rules()

kt_jvm_test(
    name = "ManifestGeneratorTest",
    srcs = [
        "ManifestGeneratorTest.kt",
    ],
    deps = [
        ":dagger",
        "//java/io/jackbradshaw/otter:component",
        "//java/io/jackbradshaw/otter/config:proto_extensions",
        "//java/io/jackbradshaw/otter/openxr/config:proto_extensions",
        "//java/io/jackbradshaw/otter/openxr/manifest/encoder",
        "//java/io/jackbradshaw/otter/openxr/standard",
        "//javatests/io/jackbradshaw/otter/openxr/manifest/goldens",
        "@bazel_tools//tools/java/runfiles",
        "@maven//:com_google_protobuf_protobuf_kotlin_lite",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
