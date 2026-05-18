load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/closet:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "suspending",
    srcs = ["SuspendableClosable.kt"],
    visibility = PUBLICITY,
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["SuspendableClosableTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":suspending",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
