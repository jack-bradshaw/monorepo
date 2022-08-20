load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

proto_library(
    name = "interaction_proto",
    srcs = [
        "interaction.proto",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/otter/model/elements:proto",
        "//java/io/jackbradshaw/otter/graphics:proto",
        "//java/io/jackbradshaw/otter/math:proto",
    ],
)

java_proto_library(
    name = "interaction_java_proto",
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":interaction_proto"],
)

java_lite_proto_library(
    name = "interaction_java_lite_proto",
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":interaction_proto"],
)

kt_jvm_library(
    name = "interaction_extensions",
    srcs = [
        "InteractionExt.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":interaction_java_lite_proto",
    ],
)

kt_jvm_library(
    name = "collision",
    srcs = [
        "Collision.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":interaction_java_lite_proto",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "physics_collision_event_extensions",
    srcs = [
        "PhysicsCollisionEventExt.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":collision",
        ":interaction_extensions",
        ":interaction_java_lite_proto",
        "//java/io/jackbradshaw/otter/engine",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "global_collisions",
    srcs = [
        "GlobalCollisions.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":collision",
        ":physics_collision_event_extensions",
        "//java/io/jackbradshaw/otter/engine",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
