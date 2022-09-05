load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "manifests",
    srcs = [
        "ManifestGenerator.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/openxr/model:java_lite_proto",
        "//java/io/jackbradshaw/openxr/standard",
        "//java/io/jackbradshaw/otter/clock",
        "//java/io/jackbradshaw/otter/clock:qualifiers",
        "//java/io/jackbradshaw/otter/engine",
        "//java/io/jackbradshaw/otter/math:java_proto",
        "//java/io/jackbradshaw/otter/math:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/math:proto_extensions",
        "//java/io/jackbradshaw/otter/physics:java_proto",
        "//java/io/jackbradshaw/otter/physics:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/physics:proto_extensions",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "ManifestGeneratorImpl.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":manifests",
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/openxr/constants",
        "//java/io/jackbradshaw/openxr/encoding",
        "//java/io/jackbradshaw/openxr/model:java_lite_proto",
        "//java/io/jackbradshaw/openxr/standard",
        "//java/io/jackbradshaw/otter/clock",
        "//java/io/jackbradshaw/otter/clock:qualifiers",
        "//java/io/jackbradshaw/otter/engine",
        "//java/io/jackbradshaw/otter/math:java_proto",
        "//java/io/jackbradshaw/otter/math:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/math:proto_extensions",
        "//java/io/jackbradshaw/otter/physics:java_proto",
        "//java/io/jackbradshaw/otter/physics:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/physics:proto_extensions",
        "@maven//:com_google_code_gson_gson",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "ManifestGeneratorModule.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":manifests",
    ],
)
