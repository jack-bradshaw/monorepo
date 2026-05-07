load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "scope",
    srcs = ["SealantScope.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["SealantComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/sealant/hub",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["SealantComponentImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":component",
        ":scope",
        "//first_party/closet/resourcemanager:module",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
        "//first_party/sealant/hub:module",
    ],
)

kt_jvm_library(
    name = "test_abstract",
    srcs = ["SealantComponentTest.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/sealant:component",
        "//first_party/sealant/hub",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "SealantComponentImplTest",
    srcs = ["SealantComponentImplTest.kt"],
    test_class = "com.jackbradshaw.sealant.SealantComponentImplTest",
    deps = [
        ":test_abstract",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/closet/resourcemanager:module",
        "//first_party/coroutines/io",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/sealant:scope",
        "//first_party/sealant/hub",
        "//first_party/sealant/hub:impl",
        "//first_party/sealant/hub:module",
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
