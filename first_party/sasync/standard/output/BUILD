load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "output",
    srcs = ["StandardOutput.kt"],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        "//:dagger",
        "//first_party/sasync/transport/outbound",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["StandardOutputModule.kt"],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        ":output",
        "//:dagger",
        "//first_party/sasync:scope",
        "//first_party/sasync/transport/outbound",
        "//first_party/sasync/transport/outbound:impl",
    ],
)
