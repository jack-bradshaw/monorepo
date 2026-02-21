load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "modulesink",
    srcs = ["ModuleSink.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core/model:module",
        "//first_party/backstab/core/model:target",
    ],
)
