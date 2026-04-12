load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/coroutines:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "dispatcher",
    srcs = ["AdvancableDispatcher.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/advancable",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test_jvm",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["AdvancableDispatcherImpl.kt"],
    deps = [
        ":dispatcher",
        "//first_party/coroutines:scope",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["AdvancableDispatcherModule.kt"],
    visibility = ["//first_party/coroutines/testing/artificial:__subpackages__"],
    deps = [
        ":dispatcher",
        ":impl",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["AdvancableDispatcherTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":dispatcher",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["AdvancableDispatcherImplTest.kt"],
    test_class = "com.jackbradshaw.coroutines.testing.artificial.dispatcher.AdvancableDispatcherImplTest",
    deps = [
        ":module",
        ":test",
    ],
)
