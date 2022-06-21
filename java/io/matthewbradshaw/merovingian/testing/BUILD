load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

auto_factory()

dagger_rules()

kt_jvm_library(
    name = "testing_lib",
    testonly = True,
    srcs = [
        "Cube.kt",
        "CubeGame.kt",
        "CubeSwarm.kt",
        "Main.java",
        "MainKt.kt",
        "Materials.kt",
        "MaterialsModule.kt",
        "Materialsimpl.kt",
        "TestingComponent.kt",
        "TestingModule.kt",
        "TestingScope.kt",
    ],
    visibility = [
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":dagger",
        "//java/io/matthewbradshaw/merovingian",
        "//java/io/matthewbradshaw/merovingian/engine",
        "//java/io/matthewbradshaw/merovingian/ticker",
        "@maven//:com_google_protobuf_protobuf_java",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

java_binary(
    name = "testing_app",
    testonly = True,
    main_class = "io.matthewbradshaw.merovingian.testing.Main",
    visibility = [
        "//javatests:__subpackages__",
    ],
    runtime_deps = [":testing_lib"],
)
