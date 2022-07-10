load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "scope",
    srcs = [
        "FranklScope.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/frankl:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
    ],
)

kt_jvm_library(
    name = "merovingian",
    srcs = [
        "FranklComponent.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/frankl:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":scope",
        "//java/io/matthewbradshaw/frankl/clock",
        "//java/io/matthewbradshaw/frankl/clock:impl",
        "//java/io/matthewbradshaw/frankl/clock:module",
        "//java/io/matthewbradshaw/frankl/clock:qualifiers",
        "//java/io/matthewbradshaw/frankl/config",
        "//java/io/matthewbradshaw/frankl/engine",
        "//java/io/matthewbradshaw/frankl/engine:impl",
        "//java/io/matthewbradshaw/frankl/engine:module",
        "//java/io/matthewbradshaw/frankl/host",
        "//java/io/matthewbradshaw/frankl/host:impl",
        "//java/io/matthewbradshaw/frankl/host:module",
    ],
)
