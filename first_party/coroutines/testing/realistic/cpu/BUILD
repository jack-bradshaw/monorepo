load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "cpu_module",
    srcs = ["TestingCpuModule.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/idleable",
        "//first_party/coroutines:scope",
        "//first_party/coroutines/cpu:module",
        "//first_party/coroutines/testing/realistic/dispatcher",
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
