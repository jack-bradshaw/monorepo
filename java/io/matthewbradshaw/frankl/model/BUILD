load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

proto_library(
    name = "snapshot_proto",
    srcs = [
        "level.proto",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
)

java_proto_library(
    name = "snapshot_java_proto",
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":level_item_snapshot.proto"],
)

java_lite_proto_library(
    name = "snapshot_java_lite_proto",
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":snapshot_proto"],
)

kt_jvm_library(
    name = "model",
    srcs = [
        "Game.kt",
        "GameBase.kt",
        "GameDelegate.kt",
        "LevelItem.kt",
        "LevelItemBase.kt",
        "LevelItemDelegate.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":snapshot_java_lite_proto",
        "//java/io/matthewbradshaw/klu/flow",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
