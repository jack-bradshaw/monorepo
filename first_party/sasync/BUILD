load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "scope",
    srcs = [
        "SasyncScope.kt",
    ],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        "//:dagger",
    ],
)
