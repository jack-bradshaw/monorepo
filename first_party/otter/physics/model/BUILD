load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/otter:publicity.bzl", "PUBLICITY")

proto_library(
    name = "placement_proto",
    srcs = [
        "placement.proto",
    ],
    visibility = PUBLICITY,
    deps = ["//first_party/otter/math/model:proto"],
)

kt_jvm_proto_library(
    name = "placement_kt_jvm_proto",
    visibility = PUBLICITY,
    deps = [":placement_proto"],
)

kt_jvm_library(
    name = "placement_extensions",
    srcs = [
        "PlacementExt.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        ":placement_kt_jvm_proto",
        "//first_party/otter/math/model:kt_jvm_proto",
        "//first_party/otter/math/model:proto_extensions",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

proto_library(
    name = "force_proto",
    srcs = [
        "force.proto",
    ],
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = ["//first_party/otter/math/model:proto"],
)

kt_jvm_proto_library(
    name = "force_kt_jvm_proto",
    visibility = [
        "//first_party/otter:__subpackages__",
    ],
    deps = [":force_proto"],
)

kt_jvm_library(
    name = "force_extensions",
    srcs = [
        "ForceExt.kt",
    ],
    visibility = ["//first_party/otter:__subpackages__"],
    deps = [
        ":force_kt_jvm_proto",
        "//first_party/otter/math/model:kt_jvm_proto",
        "//first_party/otter/math/model:proto_extensions",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

# DEPRECATED: Use :placement_kt_jvm_proto or :force_kt_jvm_proto instead.
alias(
    name = "kt_jvm_proto",
    actual = ":placement_kt_jvm_proto",
    visibility = ["//first_party/otter:__subpackages__"],
)

# DEPRECATED: Use :placement_extensions or :force_extensions instead.
alias(
    name = "proto_extensions",
    actual = ":placement_extensions",
    visibility = ["//first_party/otter:__subpackages__"],
)
