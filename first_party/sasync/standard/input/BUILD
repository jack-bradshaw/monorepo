load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "input",
    srcs = ["StandardInput.kt"],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        "//:dagger",
        "//first_party/sasync/transport/inbound",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["StandardInputModule.kt"],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        ":input",
        "//:dagger",
        "//first_party/sasync:scope",
        "//first_party/sasync/transport/inbound",
        "//first_party/sasync/transport/inbound:impl",
    ],
)
