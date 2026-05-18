load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/concurrency/quinn:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "scope",
    srcs = ["QuinnScope.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library(
    name = "specific",
    srcs = [
        "QuinnSpecific.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

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
        ":scope",
        "//first_party/closet/observable/standard",
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
        ":module",
        ":scope",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["QuinnModule.kt"],
    visibility = [
        "//first_party/concurrency/quinn:__subpackages__",
        "//first_party/concurrency/quinn/testing:__subpackages__",
    ],
    deps = [
        ":impl",
        ":quinn",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["QuinnTest.kt"],
    visibility = ["//first_party/concurrency/quinn/testing:__subpackages__"],
    deps = [
        ":quinn",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/closet/observable/helpers",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["QuinnImplTest.kt"],
    test_class = "com.jackbradshaw.concurrency.quinn.QuinnImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":impl",
        ":test",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_as_observable_closable_test",
    srcs = ["QuinnImplAsObservableClosableTest.kt"],
    test_class = "com.jackbradshaw.concurrency.quinn.QuinnImplAsObservableClosableTest",
    deps = [
        ":component",
        ":component_impl",
        ":impl",
        "//first_party/closet/observable:test",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_as_suspendable_closable_test",
    srcs = ["QuinnImplAsSuspendableClosableTest.kt"],
    test_class = "com.jackbradshaw.concurrency.quinn.QuinnImplAsSuspendableClosableTest",
    deps = [
        ":component",
        ":component_impl",
        ":impl",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/closet/suspending:test",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
