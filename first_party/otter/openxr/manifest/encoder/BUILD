load("//first_party/dagger:defs.bzl", 'kt_jvm_library_with_dagger', 'kt_jvm_test_with_dagger')
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "encoder",
    srcs = [
        "ManifestEncoder.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        "//first_party/otter/openxr/model:kt_jvm_proto",
        "//first_party/otter/openxr/standard",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = [
        "ManifestEncoderImpl.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        ":encoder",
        "//first_party/otter:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = [
        "ManifestEncoderModule.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        ":encoder",
        ":impl",
    ],
)

kt_jvm_test_with_dagger(
    name = "test",
    srcs = [
        "ManifestEncoderTest.kt",
    ],
    test_class = "com.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoderTest",
    visibility = ["//visibility:private"],
    deps = [
        "//first_party/otter:component",
        "//first_party/otter/config:proto_extensions",
        "//first_party/otter/openxr/config:proto_extensions",
        "//first_party/otter/openxr/manifest/encoder",
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_protobuf_protobuf_kotlin_lite",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
