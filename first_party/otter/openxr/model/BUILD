load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("//first_party/publicity:defs.bzl", "quarantined")

PUBLICITY = quarantined("//first_party/otter/openxr/model")

proto_library(
    name = "proto",
    srcs = [
        "input.proto",
        "input_component.proto",
        "input_identifier.proto",
        "input_location.proto",
        "interaction_profile.proto",
        "output.proto",
        "output_identifier.proto",
        "output_location.proto",
        "user.proto",
    ],
    visibility = PUBLICITY,
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = PUBLICITY,
    deps = [":proto"],
)
