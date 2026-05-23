load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/concurrency/quinn:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library_with_dagger(
    name = "idleable",
    srcs = [
        "TestingQuinnComponent.kt",
        "factory/IdleableQuinnHubImpl.kt",
        "factory/IdleableQuinnHubModule.kt",
        "taskbarrier/TaskBarrierModule.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/idleable",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/chronosphere/testingtaskbarrier:component",
        "//first_party/chronosphere/testingtaskbarrier:component_impl",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/closet/resourcemanager/set",
        "//first_party/closet/resourcemanager/set:component",
        "//first_party/closet/resourcemanager/set:component_impl",
        "//first_party/concurrency/quinn",
        "//first_party/concurrency/quinn:component",
        "//first_party/concurrency/quinn:production_module",
        "//first_party/concurrency/quinn:qualifiers",
        "//first_party/concurrency/quinn/testing/idleable",
        "//first_party/concurrency/quinn/testing/idleable:impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
