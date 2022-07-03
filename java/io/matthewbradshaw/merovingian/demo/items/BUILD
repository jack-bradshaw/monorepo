load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "items",
    testonly = True,
    srcs = [
        "Cube.kt",
        "CubeSwarm.kt",
        "CubeWorld.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/matthewbradshaw/merovingian/model",
    ],
)

kt_jvm_library(
    name = "impl",
    testonly = True,
    srcs = [
        "CubeImpl.kt",
        "CubeSwarmImpl.kt",
        "CubeWorldImpl.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":items",
        "//java/io/matthewbradshaw/klu",
        "//java/io/matthewbradshaw/merovingian/clock",
        "//java/io/matthewbradshaw/merovingian/clock:qualifiers",
        "//java/io/matthewbradshaw/merovingian/coroutines",
        "//java/io/matthewbradshaw/merovingian/demo:scope",
        "//java/io/matthewbradshaw/merovingian/demo/constants",
        "//java/io/matthewbradshaw/merovingian/demo/materials",
        "//java/io/matthewbradshaw/merovingian/engine",
        "//java/io/matthewbradshaw/merovingian/model",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
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
        "//java/io/matthewbradshaw/merovingian/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":items",
        "@maven//:javax_inject_javax_inject",
    ],
)
