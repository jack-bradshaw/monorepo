load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_jvm_external//:defs.bzl", "java_export")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "scope",
    srcs = [
        "OtterScope.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
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
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//first_party/otter/config:kt_jvm_proto",
        "//first_party/otter/config:proto_extensions",
        "//first_party/otter/coroutines:module",
        "//first_party/otter/engine/core",
        "//first_party/otter/engine/core:impl",
        "//first_party/otter/engine/core:module",
        "//first_party/otter/openxr/manifest/encoder:module",
        "//first_party/otter/openxr/manifest/generator:module",
        "//first_party/otter/openxr/manifest/installer:module",
        "//first_party/otter/qualifiers",
        "//first_party/otter/scene/stage:module",
        "//first_party/otter/timing",
        "//first_party/otter/timing:impl",
        "//first_party/otter/timing:module",
    ],
)

kt_jvm_library(
    name = "otter",
    visibility = [
        "//first_party:__subpackages__",
    ],
    exports = [
        ":component",
        ":scope",
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
