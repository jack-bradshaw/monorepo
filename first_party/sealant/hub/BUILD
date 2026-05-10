load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "hub",
    srcs = ["SealedHub.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/closet/observable",
        "//first_party/sealant/flow",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["SealedHubImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":hub",
        "//first_party/closet/resourcemanager:resource_manager",
        "//first_party/coroutines/io",
        "//first_party/sealant:scope",
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["SealedHubModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":hub",
        ":impl",
    ],
)

kt_jvm_library(
    name = "test_abstract",
    srcs = [
        "SealedHubFactoryTest.kt",
        "SealedHubTest.kt",
    ],
    visibility = ["//visibility:public"],
    deps = [
        ":hub",
        "//first_party/chronosphere/testingtaskbarrier",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "SealedHubImplTest",
    srcs = ["SealedHubImplTest.kt"],
    test_class = "com.jackbradshaw.sealant.hub.SealedHubImplTest",
    deps = [
        ":impl",
        ":module",
        ":test_abstract",
        "//first_party/closet/resourcemanager:module",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/sealant:scope",
    ],
)

kt_jvm_test_with_dagger(
    name = "SealedHubImplFactoryTest",
    srcs = ["SealedHubImplFactoryTest.kt"],
    test_class = "com.jackbradshaw.sealant.hub.SealedHubImplFactoryTest",
    deps = [
        ":impl",
        ":module",
        ":test_abstract",
        "//first_party/closet/resourcemanager:module",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/sealant:scope",
    ],
)


kt_jvm_test_with_dagger(
    name = "SealedFlowFromHubTest",
    srcs = ["SealedFlowFromHubTest.kt"],
    test_class = "com.jackbradshaw.sealant.hub.SealedFlowFromHubTest",
    deps = [
        ":impl",
        ":module",
        "//first_party/sealant/flow:test_abstract",
        "//first_party/closet/resourcemanager:module",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/sealant:scope",
    ],
)
