load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

proto_library(
    name = "interaction_proto",
    srcs = [
        "interaction.proto",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        "//first_party/otter/graphics/model:proto",
        "//first_party/otter/math/model:proto",
    ],
)

kt_jvm_proto_library(
    name = "interaction_kt_jvm_proto",
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [":interaction_proto"],
)

kt_jvm_library(
    name = "interaction_extensions",
    srcs = [
        "InteractionExt.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        ":interaction_kt_jvm_proto",
    ],
)

kt_jvm_library(
    name = "collision",
    srcs = [
        "Collision.kt",
    ],
    visibility = ["//first_party/otter:__subpackages__"],
    deps = [
        ":interaction_kt_jvm_proto",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "physics_collision_event_extensions",
    srcs = [
        "PhysicsCollisionEventExt.kt",
    ],
    visibility = ["//first_party/otter:__subpackages__"],
    deps = [
        ":collision",
        ":interaction_extensions",
        ":interaction_kt_jvm_proto",
        "//first_party/otter/engine/core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_bullet",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "global_collisions",
    srcs = [
        "GlobalCollisions.kt",
    ],
    visibility = ["//first_party/otter:__subpackages__"],
    deps = [
        ":collision",
        ":physics_collision_event_extensions",
        "//first_party/otter/engine/core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_bullet",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)
