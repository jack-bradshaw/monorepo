load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "events",
    srcs = [
        "Events.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/clearxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/jackbradshaw/clearxr/manifest/encoder",
        "//java/io/jackbradshaw/clearxr/model:java_lite_proto",
        "//java/io/jackbradshaw/clearxr/standard",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "EventsImpl.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/clearxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":events",
        "//java/io/jackbradshaw/clearxr:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "EventsModule.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/clearxr:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":events",
        ":impl",
    ],
)
