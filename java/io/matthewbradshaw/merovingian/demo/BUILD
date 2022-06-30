load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "scope",
    testonly = True,
    srcs = [
        "DemoScope.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "external_module",
    testonly = True,
    srcs = [
        "ExternalModule.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "@maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library(
    name = "component",
    testonly = True,
    srcs = [
        "DemoComponent.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":external_module",
        ":scope",
        "//java/io/matthewbradshaw/merovingian",
        "//java/io/matthewbradshaw/merovingian/demo/items",
        "//java/io/matthewbradshaw/merovingian/demo/items:module",
        "//java/io/matthewbradshaw/merovingian/demo/materials:module",
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
        "//java/io/matthewbradshaw/merovingian/demo:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":component",
        ":external_module",
    ],
)

java_binary(
    name = "demo",
    testonly = True,
    main_class = "io.matthewbradshaw.merovingian.demo.main.Main",
    visibility = [
        "//java/io/matthewbradshaw/merovingian/demo:__subpackages__",
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
