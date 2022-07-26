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
        "//java/io/matthewbradshaw/jockstrap/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/matthewbradshaw/jockstrap/model/world:model",
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
        "//java/io/matthewbradshaw/jockstrap/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":items",
        "//java/io/matthewbradshaw/klu",
        "//java/io/matthewbradshaw/jockstrap/model/world:model",
        "//java/io/matthewbradshaw/jockstrap/clock",
        "//java/io/matthewbradshaw/jockstrap/clock:qualifiers",
        "//java/io/matthewbradshaw/jockstrap/coroutines",
        "//java/io/matthewbradshaw/jockstrap/demo:scope",
        "//java/io/matthewbradshaw/jockstrap/demo/constants",
        "//java/io/matthewbradshaw/jockstrap/demo/materials",
        "//java/io/matthewbradshaw/jockstrap/engine",
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
        "//java/io/matthewbradshaw/jockstrap/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":items",
        "@maven//:javax_inject_javax_inject",
    ],
)
