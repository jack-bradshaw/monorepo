load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/closet:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "resource_manager",
    srcs = [
        "ResourceManager.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "//first_party/closet/observable",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = [
        "ResourceManagerImpl.kt",
    ],
    deps = [
        ":resource_manager",
        "//first_party/closet/observable",
        "//first_party/coroutines/cpu",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = [
        "ResourceManagerImplModule.kt",
    ],
    deps = [
        ":impl",
        ":resource_manager",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["ResourceManagerComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":resource_manager",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ResourceManagerComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":module",
        ":resource_manager",
        "//first_party/closet:scope",
        "//first_party/coroutines:component",
    ],
)

kt_jvm_library(
    name = "factory_test",
    testonly = True,
    srcs = ["ResourceManagerFactoryTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":resource_manager",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_jvm_test_with_dagger(
    name = "factory_impl_test",
    size = "small",
    srcs = ["ResourceManagerFactoryImplTest.kt"],
    test_class = "com.jackbradshaw.closet.resourcemanager.ResourceManagerFactoryImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":factory_test",
        "//first_party/coroutines:component_impl",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["ResourceManagerTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":resource_manager",
        "//first_party/coroutines:component",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    size = "small",
    srcs = ["ResourceManagerImplTest.kt"],
    test_class = "com.jackbradshaw.closet.resourcemanager.ResourceManagerImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":test",
        "//first_party/coroutines/testing/realistic:component_impl",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_observable_closable_test",
    size = "small",
    srcs = ["ResourceManagerImplObservableClosableTest.kt"],
    test_class = "com.jackbradshaw.closet.resourcemanager.ResourceManagerImplObservableClosableTest",
    deps = [
        ":component",
        ":component_impl",
        ":test",
        "//first_party/closet/observable:test",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
    ],
)
