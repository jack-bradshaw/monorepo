load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

kt_jvm_library(
    name = "impl",
    srcs = [
        "BaseEntity.kt",
        "BaseGame.kt",
        "BaseLevel.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/jockstrap:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":game",
    ],
)
