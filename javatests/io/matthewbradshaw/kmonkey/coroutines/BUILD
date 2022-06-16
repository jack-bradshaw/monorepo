load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "DispatcherTest",
    srcs = [
        "DispatcherTest.kt.kt",
    ],
    use_testrunner = False,
    deps = [
        "@maven//:org_junit_jupiter_junit_jupiter_engine",
        "//java/io/matthewbradshaw/kmonkey/coroutines:dispatcher",
    ],
)