load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/otter:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "stage",
    srcs = ["SceneStage.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/klu/flow",
        "//first_party/otter/engine/core",
        "//first_party/otter/math/model:proto_extensions",
        "//first_party/otter/physics/model:proto_extensions",
        "//first_party/otter/scene/item",
        "//first_party/otter/scene/primitive",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["SceneStageImpl.kt"],
    deps = [
        ":stage",
        "//first_party/klu/flow",
        "//first_party/otter/coroutines",
        "//first_party/otter/engine/core",
        "//first_party/otter/qualifiers",
        "//first_party/otter/scene/item",
        "//first_party/otter/timing",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["SceneStageModule.kt"],
    visibility = PUBLICITY,
    deps = [
        ":impl",
        ":stage",
    ],
)
