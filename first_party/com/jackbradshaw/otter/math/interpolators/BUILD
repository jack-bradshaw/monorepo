load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//autofactory:autofactory.bzl", "auto_factory")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

auto_factory()

kt_jvm_library(
    name = "interpolators",
    srcs = [
        "QuaternionInterpolator.kt",
        "QuaternionNlerpInterpolator.kt",
        "QuaternionSlerpInterpolator.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        "//first_party/com/jackbradshaw/otter/math/model:kt_jvm_proto",
        "//first_party/com/jackbradshaw/otter/math/model:proto_extensions",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "QuaternionNlerpInterpolator.kt",
        "QuaternionSlerpInterpolator.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":interpolators",
        "//first_party/com/jackbradshaw/otter/math/model:kt_jvm_proto",
        "//first_party/com/jackbradshaw/otter/math/model:proto_extensions",
    ],
)
