load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

proto_library(
    name = "proto",
    srcs = [
        "component.proto",
        "entity.proto",
        "level.proto",
    ],
    visibility = [
        "//java/io/jackbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/jockstrap/math:proto",
        "//java/io/jackbradshaw/jockstrap/physics:proto",
    ],
)

java_proto_library(
    name = "java_proto",
    visibility = [
        "//java/io/jackbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":proto"],
)

java_lite_proto_library(
    name = "java_lite_protos",
    visibility = [
        "//java/io/jackbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = [
        "Component.kt",
        "Entity.kt",
        "Game.kt",
        "Level.kt",
        "Relationship.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":java_lite_protos",
        "//java/io/jackbradshaw/jockstrap/math:java_lite_proto",
        "//java/io/jackbradshaw/jockstrap/model/frames",
        "//java/io/jackbradshaw/jockstrap/physics:java_lite_proto",
        "//java/io/jackbradshaw/klu/flow",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
