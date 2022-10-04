load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@rules_jvm_external//:defs.bzl", "java_export")

dagger_rules()

kt_jvm_library(
    name = "scope",
    srcs = [
        "OtterScope.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = [
        "Otter.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//java/io/jackbradshaw/otter/config:java_lite_proto",
        "//java/io/jackbradshaw/otter/config:proto_extensions",
        "//java/io/jackbradshaw/otter/coroutines:module",
        "//java/io/jackbradshaw/otter/engine",
        "//java/io/jackbradshaw/otter/engine:impl",
        "//java/io/jackbradshaw/otter/engine:module",
        "//java/io/jackbradshaw/otter/openxr/manifest/encoder:module",
        "//java/io/jackbradshaw/otter/openxr/manifest/generator:module",
        "//java/io/jackbradshaw/otter/openxr/manifest/installer:module",
        "//java/io/jackbradshaw/otter/qualifiers",
        "//java/io/jackbradshaw/otter/timing",
        "//java/io/jackbradshaw/otter/timing:impl",
        "//java/io/jackbradshaw/otter/timing:module",
    ],
)

kt_jvm_library(
    name = "otter",
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
        ":otter",
    ],
)

java_export(
    name = "release",
    srcs = ["Stub.java"],
    maven_coordinates = "io.jackbradshaw:otter:0.0.0",
    pom_template = "pom_template.xml",
    deps = [
        ":otter",
    ],
)
