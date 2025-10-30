load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "dispatchers",
    srcs = ["Dispatchers.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//:dagger",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["DispatchersModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":dispatchers",
        "//:dagger",
        "//first_party/coroutines:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
