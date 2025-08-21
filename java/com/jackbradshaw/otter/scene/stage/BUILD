load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//autofactory:autofactory.bzl", "auto_factory")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

auto_factory()

kt_jvm_library(
    name = "stage",
    srcs = [
        "SceneStage.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        "//java/com/jackbradshaw/klu/flow",
        "//java/com/jackbradshaw/otter/engine/core",
        "//java/com/jackbradshaw/otter/math/model:proto_extensions",
        "//java/com/jackbradshaw/otter/physics/model:proto_extensions",
        "//java/com/jackbradshaw/otter/scene/item",
        "//java/com/jackbradshaw/otter/scene/primitive",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "SceneStageImpl.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":stage",
        "//java/com/jackbradshaw/klu/flow",
        "//java/com/jackbradshaw/otter/coroutines",
        "//java/com/jackbradshaw/otter/engine/core",
        "//java/com/jackbradshaw/otter/qualifiers",
        "//java/com/jackbradshaw/otter/scene/item",
        "//java/com/jackbradshaw/otter/timing",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "SceneStageModule.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":stage",
    ],
)
