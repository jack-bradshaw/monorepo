load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/concurrency/quinn:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "idleable",
    srcs = ["IdleableQuinn.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/idleable",
        "//first_party/concurrency/quinn",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["IdleableQuinnImpl.kt"],
    visibility = [
        "//first_party/concurrency/quinn/testing/hub:__pkg__",
    ],
    deps = [
        ":idleable",
        "//first_party/concurrency/quinn",
        "//first_party/concurrency/quinn/testing/prod",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["IdleableQuinnTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":idleable",
        "//first_party/chronosphere/testingtaskbarrier",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["IdleableQuinnImplTest.kt"],
    test_class = "com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinnImplTest",
    deps = [
        ":idleable",
        ":impl",
        ":test",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/concurrency/quinn/testing/prod:component",
        "//first_party/concurrency/quinn/testing/prod:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "as_quinn_test",
    srcs = ["IdleableQuinnAsQuinnTest.kt"],
    test_class = "com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinnAsQuinnTest",
    deps = [
        ":idleable",
        ":impl",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/chronosphere/testingtaskbarrier:component",
        "//first_party/chronosphere/testingtaskbarrier:component_impl",
        "//first_party/concurrency/quinn:test",
        "//first_party/concurrency/quinn/testing/prod:component",
        "//first_party/concurrency/quinn/testing/prod:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
