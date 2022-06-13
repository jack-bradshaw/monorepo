load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "jmonkey",
    srcs = [
        "JMonkeyEngine.kt",
        "JMonkeyApp.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        ":dagger",
        "//java/io/matthewbradshaw/octavius:scope",
        "//java/io/matthewbradshaw/octavius/core",
        "//java/io/matthewbradshaw/octavius/heartbeat",
        "//java/io/matthewbradshaw/octavius/ui",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "JMonkeyAppImpl.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        ":dagger",
        ":jmonkey",
        "//java/io/matthewbradshaw/octavius:scope",
        "//java/io/matthewbradshaw/octavius/core",
        "//java/io/matthewbradshaw/octavius/heartbeat",
        "//java/io/matthewbradshaw/octavius/ui",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "JMonkeyEngineModule.kt",
        "JMonkeyAppModule.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        ":dagger",
        ":jmonkey",
        ":impl",
        "//java/io/matthewbradshaw/octavius:scope",
        "//java/io/matthewbradshaw/octavius/core",
        "//java/io/matthewbradshaw/octavius/heartbeat",
        "//java/io/matthewbradshaw/octavius/ui",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)
