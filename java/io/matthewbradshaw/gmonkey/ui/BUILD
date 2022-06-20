load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "ui",
    srcs = [
        "Item.kt",
        "Level.kt",
        "Region.kt",
        "World.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/gmonkey:__subpackages__"],
    deps = [
        "//java/io/matthewbradshaw/gmonkey/lifecycle",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
