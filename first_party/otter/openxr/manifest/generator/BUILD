load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/otter:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "generator",
    srcs = [
        "ManifestGenerator.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "//first_party/klu/flow",
        "//first_party/otter/openxr/model:kt_jvm_proto",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "ManifestGeneratorImpl.kt",
    ],
    visibility = ["//visibility:private"],
    deps = [
        ":generator",
        "//first_party/klu/flow",
        "//first_party/otter:scope",
        "//first_party/otter/config:kt_jvm_proto",
        "//first_party/otter/openxr/manifest/encoder",
        "//first_party/otter/openxr/model:kt_jvm_proto",
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_code_gson_gson",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = [
        "ManifestGeneratorModule.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        ":generator",
        ":impl",
    ],
)

kt_jvm_test_with_dagger(
    name = "test",
    srcs = [
        "ManifestGeneratorTest.kt",
    ],
    test_class = "com.jackbradshaw.otter.openxr.manifest.generator.ManifestGeneratorTest",
    visibility = ["//visibility:private"],
    deps = [
        "//first_party/otter:component",
        "//first_party/otter:component_impl",
        "//first_party/otter/config:proto_extensions",
        "//first_party/otter/openxr/config:proto_extensions",
        "//first_party/otter/openxr/manifest/encoder",
        "//first_party/otter/openxr/manifest/testgoldens",
        "//first_party/otter/openxr/standard",
        "@bazel_tools//tools/java/runfiles",
        "@com_jackbradshaw_maven//:com_google_protobuf_protobuf_kotlin_lite",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_skyscreamer_jsonassert",
    ],
)
