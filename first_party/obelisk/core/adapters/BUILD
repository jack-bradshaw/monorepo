load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/obelisk:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "inflow_adapter",
    srcs = ["InflowAdapter.kt"],
    visibility = PUBLICITY,
)

kt_jvm_library(
    name = "outflow_adapter",
    srcs = ["OutflowAdapter.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/obelisk/core/model:source",
    ],
)
