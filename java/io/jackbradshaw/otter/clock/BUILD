load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "qualifiers",
    srcs = [
        "Qualifiers.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "clock",
    srcs = [
        "Clock.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "PhysicsClock.kt",
        "RealClock.kt",
        "RenderingClock.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":clock",
        "//java/io/jackbradshaw/otter:scope",
        "//java/io/jackbradshaw/otter/coroutines",
        "//java/io/jackbradshaw/otter/engine",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "ClockModule.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":clock",
        ":dagger",
        ":impl",
        ":qualifiers",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
