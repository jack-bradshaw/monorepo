load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@rules_jvm_external//:defs.bzl", "java_export")

dagger_rules()

kt_jvm_library(
    name = "scope",
    srcs = [
        "OmniXrScope.kt",
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
        "OmniXr.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//java/io/jackbradshaw/omnixr/config:java_lite_proto",
        "//java/io/jackbradshaw/omnixr/config:proto_extensions",
        "//java/io/jackbradshaw/omnixr/manifest/encoder",
        "//java/io/jackbradshaw/omnixr/manifest/encoder:module",
        "//java/io/jackbradshaw/omnixr/manifest/generator",
        "//java/io/jackbradshaw/omnixr/manifest/generator:module",
        "//java/io/jackbradshaw/omnixr/manifest/installer",
        "//java/io/jackbradshaw/omnixr/manifest/installer:module",
    ],
)

kt_jvm_library(
    name = "omnixr",
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
        ":omnixr",
    ],
)

java_export(
    name = "release",
    srcs = ["Stub.java"],
    maven_coordinates = "io.jackbradshaw:omnixr:0.0.0",
    pom_template = "pom_template.xml",
    deps = [
        ":omnixr",
    ],
)
