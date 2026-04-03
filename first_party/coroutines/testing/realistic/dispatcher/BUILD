load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "dispatcher",
    srcs = [
        "IdleableDispatcher.kt",
        "IdleableDispatcherImpl.kt",
        "IdleableDispatcherModule.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/idleable",
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "IdleableDispatcherImplTest",
    test_class = "com.jackbradshaw.coroutines.testing.realistic.dispatcher.IdleableDispatcherImplTest",
    srcs = [
        "IdleableDispatcherTest.kt",
        "IdleableDispatcherImplTest.kt",
    ],
    deps = [
        ":dispatcher",
        "//first_party/coroutines:scope",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
