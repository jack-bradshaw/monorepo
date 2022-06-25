load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "engine",
    srcs = [
        "Engine.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/merovingian:__subpackages__"],
    deps = [
        ":dagger",
        "//java/io/matthewbradshaw/merovingian:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "engine_impl",
    srcs = [
        "EngineImpl.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":engine",
        "//java/io/matthewbradshaw/merovingian:scope",
        "//java/io/matthewbradshaw/merovingian/config",
        "//java/io/matthewbradshaw/merovingian/coroutines:dispatcher",
        "@maven//:com_google_auto_factory_auto_factory",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "qualifiers",
    srcs = [
        "Qualifiers.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "EngineModule.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":engine",
        ":engine_impl",
        ":qualifiers",
        "//java/io/matthewbradshaw/merovingian:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)
