load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/quinn:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "quinn",
    srcs = ["Quinn.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/closet/observable",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["QuinnImpl.kt"],
    deps = [
        ":quinn",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["QuinnComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":quinn",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["QuinnComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":impl",
        ":quinn",
        "//first_party/quinn:scope",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["QuinnTest.kt"],
    deps = [
        ":quinn",
        "//first_party/chronosphere/testingtaskbarrier",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["QuinnImplTest.kt"],
    test_class = "com.jackbradshaw.quinn.core.QuinnImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":impl",
        ":test",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_observable_closable_test",
    srcs = ["QuinnImplObservableClosableTest.kt"],
    test_class = "com.jackbradshaw.quinn.core.QuinnImplObservableClosableTest",
    deps = [
        ":component",
        ":component_impl",
        ":impl",
        "//first_party/closet/observable:test",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
