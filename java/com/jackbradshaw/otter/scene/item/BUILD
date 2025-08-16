load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//autofactory:autofactory.bzl", "auto_factory")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

auto_factory()

kt_jvm_library(
    name = "item",
    srcs = [
        "SceneItem.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/com/jackbradshaw/klu/flow",
        "//java/com/jackbradshaw/otter/engine/core",
        "//java/com/jackbradshaw/otter/math/model:proto_extensions",
        "//java/com/jackbradshaw/otter/physics/model:proto_extensions",
        "//java/com/jackbradshaw/otter/scene/primitive",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "SceneItemImpl.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":item",
        "//java/com/jackbradshaw/klu/flow",
        "//java/com/jackbradshaw/otter/scene/primitive",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
