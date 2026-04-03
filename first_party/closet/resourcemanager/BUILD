load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "resource_manager",
    srcs = [
        "ResourceManager.kt",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/closet/obsevable:observable",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["ResourceManagerComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":resource_manager",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ResourceManagerComponentImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":component",
        ":module",
        "//first_party/closet:scope",
        "//first_party/coroutines:component",
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = [
        "ResourceManagerImpl.kt",
    ],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":resource_manager",
        "//first_party/coroutines:component",
        "//first_party/coroutines/cpu:module",
        "//first_party/coroutines/io:module",
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = [
        "ResourceManagerImplModule.kt",
    ],
    visibility = ["//visibility:public"],
    deps = [
        ":impl",
        ":resource_manager",
        "//:dagger",
    ],
)

kt_jvm_library(
    name = "test_abstract_resource_manager",
    srcs = [
        "ResourceManagerFactoryTest.kt",
        "ResourceManagerTest.kt",
    ],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":resource_manager",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "test_resource_manager",
    size = "small",
    srcs = [
        "ResourceManagerFactoryImplTest.kt",
        "ResourceManagerImplObservableClosableTest.kt",
        "ResourceManagerImplTest.kt",
        "TestResourceObservableClosableTest.kt",
    ],
    test_class = "com.jackbradshaw.closet.resourcemanager.ResourceManagerImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":resource_manager",
        ":test_abstract_resource_manager",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
