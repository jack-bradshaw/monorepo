load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

kt_jvm_library(
    name = "bases",
    srcs = [
        "ComponentBase.kt",
        "GameBase.kt",
        "IntegrationBase.kt",
        "ItemBase.kt",
        "LevelBase.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/otter/engine",
        "//java/io/jackbradshaw/otter/math:java_lite_proto",
        "//java/io/jackbradshaw/otter/physics:java_lite_proto",
        "//java/io/jackbradshaw/otter/physics:proto_extensions",
        "//java/io/jackbradshaw/otter/structure/controllers",
        "//java/io/jackbradshaw/otter/structure/controllers:java_lite_protos",
        "//java/io/jackbradshaw/otter/structure/frames",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
