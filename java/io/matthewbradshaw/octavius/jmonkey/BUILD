load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "module",
    srcs = [
        "JMonkeyModule.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        "//java/io/matthewbradshaw/octavius/app:app",
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
