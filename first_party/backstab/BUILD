load("@rules_kotlin//kotlin:core.bzl", "kt_ksp_plugin")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/backstab:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "backstab",
    visibility = ["//first_party/backstab:__subpackages__"],
    exports = [
        "//first_party/backstab/core/generator",
        "//first_party/backstab/core/main",
        "//first_party/backstab/core/model:module",
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/core/model:type",
        "//first_party/backstab/core/repository",
    ],
)

kt_ksp_plugin(
    name = "plugin",
    processor_class = "com.jackbradshaw.oksp.entrypoint.EntryPointImpl",
    visibility = PUBLICITY,
    deps = [
        "//first_party/backstab/ksp/entrypoint:impl",
        "//first_party/oksp/entrypoint:entrypoint_impl_declared",
    ],
)
