load("@rules_jvm_external//:defs.bzl", "java_export")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load(":publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["OtterScope.kt"],
    visibility = PUBLICITY,
)

kt_jvm_library(
    name = "component",
    srcs = ["OtterComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/otter/config:kt_jvm_proto",
        "//first_party/otter/engine/core",
        "//first_party/otter/openxr/manifest/encoder",
        "//first_party/otter/openxr/manifest/generator",
        "//first_party/otter/openxr/manifest/installer",
        "//first_party/otter/qualifiers",
        "//first_party/otter/scene/stage",
        "//first_party/otter/timing",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["OtterComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":scope",
        "//first_party/otter/config:kt_jvm_proto",
        "//first_party/otter/config:proto_extensions",
        "//first_party/otter/coroutines:module",
        "//first_party/otter/engine/core:module",
        "//first_party/otter/openxr/manifest/encoder:module",
        "//first_party/otter/openxr/manifest/generator:module",
        "//first_party/otter/openxr/manifest/installer:module",
        "//first_party/otter/scene/stage:module",
        "//first_party/otter/timing:module",
    ],
)

java_export(
    name = "release",
    srcs = ["Stub.java"],
    maven_coordinates = "com.jackbradshaw:otter:0.0.0",
    pom_template = "pom_template.xml",
    tags = ["no-javadocs"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":component_impl",
        ":scope",
        "@com_jackbradshaw_maven//:com_google_protobuf_protobuf_kotlin",
    ],
)
