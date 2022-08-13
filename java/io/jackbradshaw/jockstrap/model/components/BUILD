load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

proto_library(
    name = "proto",
    srcs = [
        "biped_component.proto",
        "lighting_component.proto",
        "rigid_body_component.proto",
        "sensor_component.proto",
        "spatial_component.proto",
        "vehicle_component.proto",
    ],
    visibility = [
        "//java/io/jackbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/jockstrap/graphics:proto",
        "//java/io/jackbradshaw/jockstrap/math:proto",
        "//java/io/jackbradshaw/jockstrap/model/elements:proto",
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
        "LightingComponent.kt",
        "LightingComponentImpl.kt",
        "RigidBodyComponent.kt",
        "RigidBodyComponentImpl.kt",
        "SpatialComponent.kt",
        "SpatialComponentImpl.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":java_lite_protos",
        "//java/io/jackbradshaw/jockstrap/graphics:java_lite_proto",
        "//java/io/jackbradshaw/jockstrap/graphics:jmonkey_conversion",
        "//java/io/jackbradshaw/jockstrap/math:jmonkey_conversion",
        "//java/io/jackbradshaw/jockstrap/math:proto_extensions",
        "//java/io/jackbradshaw/jockstrap/model/bases:proto_extensions",
        "//java/io/jackbradshaw/jockstrap/model/elements:java_lite_protos",
        "//java/io/jackbradshaw/jockstrap/model/elements:proto_extensions",
        "//java/io/jackbradshaw/jockstrap/physics:jmonkey_conversion",
        "//java/io/jackbradshaw/klu/flow",
        "@maven//:com_google_guava_guava",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
