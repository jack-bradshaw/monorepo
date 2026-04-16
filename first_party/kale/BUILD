load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/kale:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "scope",
    srcs = ["KaleScope.kt"],
    visibility = ["//first_party/kale:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
