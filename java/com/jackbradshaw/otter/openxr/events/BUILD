load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "events",
    srcs = [
        "Events.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/manifest/encoder",
        "//java/com/jackbradshaw/otter/openxr/model:kt_jvm_proto",
        "//java/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "EventsImpl.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":events",
        "//java/com/jackbradshaw/otter:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "EventsModule.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":events",
        ":impl",
    ],
)
