load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/publicity:defs.bzl", "public")

PUBLICITY = public()

proto_library(
    name = "frequency",
    srcs = ["frequency.proto"],
    visibility = PUBLICITY,
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = PUBLICITY,
    deps = [":frequency"],
)

kt_jvm_library(
    name = "kt_helpers",
    srcs = ["FrequencyHelpers.kt"],
    visibility = PUBLICITY,
    deps = [":kt_jvm_proto"],
)

kt_jvm_test(
    name = "kt_helpers_test",
    srcs = ["FrequencyHelpersTest.kt"],
    test_class = "com.jackbradshaw.universal.frequency.FrequencyHelpersTest",
    deps = [
        ":kt_helpers",
        ":kt_jvm_proto",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)
