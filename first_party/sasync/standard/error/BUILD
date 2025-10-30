load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "error",
    srcs = ["StandardError.kt"],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        "//:dagger",
        "//first_party/sasync/transport/outbound",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["StandardErrorModule.kt"],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        ":error",
        "//:dagger",
        "//first_party/sasync:scope",
        "//first_party/sasync/transport/outbound",
        "//first_party/sasync/transport/outbound:impl",
    ],
)
