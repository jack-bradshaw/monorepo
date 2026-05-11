load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "source",
    srcs = ["SealedSource.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/sealant/hub",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["SealedSourceImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":source",
        "//first_party/sealant/flow",
        "//first_party/sealant/hub",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "SealedSourceImplAsHubTest",
    srcs = ["SealedSourceImplAsHubTest.kt"],
    test_class = "com.jackbradshaw.sealant.source.SealedSourceImplAsHubTest",
    deps = [
        ":impl",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/closet/resourcemanager:module",
        "//first_party/coroutines/io",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/sealant:scope",
        "//first_party/sealant/hub:module",
        "//first_party/sealant/hub:test_abstract",
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
