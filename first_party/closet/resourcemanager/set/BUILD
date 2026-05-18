load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/closet:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "set",
    srcs = [
        "ResourceSet.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "//first_party/closet/observable",
        "//first_party/closet/resourcemanager/map",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = [
        "ResourceSetImpl.kt",
    ],
    deps = [
        ":set",
        "//first_party/closet:scope",
        "//first_party/closet/observable",
        "//first_party/closet/observable/helpers",
        "//first_party/closet/observable/standard:component",
        "//first_party/coroutines/cpu",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = [
        "ResourceSetImplModule.kt",
    ],
    deps = [
        ":impl",
        ":set",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["ResourceSetComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":set",
        "//first_party/closet/observable/standard:component",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ResourceSetComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":module",
        ":set",
        "//first_party/closet:scope",
        "//first_party/closet/observable/standard:component",
        "//first_party/coroutines:component",
    ],
)

kt_jvm_library(
    name = "factory_test",
    testonly = True,
    srcs = ["ResourceSetFactoryTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":set",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_jvm_test_with_dagger(
    name = "factory_impl_test",
    size = "small",
    srcs = ["ResourceSetFactoryImplTest.kt"],
    test_class = "com.jackbradshaw.closet.resourcemanager.set.ResourceSetFactoryImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":factory_test",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/coroutines:component_impl",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_as_observable_closable_test",
    size = "small",
    srcs = ["ResourceSetImplAsObservableClosableTest.kt"],
    test_class = "com.jackbradshaw.closet.resourcemanager.set.ResourceSetImplAsObservableClosableTest",
    deps = [
        ":component",
        ":component_impl",
        "//first_party/closet/observable:test",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["ResourceSetTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":set",
        "//first_party/coroutines:component",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    size = "small",
    srcs = ["ResourceSetImplTest.kt"],
    test_class = "com.jackbradshaw.closet.resourcemanager.set.ResourceSetImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":test",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/closet/observable",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
    ],
)
