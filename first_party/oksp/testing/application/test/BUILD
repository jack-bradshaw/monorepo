load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "test",
    srcs = ["ApplicationTestRuleTest.kt"],
    data = [
        "//first_party/oksp/application",
        "//first_party/oksp/entrypoint",
    ],
    test_class = "com.jackbradshaw.oksp.testing.application.test.ApplicationTestRuleTest",
    deps = [
        "//first_party/oksp/application",
        "//first_party/oksp/testing/application:factory",
        "@bazel_tools//tools/java/runfiles",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
