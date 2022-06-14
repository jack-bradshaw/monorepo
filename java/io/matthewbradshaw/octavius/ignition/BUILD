load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "ignition",
    srcs = [
        "Ignition.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = ["@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core"],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "IgnitionImpl.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        ":ignition",
        "@maven//:javax_inject_javax_inject",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "IgnitionModule.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        ":dagger",
        ":ignition",
        ":impl",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
