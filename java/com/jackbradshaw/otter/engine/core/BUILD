load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "core",
    srcs = [
        "EngineCore.kt",
    ],
    visibility = ["//java/com/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":dagger",
        "//java/com/jackbradshaw/otter:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_bullet",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "EngineCoreImpl.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":core",
        ":dagger",
        "//java/com/jackbradshaw/otter:scope",
        "//java/com/jackbradshaw/otter/config:kt_jvm_proto",
        "//java/com/jackbradshaw/otter/coroutines",
        "//java/com/jackbradshaw/otter/openxr/manifest/installer",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "EngineCoreModule.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":core",
        ":dagger",
        ":impl",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_vr",
    ],
)
