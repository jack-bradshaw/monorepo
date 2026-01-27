load("@rules_java//java:java_binary.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "scope",
    testonly = True,
    srcs = ["DemoScope.kt"],
    visibility = ["//first_party/otter/demo:__subpackages__"],
)

kt_jvm_library(
    name = "component",
    testonly = True,
    srcs = ["DemoComponent.kt"],
    visibility = ["//first_party/otter/demo:__subpackages__"],
    deps = [
        "//first_party/otter/demo/items",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    testonly = True,
    srcs = ["DemoComponentImpl.kt"],
    visibility = ["//first_party/otter/demo:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//first_party/otter:component",
        "//first_party/otter/demo/items:module",
        "//first_party/otter/demo/materials:module",
        "//first_party/otter/demo/support:module",
    ],
)

kt_jvm_library(
    name = "main",
    testonly = True,
    srcs = [
        "Main.java",
        "MainKt.kt",
    ],
    visibility = ["//first_party/otter/demo:__subpackages__"],
    deps = [
        ":component",
        ":component_impl",
        "//first_party/otter:component_impl",
        "//first_party/otter/config:kt_jvm_proto",
        "//first_party/otter/config:proto_extensions",
        "//first_party/otter/engine/config:kt_jvm_proto",
        "//first_party/otter/openxr/config:kt_jvm_proto",
        "//first_party/otter/openxr/config:proto_extensions",
    ],
)

java_binary(
    name = "demo",
    testonly = True,
    main_class = "com.jackbradshaw.otter.demo.Main",
    visibility = ["//first_party/otter/demo:__subpackages__"],
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
