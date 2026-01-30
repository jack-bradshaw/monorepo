load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "io",
    srcs = ["Io.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["IoModule.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":io",
        "//first_party/coroutines:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
