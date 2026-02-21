load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

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
        "//first_party/backstab/core/ports/errorsink",
        "//first_party/backstab/core/ports/modulesink",
        "//first_party/backstab/core/ports/targetsource",
        "//first_party/coroutines/io",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module_impl",
    srcs = ["MainModule.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":impl",
        ":main",
        "//first_party/coroutines/io",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = [
        "MainImplTest.kt",
        "MainTest.kt",
    ],
    deps = [
        ":impl",
        ":main",
        "//first_party/backstab/core/generator:impl",
        "//first_party/backstab/core/model:module",
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/core/model:type",
        "//first_party/backstab/core/ports/errorsink",
        "//first_party/backstab/core/ports/modulesink",
        "//first_party/backstab/core/ports/targetsource",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test(
    name = "impl_test",
    test_class = "com.jackbradshaw.backstab.core.main.MainImplTest",
    deps = [":test"],
)
