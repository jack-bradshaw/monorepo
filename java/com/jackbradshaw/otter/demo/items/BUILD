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
        "//java/com/jackbradshaw/otter/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/com/jackbradshaw/otter/scene/item",
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
        "//java/com/jackbradshaw/otter/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":items",
        "//java/com/jackbradshaw/klu",
        "//java/com/jackbradshaw/otter/coroutines",
        "//java/com/jackbradshaw/otter/demo:scope",
        "//java/com/jackbradshaw/otter/demo/constants",
        "//java/com/jackbradshaw/otter/demo/materials",
        "//java/com/jackbradshaw/otter/engine/core",
        "//java/com/jackbradshaw/otter/qualifiers",
        "//java/com/jackbradshaw/otter/scene/item",
        "//java/com/jackbradshaw/otter/scene/item:impl",
        "//java/com/jackbradshaw/otter/timing",
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
        "//java/com/jackbradshaw/otter/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":items",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
