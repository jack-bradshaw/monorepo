load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "game",
    srcs = [
        "Game.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/gmonkey:__subpackages__"],
    deps = [
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "scope",
    srcs = [
        "OctaviusScope.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/gmonkey:__subpackages__"],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "octavius",
    srcs = [
        "Octavius.kt",
        "Otto.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/gmonkey:__subpackages__"],
    deps = [
        ":dagger",
        ":scope",
        "//java/io/matthewbradshaw/gmonkey/octavius/engine",
        "//java/io/matthewbradshaw/gmonkey/octavius/engine:module",
        "//java/io/matthewbradshaw/gmonkey/octavius/ticker",
        "//java/io/matthewbradshaw/gmonkey/octavius/ticker:impl",
        "//java/io/matthewbradshaw/gmonkey/octavius/ticker:module",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)
