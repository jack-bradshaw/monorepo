load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:bazel/auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

kt_jvm_library(
    name = "stage",
    srcs = [
        "SceneStage.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/otter/engine/core",
        "//java/io/jackbradshaw/otter/math/model:proto_extensions",
        "//java/io/jackbradshaw/otter/physics/model:proto_extensions",
        "//java/io/jackbradshaw/otter/scene/item",
        "//java/io/jackbradshaw/otter/scene/primitive",
        "@maven//:com_google_guava_guava",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "SceneStageImpl.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":stage",
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/otter/coroutines",
        "//java/io/jackbradshaw/otter/engine/core",
        "//java/io/jackbradshaw/otter/qualifiers",
        "//java/io/jackbradshaw/otter/scene/item",
        "//java/io/jackbradshaw/otter/timing",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "SceneStageModule.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":stage",
    ],
)
