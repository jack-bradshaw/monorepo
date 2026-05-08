load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/concurrency/quinn:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "component",
    srcs = ["TestQuinnComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/testingtaskbarrier:component",
        "//first_party/concurrency/quinn:component",
        "//first_party/concurrency/quinn:scope",
        "//first_party/concurrency/quinn:specific",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["TestQuinnComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        "//first_party/chronosphere/testingtaskbarrier:component",
        "//first_party/chronosphere/testingtaskbarrier:component_impl",
        "//first_party/concurrency/quinn:component",
        "//first_party/concurrency/quinn:scope",
        "//first_party/concurrency/quinn:specific",
        "//first_party/concurrency/quinn/testing/hub:module",
        "//first_party/concurrency/quinn/testing/prod:component",
        "//first_party/concurrency/quinn/testing/prod:component_impl",
        "//first_party/concurrency/quinn/testing/taskbarrier:module",
    ],
)
