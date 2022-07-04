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
        ":scope",
        "//java/io/matthewbradshaw/merovingian",
        "//java/io/matthewbradshaw/merovingian/demo/items",
        "//java/io/matthewbradshaw/merovingian/demo/items:module",
        "//java/io/matthewbradshaw/merovingian/demo/materials:module",
        "//java/io/matthewbradshaw/merovingian/demo/support:module",
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
    ],
)

java_binary(
    name = "demo",
    testonly = True,
    main_class = "io.matthewbradshaw.merovingian.demo.Main",
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
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_bullet_native",
        "@maven//:org_lwjgl_lwjgl",
    ],
)
