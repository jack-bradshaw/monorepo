load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/concurrency/quinn:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library_with_dagger(
    name = "idleable",
    srcs = [
        "TestingQuinnComponent.kt",
        "factory/IdleableQuinnHubImpl.kt",
        "factory/IdleableQuinnHubModule.kt",
        "idleable/IdleableQuinn.kt",
        "idleable/IdleableQuinnImpl.kt",
        "taskbarrier/TaskBarrierModule.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/idleable",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/chronosphere/testingtaskbarrier:component",
        "//first_party/closet/resourcemanager:component_impl",
        "//first_party/closet/resourcemanager:resource_manager",
        "//first_party/concurrency/quinn",
        "//first_party/concurrency/quinn:component",
        "//first_party/concurrency/quinn:production_module",
        "//first_party/concurrency/quinn:qualifiers",
        "//first_party/coroutines/testing/realistic:component",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "test",
    srcs = [
        "idleable/IdleableQuinnImplTest.kt",
        "idleable/IdleableQuinnTest.kt",
    ],
    test_class = "com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinnImplTest",
    deps = [
        ":idleable",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "as_quinn_test",
    srcs = [
        "idleable/IdleableQuinnAsQuinnTest.kt",
    ],
    test_class = "com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinnAsQuinnTest",
    deps = [
        ":idleable",
        "//first_party/concurrency/quinn:test",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
