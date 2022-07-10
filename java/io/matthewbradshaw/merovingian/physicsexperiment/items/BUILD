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
        "//java/io/matthewbradshaw/merovingian/physicsexperiment:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/matthewbradshaw/merovingian/model/world",
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
        "//java/io/matthewbradshaw/merovingian/physicsexperiment:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":items",
        "//java/io/matthewbradshaw/klu",
        "//java/io/matthewbradshaw/klu/flow",
        "//java/io/matthewbradshaw/merovingian/clock",
        "//java/io/matthewbradshaw/merovingian/clock:qualifiers",
        "//java/io/matthewbradshaw/merovingian/coroutines",
        "//java/io/matthewbradshaw/merovingian/engine",
        "//java/io/matthewbradshaw/merovingian/model/math:math_ext",
        "//java/io/matthewbradshaw/merovingian/model/math:math_java_lite_proto",
        "//java/io/matthewbradshaw/merovingian/model/world",
        "//java/io/matthewbradshaw/merovingian/physicsexperiment:scope",
        "//java/io/matthewbradshaw/merovingian/physicsexperiment/constants",
        "//java/io/matthewbradshaw/merovingian/physicsexperiment/materials",
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
        "//java/io/matthewbradshaw/merovingian/physicsexperiment:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":items",
        "@maven//:javax_inject_javax_inject",
    ],
)
