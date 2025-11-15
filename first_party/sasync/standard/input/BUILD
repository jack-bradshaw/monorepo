load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "input",
    srcs = ["StandardInput.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        "//:dagger",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["StandardInputModule.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":input",
        "//:dagger",
        "//first_party/sasync/inbound/transport",
        "//first_party/sasync/standard:scope",
    ],
)
