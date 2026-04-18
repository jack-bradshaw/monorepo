load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/kale:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "chassis",
    srcs = ["ResolverChassis.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/closet/observable",
        "//first_party/kale/model:source",
        "//first_party/kale/model:versions",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = [
        "ResolverChassisImpl.kt",
    ],
    deps = [
        ":chassis",
        "//first_party/closet/resourcemanager:resource_manager",
        "//first_party/coroutines/io",
        "//first_party/kale/model:source",
        "//first_party/kale/model:versions",
        "//first_party/kale/provider:runner",
        "//first_party/quinn/core:quinn",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = [
        "ResolverChassisModule.kt",
    ],
    deps = [
        ":chassis",
        ":impl",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["ResolverChassisComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":chassis",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ResolverChassisComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":module",
        "//first_party/closet/resourcemanager:component",
        "//first_party/closet/resourcemanager:component_impl",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
        "//first_party/kale:scope",
        "//first_party/kale/provider:component",
        "//first_party/kale/provider:component_impl",
        "//first_party/quinn/core:component",
        "//first_party/quinn/core:component_impl",
    ],
)

kt_jvm_library(
    name = "chassis_test",
    testonly = True,
    srcs = ["ResolverChassisTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":chassis",
        "//first_party/kale/model:source",
        "//first_party/kale/model:versions",
        "//first_party/kale/model/testing:sources",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test(
    name = "chassis_impl_test",
    size = "small",
    srcs = ["ResolverChassisImplTest.kt"],
    test_class = "com.jackbradshaw.kale.resolver.chassis.ResolverChassisImplTest",
    deps = [
        ":chassis",
        ":chassis_test",
        ":component",
        ":component_impl",
        "//first_party/closet/resourcemanager:component_impl",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/quinn/core:component_impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "chassis_impl_observable_closable_test",
    size = "small",
    srcs = ["ResolverChassisImplObservableClosableTest.kt"],
    test_class = "com.jackbradshaw.kale.resolver.chassis.ResolverChassisImplObservableClosableTest",
    deps = [
        ":chassis",
        ":component",
        ":component_impl",
        "//first_party/closet/observable:test",
        "//first_party/closet/resourcemanager:component_impl",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/quinn/core:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_library(
    name = "harness_test",
    testonly = True,
    srcs = ["ResolverHarnessTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":chassis",
        "//first_party/kale/model:source",
        "//first_party/kale/model/testing:sources",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test(
    name = "harness_impl_test",
    size = "small",
    srcs = ["ResolverHarnessImplTest.kt"],
    test_class = "com.jackbradshaw.kale.resolver.chassis.ResolverHarnessImplTest",
    deps = [
        ":chassis",
        ":component",
        ":component_impl",
        ":harness_test",
        "//first_party/closet/resourcemanager:component_impl",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/kale/model:source",
        "//first_party/quinn/core:component_impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "harness_impl_obsevable_closable_test",
    size = "small",
    srcs = ["ResolverHarnessImplObservableClosableTest.kt"],
    test_class = "com.jackbradshaw.kale.resolver.chassis.ResolverHarnessImplObservableClosableTest",
    deps = [
        ":chassis",
        ":component",
        ":component_impl",
        "//first_party/closet/observable:test",
        "//first_party/closet/resourcemanager:component_impl",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/kale/model:source",
        "//first_party/kale/model/testing:sources",
        "//first_party/quinn/core:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
