load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "dispatchers",
    srcs = ["Dispatchers.kt"],
    visibility = ["//visibility:public"],
    deps = [
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["DispatchersModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":dispatchers",
        "//first_party/coroutines:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
