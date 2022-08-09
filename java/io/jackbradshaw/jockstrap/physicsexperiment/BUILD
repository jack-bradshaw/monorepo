load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "scope",
    testonly = True,
    srcs = [
        "PhysicsExperimentScope.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/jockstrap/physicsexperiment:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "component",
    testonly = True,
    srcs = [
        "PhysicsExperimentComponent.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/jockstrap/physicsexperiment:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//java/io/jackbradshaw/jockstrap",
        "//java/io/jackbradshaw/jockstrap/physicsexperiment/items",
        "//java/io/jackbradshaw/jockstrap/physicsexperiment/items:module",
        "//java/io/jackbradshaw/jockstrap/physicsexperiment/materials:module",
        "//java/io/jackbradshaw/jockstrap/physicsexperiment/support:module",
    ],
)

kt_jvm_library(
    name = "main",
    testonly = True,
    srcs = [
        "Main.java",
        "MainKt.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/jockstrap/physicsexperiment:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":component",
    ],
)

java_binary(
    name = "physicsexperiment",
    testonly = True,
    main_class = "Main",
    visibility = [
        "//java/io/jackbradshaw/jockstrap/physicsexperiment:__subpackages__",
        "//javatests:__subpackages__",
    ],
    runtime_deps = [
        ":main",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_bullet_native",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_desktop",
        "@maven//:org_jmonkeyengine_jme3_lwjgl3",
        "@maven//:org_jmonkeyengine_jme3_vr",
        "@maven//:org_lwjgl_lwjgl",
    ],
)
