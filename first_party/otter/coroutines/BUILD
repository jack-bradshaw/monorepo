load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:defs.bzl", "quarantined")

PUBLICITY = quarantined("//first_party/otter/coroutines")

kt_jvm_library(
    name = "coroutines",
    srcs = ["Dispatchers.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/otter/engine/core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["CoroutinesModule.kt"],
    visibility = PUBLICITY,
    deps = [
        ":coroutines",
        "//first_party/otter/engine/core",
        "//first_party/otter/qualifiers",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
