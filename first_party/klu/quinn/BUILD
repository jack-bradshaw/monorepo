load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "quinn",
    srcs = ["Quinn.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/closet/observable",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["QuinnComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":quinn",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["QuinnComponentImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":component",
        ":impl",
        ":quinn",
        "//first_party/klu:scope",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["QuinnImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":quinn",
        "//first_party/closet/observable",
        "//first_party/klu:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "test_lib",
    srcs = ["QuinnTest.kt"],
    testonly = True,
    deps = [
        ":quinn",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/coroutines:component",
        "//first_party/coroutines/testing/realistic:component",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["QuinnImplTest.kt"],
    test_class = "com.jackbradshaw.quinn.QuinnImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":impl",
        ":test_lib",
        "@com_jackbradshaw_maven//:junit_junit",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_observable_closable_test",
    srcs = ["QuinnImplObservableClosableTest.kt"],
    test_class = "com.jackbradshaw.quinn.QuinnImplObservableClosableTest",
    deps = [
        ":component",
        ":component_impl",
        ":impl",
        "@com_jackbradshaw_maven//:junit_junit",
        "//first_party/closet/observable:test",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
    ],
)
