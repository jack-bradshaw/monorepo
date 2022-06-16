load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "ticker",
    srcs = [
        "Ticker.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = ["@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core"],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "TickerImpl.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        ":ticker",
        "@maven//:javax_inject_javax_inject",
        "//java/io/matthewbradshaw/octavius:scope",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "TickerModule.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        ":dagger",
        ":ticker",
        ":impl",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
