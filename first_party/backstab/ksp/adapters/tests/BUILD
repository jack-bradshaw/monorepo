load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "stub",
    testonly = True,
    srcs = ["Stub.kt"],
    visibility = ["//first_party/backstab/ksp/adapters/tests:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
