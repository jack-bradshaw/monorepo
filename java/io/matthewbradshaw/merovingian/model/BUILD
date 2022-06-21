load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "model",
    srcs = [
        "Game.kt",
        "GameItem.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/merovingian:__subpackages__"],
    deps = [
        "//java/io/matthewbradshaw/merovingian/lifecycle",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
