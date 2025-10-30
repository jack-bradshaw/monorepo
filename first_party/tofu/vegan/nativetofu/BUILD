load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "tofucli",
    srcs = ["TofuCli.kt"],
    visibility = ["//visibility:public"],
)

kt_jvm_library(
    name = "simplex",
    srcs = ["TofuCliSimplex.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":tofucli",
        "@bazel_tools//tools/java/runfiles",
    ],
)
