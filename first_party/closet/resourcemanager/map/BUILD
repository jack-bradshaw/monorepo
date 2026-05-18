load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/closet:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "map",
    srcs = [
        "ResourceMap.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "//first_party/closet/observable",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = [
        "ResourceMapImpl.kt",
    ],
    deps = [
        ":map",
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
        "ResourceMapImplModule.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        ":impl",
        ":map",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["ResourceMapComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":map",
        "//first_party/closet/observable/standard:component",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ResourceMapComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":map",
        ":module",
        "//first_party/closet:scope",
        "//first_party/closet/observable/standard:component",
        "//first_party/coroutines:component",
    ],
)

kt_jvm_library(
    name = "factory_test",
    testonly = True,
    srcs = ["ResourceMapFactoryTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":map",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_jvm_test_with_dagger(
    name = "factory_impl_test",
    size = "small",
    srcs = ["ResourceMapFactoryImplTest.kt"],
    test_class = "com.jackbradshaw.closet.resourcemanager.map.ResourceMapFactoryImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":factory_test",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/coroutines:component_impl",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["ResourceMapTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":map",
        "//first_party/coroutines:component",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    size = "small",
    srcs = ["ResourceMapImplTest.kt"],
    test_class = "com.jackbradshaw.closet.resourcemanager.map.ResourceMapImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":test",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/coroutines/testing/realistic:component_impl",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_as_observable_closable_test",
    size = "small",
    srcs = ["ResourceMapImplAsObservableClosableTest.kt"],
    test_class = "com.jackbradshaw.closet.resourcemanager.map.ResourceMapImplAsObservableClosableTest",
    deps = [
        ":component",
        ":component_impl",
        ":test",
        "//first_party/closet/observable:test",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
    ],
)
