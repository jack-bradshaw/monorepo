load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "main",
    srcs = ["Main.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["MainImpl.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":main",
        "//first_party/backstab/core:scope",
        "//first_party/backstab/core/generator",
        "//first_party/backstab/core/repository",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl_module",
    srcs = ["MainImplModule.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":impl",
        ":main",
        "//first_party/coroutines/io",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = [
        "MainImplTest.kt",
        "MainTest.kt",
    ],
    test_class = "com.jackbradshaw.backstab.core.main.MainImplTest",
    deps = [
        ":impl",
        ":main",
        "//first_party/backstab/core:scope",
        "//first_party/backstab/core/generator:impl",
        "//first_party/backstab/core/model:module",
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/core/model:type",
        "//first_party/backstab/core/repository",
        "//first_party/coroutines/testing",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
