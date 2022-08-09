load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "scope",
    srcs = [
        "JockstrapScope.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = [
        "JockstrapComponent.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//java/io/jackbradshaw/jockstrap/clock",
        "//java/io/jackbradshaw/jockstrap/clock:impl",
        "//java/io/jackbradshaw/jockstrap/clock:module",
        "//java/io/jackbradshaw/jockstrap/clock:qualifiers",
        "//java/io/jackbradshaw/jockstrap/config",
        "//java/io/jackbradshaw/jockstrap/engine",
        "//java/io/jackbradshaw/jockstrap/engine:impl",
        "//java/io/jackbradshaw/jockstrap/engine:module",
    ],
)
