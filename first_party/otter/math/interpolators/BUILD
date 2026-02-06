load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/publicity:defs.bzl", "quarantined")

PUBLICITY = quarantined("//first_party/otter/math/interpolators")

kt_jvm_library(
    name = "interpolators",
    srcs = [
        "QuaternionInterpolator.kt",
        "QuaternionNlerpInterpolator.kt",
        "QuaternionSlerpInterpolator.kt",
    ],
    visibility = PUBLICITY,
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
    deps = [
        ":interpolators",
        "//first_party/autofactory",
        "//first_party/otter/math/model:kt_jvm_proto",
        "//first_party/otter/math/model:proto_extensions",
    ],
)
