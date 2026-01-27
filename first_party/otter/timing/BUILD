load("//first_party/dagger:defs.bzl", 'kt_jvm_library_with_dagger')
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "timing",
    srcs = [
        "Clock.kt",
    ],
    visibility = ["//first_party/otter:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = [
        "PhysicsClock.kt",
        "RenderingClock.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        ":timing",
        "//first_party/otter:scope",
        "//first_party/otter/coroutines",
        "//first_party/otter/engine/core",
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
        "//first_party/otter/qualifiers",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
