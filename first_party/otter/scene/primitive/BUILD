load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "primitive",
    srcs = [
        "ScenePrimitive.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        "//first_party/klu/flow",
        "//first_party/otter/physics/model:kt_jvm_proto",
        "@com_jackbradshaw_maven//:com_google_protobuf_protobuf_lite",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
