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
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        "//first_party/com/jackbradshaw/klu/flow",
        "//first_party/com/jackbradshaw/otter/engine/core",
        "//first_party/com/jackbradshaw/otter/math/model:proto_extensions",
        "//first_party/com/jackbradshaw/otter/physics/model:proto_extensions",
        "//first_party/com/jackbradshaw/otter/scene/item",
        "//first_party/com/jackbradshaw/otter/scene/primitive",
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
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":stage",
        "//first_party/com/jackbradshaw/klu/flow",
        "//first_party/com/jackbradshaw/otter/coroutines",
        "//first_party/com/jackbradshaw/otter/engine/core",
        "//first_party/com/jackbradshaw/otter/qualifiers",
        "//first_party/com/jackbradshaw/otter/scene/item",
        "//first_party/com/jackbradshaw/otter/timing",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "SceneStageModule.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":stage",
    ],
)
