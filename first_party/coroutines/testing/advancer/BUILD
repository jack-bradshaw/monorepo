load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "advancer",
    srcs = ["Advancer.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["AdvancerImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":advancer",
        "//:dagger",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["AdvancerModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":advancer",
        ":impl",
        "//:dagger",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["AdvancerTest.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":advancer",
        "//first_party/wheatley",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
