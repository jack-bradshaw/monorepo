load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/otter:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "timing",
    srcs = [
        "Clock.kt",
    ],
    visibility = PUBLICITY,
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
    visibility = ["//visibility:private"],
    deps = [
        ":timing",
        "//first_party/otter/coroutines",
        "//first_party/otter/engine/core",
        "//first_party/otter/qualifiers",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = [
        "TimingModule.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        ":impl",
        ":timing",
        "//first_party/otter:scope",
    ],
)
