load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

proto_library(
    name = "item_snapshot_proto",
    srcs = [
        "item_snapshot.proto",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/otter/math/model:proto",
        "//java/io/jackbradshaw/otter/physics/model:proto",
    ],
)

java_proto_library(
    name = "item_snapshot_java_proto",
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":item_snapshot_proto"],
)

java_lite_proto_library(
    name = "item_snapshot_java_lite_proto",
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":item_snapshot_proto"],
)

kt_jvm_library(
    name = "scene",
    srcs = [
        "Host.kt",
        "Item.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":item_snapshot_java_proto",
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/otter/engine/core",
        "//java/io/jackbradshaw/otter/math/model:proto_extensions",
        "//java/io/jackbradshaw/otter/physics/model:proto_extensions",
        "@maven//:com_google_guava_guava",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "HostImpl.kt",
        "ItemBase.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":scene",
        "//java/io/jackbradshaw/klu/flow",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
