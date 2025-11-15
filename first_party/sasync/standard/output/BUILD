load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "output",
    srcs = ["StandardOutput.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        "//:dagger",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["StandardOutputModule.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":output",
        "//:dagger",
        "//first_party/sasync/outbound/transport",
        "//first_party/sasync/standard:scope",
    ],
)
