load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

auto_factory()

dagger_rules()

kt_jvm_library(
    name = "external_module",
    testonly = True,
    srcs = [
        "ExternalModule.kt",
    ],
    visibility = [
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":cube",
        ":dagger",
        ":materials",
        ":scope",
        "//java/io/matthewbradshaw/merovingian",
        "//java/io/matthewbradshaw/merovingian/clock",
        "//java/io/matthewbradshaw/merovingian/engine",
        "//java/io/matthewbradshaw/merovingian/model",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "scope",
    testonly = True,
    srcs = [
        "TestingScope.kt",
    ],
    visibility = [
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "materials",
    testonly = True,
    srcs = [
        "Materials.kt",
        "MaterialsImpl.kt",
        "MaterialsModule.kt",
    ],
    visibility = [
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "cube",
    testonly = True,
    srcs = [
        "Cube.kt",
        "Materials.kt",
        "TestingScope.kt",
    ],
    visibility = [
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":dagger",
        ":materials",
        ":scope",
        "//java/io/matthewbradshaw/kotlinhelpers:concurrency",
        "//java/io/matthewbradshaw/merovingian/model",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "cubeswarm",
    testonly = True,
    srcs = [
        "CubeSwarm.kt",
    ],
    visibility = [
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":cube",
        ":dagger",
        ":materials",
        ":scope",
        "//java/io/matthewbradshaw/kotlinhelpers:concurrency",
        "//java/io/matthewbradshaw/merovingian",
        "//java/io/matthewbradshaw/merovingian/clock",
        "//java/io/matthewbradshaw/merovingian/engine",
        "//java/io/matthewbradshaw/merovingian/model",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "cubegame",
    testonly = True,
    srcs = [
        "CubeGame.kt",
    ],
    visibility = [
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":cube",
        ":cubeswarm",
        ":dagger",
        ":materials",
        ":scope",
        "//java/io/matthewbradshaw/kotlinhelpers:concurrency",
        "//java/io/matthewbradshaw/merovingian/engine",
        "//java/io/matthewbradshaw/merovingian/model",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "component",
    testonly = True,
    srcs = [
        "TestingComponent.kt",
    ],
    visibility = [
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":cube",
        ":cubegame",
        ":cubeswarm",
        ":dagger",
        ":external_module",
        ":materials",
        ":scope",
        "//java/io/matthewbradshaw/merovingian",
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
        "//javatests:__subpackages__",
    ],
    deps = [
        ":component",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_desktop",
        "@maven//:org_jmonkeyengine_jme3_lwjgl3",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

java_binary(
    name = "testing",
    testonly = True,
    main_class = "io.matthewbradshaw.merovingian.testing.Main",
    visibility = [
        "//javatests:__subpackages__",
    ],
    runtime_deps = [
        ":main",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_desktop",
        "@maven//:org_jmonkeyengine_jme3_lwjgl3",
        "@maven//:org_jmonkeyengine_jme3_vr",
        "@maven//:org_lwjgl_lwjgl",
    ],
)

java_binary(
    name = "hello",
    testonly = True,
    srcs = ["Thing.java"],
    main_class = "io.matthewbradshaw.merovingian.testing.Thing",
    visibility = [
        "//javatests:__subpackages__",
    ],
    deps = [
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_desktop",
        "@maven//:org_jmonkeyengine_jme3_lwjgl3",
        "@maven//:org_jmonkeyengine_jme3_testdata",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)
