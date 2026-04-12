load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "dispatcher",
    srcs = ["IdleableDispatcher.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/idleable",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["IdleableDispatcherImpl.kt"],
    visibility = ["//first_party/coroutines/testing/realistic:__subpackages__"],
    deps = [
        ":dispatcher",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["IdleableDispatcherModule.kt"],
    visibility = ["//first_party/coroutines/testing/realistic:__subpackages__"],
    deps = [
        ":dispatcher",
        ":impl",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["IdleableDispatcherTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":dispatcher",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["IdleableDispatcherImplTest.kt"],
    test_class = "com.jackbradshaw.coroutines.testing.realistic.dispatcher.IdleableDispatcherImplTest",
    deps = [
        ":module",
        ":test",
        "//first_party/coroutines:scope",
    ],
)
