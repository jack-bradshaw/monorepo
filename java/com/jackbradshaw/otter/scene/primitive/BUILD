load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "primitive",
    srcs = [
        "ScenePrimitive.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        "//java/com/jackbradshaw/klu/flow",
        "//java/com/jackbradshaw/otter/physics/model:kt_jvm_proto",
        "@com_jackbradshaw_maven//:com_google_protobuf_protobuf_lite",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
