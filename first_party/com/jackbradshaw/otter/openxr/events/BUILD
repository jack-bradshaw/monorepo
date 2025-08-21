load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "events",
    srcs = [
        "Events.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        "//first_party/com/jackbradshaw/otter/openxr/manifest/encoder",
        "//first_party/com/jackbradshaw/otter/openxr/model:kt_jvm_proto",
        "//first_party/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "EventsImpl.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":events",
        "//first_party/com/jackbradshaw/otter:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "EventsModule.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":events",
        ":impl",
    ],
)
