load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "model",
    srcs = ["BackstabComponent.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)
