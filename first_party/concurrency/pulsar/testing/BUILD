load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "testing",
    srcs = ["TestPulsar.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        "//first_party/concurrency/pulsar",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["TestPulsarImpl.kt"],
    visibility = ["//first_party/concurrency:__subpackages__"],
    deps = [
        ":testing",
        "//:dagger",
        "//first_party/concurrency:scope",
        "//first_party/concurrency/pulsar",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["TestPulsarModule.kt"],
    visibility = ["//first_party/concurrency:__subpackages__"],
    deps = [
        ":impl",
        ":testing",
        "//:dagger",
        "//first_party/concurrency/pulsar",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["TestPulsarTest.kt"],
    visibility = ["//first_party/concurrency:__subpackages__"],
    deps = [
        ":testing",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test(
    name = "impl_test",
    timeout = "short",
    srcs = ["TestPulsarImplTest.kt"],
    test_class = "com.jackbradshaw.concurrency.pulsar.testing.TestPulsarImplTest",
    visibility = ["//first_party/concurrency:__subpackages__"],
    deps = [
        ":impl",
        ":module",
        ":test",
        ":testing",
        "//:dagger",
        "//first_party/concurrency:scope",
        "//first_party/coroutines/testing",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
