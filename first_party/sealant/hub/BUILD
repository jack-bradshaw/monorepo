load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/sealant:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "hub",
    srcs = ["SealedHub.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/closet/observable",
        "//first_party/sealant/session",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["SealedHubImpl.kt"],
    visibility = ["//first_party/sealant:__subpackages__"],
    deps = [
        ":hub",
        "//first_party/closet/observable/helpers",
        "//first_party/closet/observable/standard",
        "//first_party/closet/resourcemanager/set",
        "//first_party/coroutines/io",
        "//first_party/sealant:scope",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["SealedHubModule.kt"],
    visibility = ["//first_party/sealant:__subpackages__"],
    deps = [
        ":hub",
        ":impl",
        "//first_party/sealant/session:module",
    ],
)

kt_jvm_library(
    name = "test_factory",
    testonly = True,
    srcs = ["SealedHubFactoryTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":hub",
        "//first_party/chronosphere/testingtaskbarrier",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["SealedHubTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":hub",
        "//first_party/chronosphere/testingtaskbarrier",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "sealed_hub_impl_test",
    size = "small",
    srcs = ["SealedHubImplTest.kt"],
    test_class = "com.jackbradshaw.sealant.hub.SealedHubImplTest",
    deps = [
        ":module",
        ":test",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/closet/resourcemanager/set:component",
        "//first_party/closet/resourcemanager/set:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/sealant:scope",
    ],
)

kt_jvm_test_with_dagger(
    name = "sealed_hub_impl_factory_test",
    size = "small",
    srcs = ["SealedHubImplFactoryTest.kt"],
    test_class = "com.jackbradshaw.sealant.hub.SealedHubImplFactoryTest",
    deps = [
        ":module",
        ":test_factory",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/closet/resourcemanager/set:component",
        "//first_party/closet/resourcemanager/set:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/sealant:scope",
    ],
)

kt_jvm_test_with_dagger(
    name = "sealed_session_from_hub_test",
    size = "small",
    srcs = ["SealedSessionFromHubTest.kt"],
    test_class = "com.jackbradshaw.sealant.hub.SealedSessionFromHubTest",
    deps = [
        ":module",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/closet/resourcemanager/set:component",
        "//first_party/closet/resourcemanager/set:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/sealant:scope",
        "//first_party/sealant/session:test",
    ],
)
