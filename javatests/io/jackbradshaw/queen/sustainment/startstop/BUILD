load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "StartStopSimplexTest",
    srcs = [
        "StartStopSimplexTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/sustainment/startstop",
        "//java/io/jackbradshaw/sustainment/startstop:simplex",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
    ],
)
