load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

proto_library(
    name = "proto",
    srcs = [
        "dynamics.proto",
        "kinematics.proto",
        "placement.proto",
    ],
    visibility = [
        "//java/io/matthewbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = ["//java/io/matthewbradshaw/jockstrap/math:proto"],
)

java_proto_library(
    name = "java_proto",
    visibility = [
        "//java/io/matthewbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":proto"],
)

java_lite_proto_library(
    name = "java_lite_proto",
    visibility = [
        "//java/io/matthewbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = [
        "DynamicsExt.kt",
        "ForceExt.kt",
        "KinematicsExt.kt",
        "PlacementExt.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/jockstrap:__subpackages__"],
    deps = [
        ":java_lite_proto",
        "//java/io/matthewbradshaw/jockstrap/math:java_lite_proto",
        "//java/io/matthewbradshaw/jockstrap/math:proto_extensions",
    ],
)

kt_jvm_library(
    name = "collisions",
    srcs = [
        "Collisions.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/jockstrap:__subpackages__"],
    deps = [
        "//java/io/matthewbradshaw/jockstrap/engine",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "jmonkey_conversion",
    srcs = [
        "JMonkeyConversion.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":java_lite_proto",
        ":proto_extensions",
        "//java/io/matthewbradshaw/jockstrap/math:jmonkey_conversion",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
