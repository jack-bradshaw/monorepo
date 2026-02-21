load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "errorsink",
    srcs = ["ErrorSink.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core/model:target",
    ],
)
