load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/coroutines:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "module",
    srcs = [
        "Cpu.kt",
        "CpuModule.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
