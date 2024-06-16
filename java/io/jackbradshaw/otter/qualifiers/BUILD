load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules()

kt_jvm_library(
    name = "qualifiers",
    srcs = [
        "Qualifiers.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":dagger",
    ],
)
