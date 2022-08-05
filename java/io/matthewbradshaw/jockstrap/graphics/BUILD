load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

proto_library(
    name = "proto",
    srcs = [
        "color.proto",
        "mesh.proto",
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
        "ColorExt.kt",
        "MeshExt.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/jockstrap:__subpackages__"],
    deps = [
        ":java_lite_proto",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
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
