load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/kale:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "sources",
    testonly = True,
    srcs = ["Sources.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        "//first_party/kale/model:source",
    ],
)
