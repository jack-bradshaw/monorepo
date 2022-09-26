load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "generator",
    srcs = [
        "ManifestGenerator.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/clearxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/clearxr/model:java_lite_proto",
        "//java/io/jackbradshaw/klu/flow",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "ManifestGeneratorImpl.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/clearxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":generator",
        "//java/io/jackbradshaw/clearxr:scope",
        "//java/io/jackbradshaw/clearxr/config:java_lite_proto",
        "//java/io/jackbradshaw/clearxr/manifest/encoder",
        "//java/io/jackbradshaw/clearxr/model:java_lite_proto",
        "//java/io/jackbradshaw/clearxr/standard",
        "//java/io/jackbradshaw/klu/flow",
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
        "//java/io/jackbradshaw/clearxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":generator",
        ":impl",
    ],
)
