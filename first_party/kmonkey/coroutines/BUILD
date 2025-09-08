load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_library(
    name = "coroutines",
    srcs = [
        "Dispatchers.kt",
    ],
    visibility = [
        "//first_party:__subpackages__",
    ],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_bullet",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)

kt_jvm_test(
    name = "test",
    srcs = [
        "DispatchersTest.kt",
    ],
    test_class = "com.jackbradshaw.kmonkey.coroutines.DispatchersTest",
    deps = [
        "//first_party/kmonkey/coroutines",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
