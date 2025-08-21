load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "timing",
    srcs = [
        "Clock.kt",
    ],
    visibility = ["//java/com/jackbradshaw/otter:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "PhysicsClock.kt",
        "RenderingClock.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":timing",
        "//java/com/jackbradshaw/otter:scope",
        "//java/com/jackbradshaw/otter/coroutines",
        "//java/com/jackbradshaw/otter/engine/core",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "TimingModule.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":impl",
        ":timing",
        "//java/com/jackbradshaw/otter/qualifiers",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
