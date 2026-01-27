load("//first_party/dagger:defs.bzl", 'kt_jvm_library_with_dagger', 'kt_jvm_test_with_dagger')
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "launcher",
    srcs = ["Launcher.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["LauncherImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":launcher",
        "//first_party/coroutines:scope",
        "//first_party/coroutines/testing/dispatchers",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["LauncherModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":impl",
        ":launcher",
        "//first_party/coroutines/testing/dispatchers:module",
    ],
)

kt_jvm_library_with_dagger(
    name = "test",
    srcs = ["LauncherTest.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":launcher",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    timeout = "short",
    srcs = ["LauncherImplTest.kt"],
    test_class = "com.jackbradshaw.coroutines.testing.launcher.LauncherImplTest",
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":impl",
        ":launcher",
        ":module",
        ":test",
        "//first_party/coroutines:scope",
        "//first_party/coroutines/testing/scope:module",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
