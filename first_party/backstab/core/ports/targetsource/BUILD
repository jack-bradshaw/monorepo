load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "targetsource",
    srcs = ["TargetSource.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core/model:target",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
