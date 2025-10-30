load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "interpolators",
    srcs = [
        "QuaternionInterpolator.kt",
        "QuaternionNlerpInterpolator.kt",
        "QuaternionSlerpInterpolator.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        "//first_party/autofactory",
        "//first_party/otter/math/model:kt_jvm_proto",
        "//first_party/otter/math/model:proto_extensions",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "QuaternionNlerpInterpolator.kt",
        "QuaternionSlerpInterpolator.kt",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [
        ":interpolators",
        "//first_party/autofactory",
        "//first_party/otter/math/model:kt_jvm_proto",
        "//first_party/otter/math/model:proto_extensions",
    ],
)
