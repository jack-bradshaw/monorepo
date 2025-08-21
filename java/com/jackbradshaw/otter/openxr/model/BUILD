load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")

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
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [":proto"],
)
