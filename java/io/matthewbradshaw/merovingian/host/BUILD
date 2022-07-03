load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "host",
    srcs = [
        "Host.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/merovingian:__subpackages__"],
    deps = [
        "//java/io/matthewbradshaw/merovingian/model",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "HostImpl.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/merovingian:__subpackages__"],
    deps = [
        ":host",
        "//java/io/matthewbradshaw/merovingian/coroutines",
        "//java/io/matthewbradshaw/merovingian/engine",
        "//java/io/matthewbradshaw/merovingian/model",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "HostModule.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/merovingian:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":host",
        ":impl",
    ],
)
