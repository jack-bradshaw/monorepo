load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "chassis",
    testonly = True,
    srcs = ["ApplicationChassis.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/kale/model:result",
        "//first_party/kale/model:source",
        "//first_party/oksp/application",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    testonly = True,
    srcs = ["ApplicationChassisImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":chassis",
        "//first_party/concurrency/quinn:component",
        "//first_party/coroutines:component",
        "//first_party/kale/model:result",
        "//first_party/kale/provider:component",
        "//first_party/kale/provider:runner",
        "//first_party/oksp/application/passed",
        "//first_party/oksp/host:host_impl",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    testonly = True,
    srcs = ["ApplicationChassisModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":chassis",
        ":impl",
    ],
)

kt_jvm_library_with_dagger(
    name = "component",
    testonly = True,
    srcs = ["ApplicationChassisComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":chassis",
        ":module",
        "//first_party/concurrency/quinn:component",
        "//first_party/coroutines:component",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["ApplicationChassisTest.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":chassis",
        "//first_party/coroutines:component",
        "//first_party/coroutines/testing/realistic:component",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["ApplicationChassisImplTest.kt"],
    test_class = "com.jackbradshaw.oksp.testing.application.chassis.ApplicationChassisImplTest",
    deps = [
        ":component",
        ":test",
        "//first_party/concurrency/quinn:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/kale/provider:component_impl",
    ],
)
