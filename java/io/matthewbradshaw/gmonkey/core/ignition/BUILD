load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "ignition",
    srcs = [
        "Ignition.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/gmonkey:__subpackages__"],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "IgnitionImpl.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/gmonkey:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":ignition",
        "//java/io/matthewbradshaw/gmonkey/core:scope",
        "//java/io/matthewbradshaw/gmonkey/core/engine",
        "@maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "IgnitionModule.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/gmonkey:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":ignition",
        ":impl",
    ],
)
