load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/releasing/maven:defs.bzl", "maven_release")
load("//first_party/releasing/copybara:defs.bzl", "copybara_release")

copybara_release(
    name = "mirror",
    destination_url = "https://github.com/jack-bradshaw/otter.git",
    source_dir = "first_party/otter",
)

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = [
        "OtterScope.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
    ],
)

kt_jvm_library(
    name = "component",
    srcs = [
        "OtterComponent.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
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
    srcs = [
        "OtterComponentImpl.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
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

kt_jvm_library(
    name = "otter",
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    exports = [
        ":component",
        ":component_impl",
        ":scope",
    ],
)

maven_release(
    name = "release",
    coordinates = "com.jackbradshaw:otter:20260131.103100",
    deps = [
        ":otter",
        "@com_jackbradshaw_maven//:com_google_protobuf_protobuf_kotlin",
    ],
    project_description = "A experimental and incomplete video game engine based on JMonkey.",
    project_name = "Otter Game Engine",
)
