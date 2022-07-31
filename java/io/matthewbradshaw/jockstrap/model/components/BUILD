load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

proto_library(
    name = "proto",
    srcs = [
        "collider_component.proto",
        "lighting_component.proto",
        "spatial_component.proto",
    ],
    visibility = [
        "//java/io/matthewbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/matthewbradshaw/jockstrap/math:proto",
        "//java/io/matthewbradshaw/jockstrap/model/elements:proto",
        "//java/io/matthewbradshaw/jockstrap/sensation:proto",
    ],
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
    name = "java_lite_protos",
    visibility = [
        "//java/io/matthewbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = [
        #  "ColliderComponent.kt",
        "LightingComponent.kt",
        # "SpatialComponent.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":java_lite_protos",
        "//java/io/matthewbradshaw/jockstrap/math:jmonkey_conversion",
        "//java/io/matthewbradshaw/jockstrap/math:proto_extensions",
        "//java/io/matthewbradshaw/jockstrap/model/bases:proto_extensions",
        "//java/io/matthewbradshaw/jockstrap/model/elements:java_lite_protos",
        "//java/io/matthewbradshaw/jockstrap/model/elements:proto_extensions",
        "//java/io/matthewbradshaw/jockstrap/physics:jmonkey_conversion",
        "//java/io/matthewbradshaw/jockstrap/sensation:java_lite_proto",
        "//java/io/matthewbradshaw/jockstrap/sensation:jmonkey_conversion",
        "//java/io/matthewbradshaw/klu/flow",
        "@maven//:com_google_guava_guava",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "jmonkey",
    srcs = [
        #  "ColliderComponent.kt",
        "JMonkeyLightingComponent.kt",
        # "SpatialComponent.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":java_lite_protos",
        ":proto_extensions",
        "//java/io/matthewbradshaw/jockstrap/math:jmonkey_conversion",
        "//java/io/matthewbradshaw/jockstrap/math:proto_extensions",
        "//java/io/matthewbradshaw/jockstrap/model/bases:proto_extensions",
        "//java/io/matthewbradshaw/jockstrap/model/elements:java_lite_protos",
        "//java/io/matthewbradshaw/jockstrap/model/elements:proto_extensions",
        "//java/io/matthewbradshaw/jockstrap/physics:jmonkey_conversion",
        "//java/io/matthewbradshaw/jockstrap/sensation:java_lite_proto",
        "//java/io/matthewbradshaw/jockstrap/sensation:jmonkey_conversion",
        "//java/io/matthewbradshaw/klu/flow",
        "@maven//:com_google_guava_guava",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
