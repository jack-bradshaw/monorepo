load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "primitive",
    srcs = [
        "ScenePrimitive.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        "//first_party/com/jackbradshaw/klu/flow",
        "//first_party/com/jackbradshaw/otter/physics/model:kt_jvm_proto",
        "@com_jackbradshaw_maven//:com_google_protobuf_protobuf_lite",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
