load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "cubegame",
    srcs = [
        "CubeGame.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        "//java/io/matthewbradshaw/octavius/core",
        "//java/io/matthewbradshaw/octavius",
        "//java/io/matthewbradshaw/octavius/ticker",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
