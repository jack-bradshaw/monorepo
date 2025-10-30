load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "pulsar",
    srcs = ["Pulsar.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["PulsarImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":pulsar",
        "//:dagger",
        "//first_party/concurrency:scope",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["PulsarModule.kt"],
    visibility = ["//first_party/concurrency:__subpackages__"],
    deps = [
        ":impl",
        ":pulsar",
        "//:dagger",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["PulsarTest.kt"],
    visibility = ["//first_party/concurrency:__subpackages__"],
    deps = [
        ":pulsar",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test(
    name = "impl_test",
    srcs = ["PulsarImplTest.kt"],
    test_class = "com.jackbradshaw.concurrency.pulsar.PulsarImplTest",
    visibility = ["//first_party/concurrency:__subpackages__"],
    deps = [
        ":impl",
        ":pulsar",
        ":test",
        "//:dagger",
        "//first_party/concurrency:scope",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
