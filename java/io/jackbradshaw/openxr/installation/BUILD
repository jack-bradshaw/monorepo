load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

kt_jvm_library(
    name = "installation",
    srcs = [
        "OpenXrInstaller.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/otter/clock",
        "//java/io/jackbradshaw/otter/clock:qualifiers",
        "//java/io/jackbradshaw/otter/engine",
        "//java/io/jackbradshaw/otter/math:java_proto",
        "//java/io/jackbradshaw/otter/math:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/math:proto_extensions",
        "//java/io/jackbradshaw/otter/openxr/model:java_lite_proto",
        "//java/io/jackbradshaw/otter/openxr/standard",
        "//java/io/jackbradshaw/otter/physics:java_proto",
        "//java/io/jackbradshaw/otter/physics:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/physics:proto_extensions",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "OpenXrInstallerImpl.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":installation",
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/otter/clock",
        "//java/io/jackbradshaw/otter/clock:qualifiers",
        "//java/io/jackbradshaw/otter/engine",
        "//java/io/jackbradshaw/otter/math:java_proto",
        "//java/io/jackbradshaw/otter/math:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/math:proto_extensions",
        "//java/io/jackbradshaw/otter/openxr/constants",
        "//java/io/jackbradshaw/otter/openxr/encoding",
        "//java/io/jackbradshaw/otter/openxr/manifests",
        "//java/io/jackbradshaw/otter/openxr/model:java_lite_proto",
        "//java/io/jackbradshaw/otter/openxr/standard",
        "//java/io/jackbradshaw/otter/physics:java_proto",
        "//java/io/jackbradshaw/otter/physics:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/physics:proto_extensions",
        "@maven//:com_google_code_gson_gson",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "OpenXrInstallerModule.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":impl",
        ":installation",
    ],
)

kt_jvm_library(
    name = "mainkt",
    srcs = [
        "MainKt.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":installation",
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/otter/clock",
        "//java/io/jackbradshaw/otter/clock:qualifiers",
        "//java/io/jackbradshaw/otter/engine",
        "//java/io/jackbradshaw/otter/math:java_proto",
        "//java/io/jackbradshaw/otter/math:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/math:proto_extensions",
        "//java/io/jackbradshaw/otter/openxr/constants",
        "//java/io/jackbradshaw/otter/openxr/encoding",
        "//java/io/jackbradshaw/otter/openxr/encoding:impl",
        "//java/io/jackbradshaw/otter/openxr/manifests:impl",
        "//java/io/jackbradshaw/otter/openxr/model:java_lite_proto",
        "//java/io/jackbradshaw/otter/openxr/standard",
        "//java/io/jackbradshaw/otter/physics:java_proto",
        "//java/io/jackbradshaw/otter/physics:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/physics:proto_extensions",
        "@maven//:com_google_code_gson_gson",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

java_binary(
    name = "bin",
    srcs = [
        "Main.java",
    ],
    main_class = "io.jackbradshaw.otter.openxr.installation.Main",
    deps = [
        ":impl",
        ":mainkt",
        "//java/io/jackbradshaw/otter/openxr/encoding:impl",
        "//java/io/jackbradshaw/otter/openxr/manifests:impl",
    ],
)
