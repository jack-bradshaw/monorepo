load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/otter/demo:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "items",
    testonly = True,
    srcs = [
        "Cube.kt",
        "CubeLevel.kt",
        "CubeSwarm.kt",
    ],
    visibility = PUBLICITY,
    deps = ["//first_party/otter/scene/item"],
)

kt_jvm_library(
    name = "impl",
    testonly = True,
    srcs = [
        "CubeImpl.kt",
        "CubeLevelImpl.kt",
        "CubeSwarmImpl.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        ":items",
        "//first_party/klu/concurrency",
        "//first_party/klu/flow",
        "//first_party/otter/coroutines",
        "//first_party/otter/demo:scope",
        "//first_party/otter/demo/constants",
        "//first_party/otter/demo/materials",
        "//first_party/otter/engine/core",
        "//first_party/otter/qualifiers",
        "//first_party/otter/scene/item",
        "//first_party/otter/scene/item:impl",
        "//first_party/otter/timing",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_bullet",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    testonly = True,
    srcs = ["ItemsModule.kt"],
    visibility = PUBLICITY,
    deps = [
        ":impl",
        ":items",
    ],
)
