load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "control_service",
    srcs = ["ObeliskControlService.kt"],
    visibility = ["//visibility:public"],
)

kt_jvm_library(
    name = "data_service",
    srcs = ["ObeliskDataService.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/sluice:sluice",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "error_service",
    srcs = ["ObeliskErrorService.kt"],
    visibility = ["//visibility:public"],
)



kt_jvm_library(
    name = "logging",
    srcs = ["ObeliskLoggingService.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/obelisk/core/model:log_level",
    ],
)
