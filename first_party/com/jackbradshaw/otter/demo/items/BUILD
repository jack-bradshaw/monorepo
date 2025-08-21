load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "items",
    testonly = True,
    srcs = [
        "Cube.kt",
        "CubeLevel.kt",
        "CubeSwarm.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter/demo:__subpackages__",
    ],
    deps = [
        "//first_party/com/jackbradshaw/otter/scene/item",
    ],
)

kt_jvm_library(
    name = "impl",
    testonly = True,
    srcs = [
        "CubeImpl.kt",
        "CubeLevelImpl.kt",
        "CubeSwarmImpl.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter/demo:__subpackages__",
    ],
    deps = [
        ":items",
        "//first_party/com/jackbradshaw/klu",
        "//first_party/com/jackbradshaw/otter/coroutines",
        "//first_party/com/jackbradshaw/otter/demo:scope",
        "//first_party/com/jackbradshaw/otter/demo/constants",
        "//first_party/com/jackbradshaw/otter/demo/materials",
        "//first_party/com/jackbradshaw/otter/engine/core",
        "//first_party/com/jackbradshaw/otter/qualifiers",
        "//first_party/com/jackbradshaw/otter/scene/item",
        "//first_party/com/jackbradshaw/otter/scene/item:impl",
        "//first_party/com/jackbradshaw/otter/timing",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_bullet",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "module",
    testonly = True,
    srcs = [
        "ItemsModule.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter/demo:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":items",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
