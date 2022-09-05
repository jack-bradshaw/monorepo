load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "encoder",
    srcs = [
        "ManifestEncoder.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/omnixr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/omnixr/model:java_lite_proto",
        "//java/io/jackbradshaw/omnixr/standard",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "ManifestEncoderImpl.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/omnixr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":encoder",
        "//java/io/jackbradshaw/omnixr:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "ManifestEncoderModule.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/omnixr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":encoder",
        ":impl",
    ],
)
