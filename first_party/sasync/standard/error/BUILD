load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "error",
    srcs = ["StandardError.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        "//:dagger",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["StandardErrorModule.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":error",
        "//:dagger",
        "//first_party/sasync/outbound/transport",
        "//first_party/sasync/standard:scope",
    ],
)
