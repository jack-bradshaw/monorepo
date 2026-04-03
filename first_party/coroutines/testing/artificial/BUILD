load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/coroutines:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "component",
    srcs = ["ArtificialCoroutinesTestingComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/testingtaskdriver",
        "//first_party/coroutines:component",
        "//first_party/coroutines/testing",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ArtificialCoroutinesTestingComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        "//first_party/chronosphere/testingtaskdriver",
        "//first_party/chronosphere/testingtaskdriver:component",
        "//first_party/chronosphere/testingtaskdriver:component_impl",
        "//first_party/chronosphere/testingtaskdriver:module",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
        "//first_party/coroutines:scope",
        "//first_party/coroutines/testing/artificial/cpu:cpu_module",
        "//first_party/coroutines/testing/artificial/dispatcher:dispatcher",
        "//first_party/coroutines/testing/artificial/io:io_module",
        "//first_party/coroutines/testing/artificial/testingtaskdriver:testingtaskdriver_module",
    ],
)
