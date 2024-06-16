load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules()

kt_jvm_library(
    name = "primitive",
    srcs = [
        "ScenePrimitive.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/otter/physics/model:java_lite_proto",
        "@maven//:com_google_protobuf_protobuf_lite",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
