load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "engine",
    srcs = [
        "Engine.kt",
        "Paradigm.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        ":dagger",
        "//java/io/matthewbradshaw/octavius:game",
        "//java/io/matthewbradshaw/octavius:scope",
        "//java/io/matthewbradshaw/octavius/ticker",
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
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        ":dagger",
        ":engine",
        "//java/io/matthewbradshaw/kmonkey/coroutines:dispatcher",
        "//java/io/matthewbradshaw/octavius:scope",
        "//java/io/matthewbradshaw/octavius/ticker",
        "@maven//:com_google_auto_factory_auto_factory",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "EngineModule.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        ":dagger",
        ":engine",
        ":engine_impl",
        "//java/io/matthewbradshaw/octavius:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)
