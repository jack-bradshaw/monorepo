load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules()

kt_jvm_library(
    name = "coroutines",
    srcs = [
        "Dispatchers.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/otter/engine/core",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "CoroutinesModule.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":coroutines",
        ":dagger",
        "//java/io/jackbradshaw/otter/engine/core",
        "//java/io/jackbradshaw/otter/qualifiers",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
