load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/concurrency:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "testing",
    srcs = ["TestPulsar.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/concurrency/pulsar",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["TestPulsarImpl.kt"],
    deps = [
        ":testing",
        "//first_party/concurrency/pulsar:scope",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["TestPulsarModule.kt"],
    visibility = ["//first_party/concurrency/testing:__pkg__"],
    deps = [
        ":impl",
        ":testing",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["TestPulsarTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":testing",
        "//first_party/coroutines/testing/realistic:component",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    timeout = "short",
    srcs = ["TestPulsarImplTest.kt"],
    test_class = "com.jackbradshaw.concurrency.pulsar.testing.TestPulsarImplTest",
    deps = [
        ":module",
        ":test",
        "//first_party/coroutines/testing/realistic:component_impl",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["TestPulsarComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/concurrency/pulsar:component",
        "//first_party/concurrency/pulsar/testing",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["TestPulsarComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        "//first_party/concurrency/pulsar:scope",
        "//first_party/concurrency/pulsar/testing:module",
    ],
)
