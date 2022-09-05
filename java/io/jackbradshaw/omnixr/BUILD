load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

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
        "OmniXrComponent.kt",
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
