load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "coroutines",
    srcs = [
        "Dispatchers.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/com/jackbradshaw/otter/engine/core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "CoroutinesModule.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":coroutines",
        ":dagger",
        "//java/com/jackbradshaw/otter/engine/core",
        "//java/com/jackbradshaw/otter/qualifiers",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
