load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "installation",
    srcs = [
        "OpenXrInstaller.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/openxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/openxr/model:java_lite_proto",
        "//java/io/jackbradshaw/openxr/standard",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "OpenXrInstallerImpl.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/openxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":installation",
        "//java/io/jackbradshaw/openxr:scope",
        "//java/io/jackbradshaw/openxr/config:java_lite_proto",
        "//java/io/jackbradshaw/openxr/manifest",
        "@maven//:com_google_code_gson_gson",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "OpenXrInstallerModule.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/openxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":installation",
    ],
)
