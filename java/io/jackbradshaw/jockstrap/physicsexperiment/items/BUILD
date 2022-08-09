load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "items",
    testonly = True,
    srcs = [
        "Cube.kt",
        "Lab.kt",
        "Origin.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/jockstrap/physicsexperiment:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/jockstrap/model/world",
    ],
)

kt_jvm_library(
    name = "impl",
    testonly = True,
    srcs = [
        "CubeImpl.kt",
        "LabImpl.kt",
        "OriginImpl.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/jockstrap/physicsexperiment:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":items",
        "//java/io/jackbradshaw/klu",
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/jockstrap/model/math:math_ext",
        "//java/io/jackbradshaw/jockstrap/model/math:math_java_lite_proto",
        "//java/io/jackbradshaw/jockstrap/model/world",
        "//java/io/jackbradshaw/jockstrap/clock",
        "//java/io/jackbradshaw/jockstrap/clock:qualifiers",
        "//java/io/jackbradshaw/jockstrap/coroutines",
        "//java/io/jackbradshaw/jockstrap/engine",
        "//java/io/jackbradshaw/jockstrap/physicsexperiment:scope",
        "//java/io/jackbradshaw/jockstrap/physicsexperiment/constants",
        "//java/io/jackbradshaw/jockstrap/physicsexperiment/materials",
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
        "//java/io/jackbradshaw/jockstrap/physicsexperiment:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":items",
        "@maven//:javax_inject_javax_inject",
    ],
)
