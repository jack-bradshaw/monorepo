load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "model",
    srcs = [
        "WorldItem.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/merovingian:__subpackages__"],
    deps = [
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
