load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/coroutines:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "cpu",
    srcs = ["Cpu.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["CpuModule.kt"],
    visibility = ["//first_party/coroutines:__subpackages__"],
    deps = [
        ":cpu",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
