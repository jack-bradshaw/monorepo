load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "entrypoint",
    srcs = [
        "Host.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/frankl:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "//java/io/matthewbradshaw/frankl/model",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "HostImpl.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/frankl:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":entrypoint",
        "//java/io/matthewbradshaw/klu/flow",
        "//java/io/matthewbradshaw/frankl/coroutines",
        "//java/io/matthewbradshaw/frankl/engine",
        "//java/io/matthewbradshaw/frankl/model",
        "//java/io/matthewbradshaw/frankl/model:snapshot_java_lite_proto",
        "//java/io/matthewbradshaw/frankl/snapshot",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_bullet",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "HostModule.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw/frankl:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":dagger",
        ":entrypoint",
        ":impl",
    ],
)
