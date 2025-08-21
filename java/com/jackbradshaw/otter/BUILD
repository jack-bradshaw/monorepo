load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_java//java:java_binary.bzl", "java_binary")
load("@rules_jvm_external//:defs.bzl", "java_export")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "scope",
    srcs = [
        "OtterScope.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
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
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//java/com/jackbradshaw/otter/config:kt_jvm_proto",
        "//java/com/jackbradshaw/otter/config:proto_extensions",
        "//java/com/jackbradshaw/otter/coroutines:module",
        "//java/com/jackbradshaw/otter/engine/core",
        "//java/com/jackbradshaw/otter/engine/core:impl",
        "//java/com/jackbradshaw/otter/engine/core:module",
        "//java/com/jackbradshaw/otter/openxr/manifest/encoder:module",
        "//java/com/jackbradshaw/otter/openxr/manifest/generator:module",
        "//java/com/jackbradshaw/otter/openxr/manifest/installer:module",
        "//java/com/jackbradshaw/otter/qualifiers",
        "//java/com/jackbradshaw/otter/scene/stage:module",
        "//java/com/jackbradshaw/otter/timing",
        "//java/com/jackbradshaw/otter/timing:impl",
        "//java/com/jackbradshaw/otter/timing:module",
    ],
)

kt_jvm_library(
    name = "otter",
    visibility = [
        "//java/com/jackbradshaw:__subpackages__",
    ],
    exports = [
        ":component",
        ":scope",
    ],
)

java_binary(
    name = "binary",
    visibility = [
        "//java/com/jackbradshaw:__subpackages__",
    ],
    runtime_deps = [
        ":otter",
    ],
)

java_export(
    name = "release",
    srcs = ["Stub.java"],
    maven_coordinates = "com.jackbradshaw:otter:0.0.0",
    pom_template = "pom_template.xml",
    deps = [
        ":otter",
        "@com_jackbradshaw_maven//:com_google_protobuf_protobuf_kotlin",
    ],
)
