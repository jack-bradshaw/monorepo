load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "dispatcher",
    srcs = [
        "AdvancableDispatcher.kt",
        "AdvancableDispatcherImpl.kt",
        "AdvancableDispatcherModule.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/advancable",
        "//first_party/coroutines:scope",
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test_jvm",
    ],
)

kt_jvm_test_with_dagger(
    name = "AdvancableDispatcherImplTest",
    test_class = "com.jackbradshaw.coroutines.testing.artificial.dispatcher.AdvancableDispatcherImplTest",
    srcs = [
        "AdvancableDispatcherTest.kt",
        "AdvancableDispatcherImplTest.kt",
    ],
    deps = [
        ":dispatcher",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test_jvm",
    ],
)
