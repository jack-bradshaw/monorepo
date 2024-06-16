load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "PassThroughUniConverterTest",
    srcs =
        [
            "PassThroughUniConverterTest.kt",
        ],
    deps = [
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/startstop",
        "//java/io/jackbradshaw/sustainment/startstop:simplex",
        "//java/io/jackbradshaw/sustainment/uniconverter",
        "//java/io/jackbradshaw/sustainment/uniconverter/startstop",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
    ],
)
