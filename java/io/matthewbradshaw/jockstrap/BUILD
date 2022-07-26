load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "scope",
    srcs = [
        "JockstrapScope.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/jockstrap:__subpackages__",
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
        "//java/io/matthewbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//java/io/matthewbradshaw/jockstrap/clock",
        "//java/io/matthewbradshaw/jockstrap/clock:impl",
        "//java/io/matthewbradshaw/jockstrap/clock:module",
        "//java/io/matthewbradshaw/jockstrap/clock:qualifiers",
        "//java/io/matthewbradshaw/jockstrap/config",
        "//java/io/matthewbradshaw/jockstrap/engine",
        "//java/io/matthewbradshaw/jockstrap/engine:impl",
        "//java/io/matthewbradshaw/jockstrap/engine:module",
    ],
)
