load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/closet:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "standard",
    srcs = ["StandardObservableClosableFactory.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/closet/observable",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["StandardObservableClosableFactoryImpl.kt"],
    deps = [
        ":standard",
        "//first_party/closet:scope",
        "//first_party/closet/observable",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["StandardObservableClosableComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":standard",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["StandardObservableClosableComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":impl",
        "//first_party/closet:scope",
    ],
)

kt_jvm_test_with_dagger(
    name = "factory_impl_factored_value_as_observable_closable_test",
    size = "small",
    srcs = ["StandardObservableClosableFactoryImplFactoredValueAsObservableClosableTest.kt"],
    test_class = "com.jackbradshaw.closet.observable.standard.StandardObservableClosableFactoryImplFactoredValueAsObservableClosableTest",
    deps = [
        ":component",
        ":component_impl",
        ":standard",
        "//first_party/closet/observable",
        "//first_party/closet/observable:test",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test_with_dagger(
    name = "factory_impl_factored_value_as_suspendable_closable_test",
    size = "small",
    srcs = ["StandardObservableClosableFactoryImplFactoredValueAsSuspendableClosableTest.kt"],
    test_class = "com.jackbradshaw.closet.observable.standard.StandardObservableClosableFactoryImplFactoredValueAsSuspendableClosableTest",
    deps = [
        ":component",
        ":component_impl",
        ":standard",
        "//first_party/closet/observable",
        "//first_party/closet/suspending:test",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_library(
    name = "standard_test",
    testonly = True,
    srcs = ["StandardObservableClosableFactoryTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":standard",
        "//first_party/chronosphere/testingtaskbarrier",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "standard_impl_test",
    size = "small",
    srcs = ["StandardObservableClosableFactoryImplTest.kt"],
    test_class = "com.jackbradshaw.closet.observable.standard.StandardObservableClosableFactoryImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":standard",
        ":standard_test",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
