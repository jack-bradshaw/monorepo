load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "keyboard",
    srcs = [
        "Keyboard.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":dagger",
        "//java/io/jackbradshaw/otter:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "keyboard_impl",
    srcs = [
        "KeyboardImpl.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":dagger",
        ":keyboard",
        "//java/io/jackbradshaw/otter:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "keyboard_module",
    srcs = [
        "KeyboardModule.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":dagger",
        ":keyboard",
        ":keyboard_impl",
        "//java/io/jackbradshaw/otter:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "mouse",
    srcs = [
        "Mouse.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":dagger",
        "//java/io/jackbradshaw/otter:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "mouse_impl",
    srcs = [
        "MouseImpl.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":dagger",
        ":mouse",
        "//java/io/jackbradshaw/otter:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "mouse_module",
    srcs = [
        "MouseModule.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":dagger",
        ":mouse",
        ":mouse_impl",
        "//java/io/jackbradshaw/otter:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "vr_controller",
    srcs = [
        "VrController.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":dagger",
        "//java/io/jackbradshaw/otter:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "vr_controller_impl",
    srcs = [
        "VrControllerImpl.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":dagger",
        ":vr_controller",
        "//java/io/jackbradshaw/otter:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)

kt_jvm_library(
    name = "vr_controller_module",
    srcs = [
        "VrControllerModule.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":dagger",
        ":vr_controller",
        ":vr_controller_impl",
        "//java/io/jackbradshaw/otter:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jmonkeyengine_jme3_vr",
    ],
)
