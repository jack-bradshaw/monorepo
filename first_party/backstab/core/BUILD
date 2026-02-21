load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "scope",
    srcs = ["CoreScope.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
