load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/sealant:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "scope",
    srcs = ["SealantScope.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["SealantComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/sealant/hub",
        "//first_party/sealant/source",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["SealantComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":scope",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/closet/resourcemanager/set:component",
        "//first_party/closet/resourcemanager/set:component_impl",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
        "//first_party/sealant/hub:module",
        "//first_party/sealant/source:module",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["SealantComponentTest.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/sealant:component",
        "//first_party/sealant/hub",
        "//first_party/sealant/source",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "sealant_component_impl_test",
    size = "small",
    srcs = ["SealantComponentImplTest.kt"],
    test_class = "com.jackbradshaw.sealant.SealantComponentImplTest",
    deps = [
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
        "//first_party/sealant/source:module",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
