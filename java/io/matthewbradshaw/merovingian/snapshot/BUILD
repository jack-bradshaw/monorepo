load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "snapshot",
    srcs = [
        "SnapshotStore.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/matthewbradshaw/klu/flow",
        "//java/io/matthewbradshaw/merovingian/model:snapshot_java_lite_proto",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
