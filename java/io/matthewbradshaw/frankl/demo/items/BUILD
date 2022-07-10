load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "items",
    testonly = True,
    srcs = [
        "Cube.kt",
        "CubeLevel.kt",
        "CubeSwarm.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/frankl/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/matthewbradshaw/frankl/model/world:model",
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
        "//java/io/matthewbradshaw/frankl/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":items",
        "//java/io/matthewbradshaw/frankl/clock",
        "//java/io/matthewbradshaw/frankl/clock:qualifiers",
        "//java/io/matthewbradshaw/frankl/coroutines",
        "//java/io/matthewbradshaw/frankl/demo:scope",
        "//java/io/matthewbradshaw/frankl/demo/constants",
        "//java/io/matthewbradshaw/frankl/demo/materials",
        "//java/io/matthewbradshaw/frankl/engine",
        "//java/io/matthewbradshaw/frankl/model/world:model",
        "//java/io/matthewbradshaw/klu",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "module",
    testonly = True,
    srcs = [
        "ItemsModule.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/frankl/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":items",
        "@maven//:javax_inject_javax_inject",
    ],
)
