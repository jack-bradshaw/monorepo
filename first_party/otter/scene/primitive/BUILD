load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/publicity:defs.bzl", "quarantined")

PUBLICITY = quarantined("//first_party/otter/scene/primitive")

kt_jvm_library(
    name = "primitive",
    srcs = ["ScenePrimitive.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/klu/flow",
        "//first_party/otter/physics/model:kt_jvm_proto",
        "@com_jackbradshaw_maven//:com_google_protobuf_protobuf_lite",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
