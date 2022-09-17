load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@rules_jvm_external//:defs.bzl", "java_export")

dagger_rules()

kt_jvm_library(
    name = "scope",
    srcs = [
        "ClearXrScope.kt",
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
        "ClearXr.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//java/io/jackbradshaw/clearxr/config:java_lite_proto",
        "//java/io/jackbradshaw/clearxr/config:proto_extensions",
        "//java/io/jackbradshaw/clearxr/manifest/encoder",
        "//java/io/jackbradshaw/clearxr/manifest/encoder:module",
        "//java/io/jackbradshaw/clearxr/manifest/generator",
        "//java/io/jackbradshaw/clearxr/manifest/generator:module",
        "//java/io/jackbradshaw/clearxr/manifest/installer",
        "//java/io/jackbradshaw/clearxr/manifest/installer:module",
    ],
)

kt_jvm_library(
    name = "clearxr",
    visibility = [
        "//java/io/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    exports = [
        ":component",
        ":scope",
    ],
)

java_binary(
    name = "binary",
    visibility = [
        "//java/io/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    runtime_deps = [
        ":clearxr",
    ],
)

java_export(
    name = "release",
    srcs = ["Stub.java"],
    maven_coordinates = "io.jackbradshaw:clearxr:0.0.0",
    pom_template = "pom_template.xml",
    deps = [
        ":clearxr",
    ],
)
