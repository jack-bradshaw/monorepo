load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "events",
    srcs = [
        "Events.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        "//first_party/otter/openxr/manifest/encoder",
        "//first_party/otter/openxr/model:kt_jvm_proto",
        "//first_party/otter/openxr/standard",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "EventsImpl.kt",
    ],
    visibility = ["//visibility:private"],
    deps = [
        ":events",
        "//first_party/otter:scope",
        "//first_party/otter/openxr/manifest/encoder",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = [
        "EventsModule.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        ":events",
        ":impl",
    ],
)
