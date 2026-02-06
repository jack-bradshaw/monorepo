load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:defs.bzl", "public")

PUBLICITY = public()

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["TestIoModule.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/coroutines:scope",
        "//first_party/coroutines/io",
        "//first_party/coroutines/testing/scope:module",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
