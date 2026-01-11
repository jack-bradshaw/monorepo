load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "testing",
    srcs = ["TestCoroutines.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//:dagger",
        "//first_party/coroutines/testing/advancer:module",
        "//first_party/coroutines/testing/io:testmodule",
        "//first_party/coroutines/testing/launcher:module",
        "//first_party/coroutines/testing/scope:module",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
