load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/backstab:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "model",
    srcs = ["BackstabComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)
