load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "manifest",
    srcs = [
        "ManifestGenerator.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/omnixr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
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
        "//java/io/jackbradshaw/omnixr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":manifest",
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/omnixr:scope",
        "//java/io/jackbradshaw/omnixr/encoding",
        "//java/io/jackbradshaw/omnixr/model:java_lite_proto",
        "//java/io/jackbradshaw/omnixr/standard",
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
        "//java/io/jackbradshaw/omnixr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":manifest",
    ],
)
