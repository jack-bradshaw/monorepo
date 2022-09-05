load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "scope",
    srcs = [
        "OpenXrScope.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = [
        "OpenXrComponent.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//java/io/jackbradshaw/openxr/config:java_lite_proto",
        "//java/io/jackbradshaw/openxr/encoding",
        "//java/io/jackbradshaw/openxr/encoding:module",
        "//java/io/jackbradshaw/openxr/installation",
        "//java/io/jackbradshaw/openxr/installation:module",
        "//java/io/jackbradshaw/openxr/manifest",
        "//java/io/jackbradshaw/openxr/manifest:module",
    ],
)
