load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_test_with_dagger")
load("//first_party/sealant:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "source",
    srcs = ["SealedSource.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/sealant/hub",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["SealedSourceImpl.kt"],
    deps = [
        ":source",
        "//first_party/closet/observable/helpers",
        "//first_party/sealant:scope",
        "//first_party/sealant/hub",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["SealedSourceModule.kt"],
    visibility = PUBLICITY,
    deps = [
        ":impl",
        ":source",
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
    ],
)

kt_jvm_test_with_dagger(
    name = "sealed_source_impl_as_hub_test",
    size = "small",
    srcs = ["SealedSourceImplAsHubTest.kt"],
    test_class = "com.jackbradshaw.sealant.source.SealedSourceImplAsHubTest",
    deps = [
        ":impl",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/closet/resourcemanager/set:component",
        "//first_party/closet/resourcemanager/set:component_impl",
        "//first_party/coroutines/io",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/sealant:scope",
        "//first_party/sealant/hub:module",
        "//first_party/sealant/hub:test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["SealedSourceTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":source",
        "//first_party/chronosphere/testingtaskbarrier",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "sealed_source_impl_test",
    size = "small",
    srcs = ["SealedSourceImplTest.kt"],
    test_class = "com.jackbradshaw.sealant.source.SealedSourceImplTest",
    deps = [
        ":impl",
        ":test",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/closet/resourcemanager/set:component",
        "//first_party/closet/resourcemanager/set:component_impl",
        "//first_party/coroutines/io",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/sealant:scope",
        "//first_party/sealant/hub:module",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
