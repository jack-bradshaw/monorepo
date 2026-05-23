load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/otter:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "item",
    srcs = [
        "SceneItem.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "//first_party/otter/physics/model:proto_extensions",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "SceneItemImpl.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        ":item",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
