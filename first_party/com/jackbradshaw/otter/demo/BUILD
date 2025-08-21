load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_java//java:java_binary.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "scope",
    testonly = True,
    srcs = [
        "DemoScope.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter/demo:__subpackages__",
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
        "//first_party/com/jackbradshaw/otter/demo:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//first_party/com/jackbradshaw/otter",
        "//first_party/com/jackbradshaw/otter/demo/items",
        "//first_party/com/jackbradshaw/otter/demo/items:module",
        "//first_party/com/jackbradshaw/otter/demo/materials:module",
        "//first_party/com/jackbradshaw/otter/demo/support:module",
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
        "//first_party/com/jackbradshaw/otter/demo:__subpackages__",
    ],
    deps = [
        ":component",
        "//first_party/com/jackbradshaw/otter/config:kt_jvm_proto",
        "//first_party/com/jackbradshaw/otter/engine/config:kt_jvm_proto",
        "//first_party/com/jackbradshaw/otter/openxr/config:kt_jvm_proto",
    ],
)

java_binary(
    name = "demo",
    testonly = True,
    main_class = "com.jackbradshaw.otter.demo.Main",
    visibility = [
        "//first_party/com/jackbradshaw/otter/demo:__subpackages__",
    ],
    runtime_deps = [
        ":main",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_bullet",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_bullet_native",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_desktop",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_lwjgl3",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_vr",
        "@com_jackbradshaw_maven//:org_lwjgl_lwjgl",
    ],
)
