load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/coroutines:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "component",
    srcs = ["RealisticCoroutinesTestingComponent.kt"],
    visibility = PUBLICITY,
    exports = [
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/coroutines:component",
    ],
    deps = [
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/coroutines:component",
        "//first_party/coroutines/testing:coroutines",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["RealisticCoroutinesTestingComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/chronosphere/testingtaskbarrier:component",
        "//first_party/chronosphere/testingtaskbarrier:component_impl",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
        "//first_party/coroutines:scope",
        "//first_party/coroutines/testing/realistic/cpu:module",
        "//first_party/coroutines/testing/realistic/dispatcher",
        "//first_party/coroutines/testing/realistic/dispatcher:module",
        "//first_party/coroutines/testing/realistic/io:module",
        "//first_party/coroutines/testing/realistic/testingtaskbarrier:module",
    ],
)
