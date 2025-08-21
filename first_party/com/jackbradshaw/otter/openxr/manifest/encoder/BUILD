load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "encoder",
    srcs = [
        "ManifestEncoder.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        "//first_party/com/jackbradshaw/otter/openxr/model:kt_jvm_proto",
        "//first_party/com/jackbradshaw/otter/openxr/standard",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "ManifestEncoderImpl.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":encoder",
        "//first_party/com/jackbradshaw/otter:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "ManifestEncoderModule.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":encoder",
        ":impl",
    ],
)

kt_jvm_test(
    name = "test",
    srcs = [
        "ManifestEncoderTest.kt",
    ],
    test_class = "com.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoderTest",
    visibility = ["//visibility:private"],
    deps = [
        ":dagger",
        "//first_party/com/jackbradshaw/otter:component",
        "//first_party/com/jackbradshaw/otter/config:proto_extensions",
        "//first_party/com/jackbradshaw/otter/openxr/config:proto_extensions",
        "//first_party/com/jackbradshaw/otter/openxr/manifest/encoder",
        "//first_party/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_protobuf_protobuf_kotlin_lite",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
