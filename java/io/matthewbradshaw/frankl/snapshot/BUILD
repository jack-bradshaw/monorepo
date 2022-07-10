load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "snapshot",
    srcs = [
        "SnapshotStore.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/frankl:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/matthewbradshaw/frankl/model:snapshot_java_lite_proto",
        "//java/io/matthewbradshaw/klu/flow",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
