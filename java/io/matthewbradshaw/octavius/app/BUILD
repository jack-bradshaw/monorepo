load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "app",
    srcs = [
        "App.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        "//java/io/matthewbradshaw/octavius/heartbeat",
        "//java/io/matthewbradshaw/octavius/ui",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:javax_inject_javax_inject",
        "//java/io/matthewbradshaw/octavius/core:core",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)