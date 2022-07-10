load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

proto_library(
    name = "math_proto",
    srcs = [
        "quaternion.proto",
        "transform.proto",
        "vector3.proto",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
)

java_proto_library(
    name = "math_java_proto",
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":math_proto"],
)

java_lite_proto_library(
    name = "math_java_lite_proto",
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":math_proto"],
)

kt_jvm_library(
    name = "math_ext",
    srcs = [
        "QuaternionExt.kt",
        "TransformExt.kt",
        "Vector3Ext.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":math_java_lite_proto",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
