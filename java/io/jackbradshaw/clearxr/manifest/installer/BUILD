load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "installer",
    srcs = [
        "ManifestInstaller.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/clearxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/clearxr/model:java_lite_proto",
        "//java/io/jackbradshaw/clearxr/standard",
        "//java/io/jackbradshaw/klu/flow",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "ManifestInstallerImpl.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/clearxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":installer",
        "//java/io/jackbradshaw/clearxr:scope",
        "//java/io/jackbradshaw/clearxr/config:java_lite_proto",
        "//java/io/jackbradshaw/clearxr/manifest/generator",
        "@maven//:com_google_code_gson_gson",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "ManifestInstallerModule.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/clearxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":installer",
    ],
)
