load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "BackwardsUniConverterTest",
    srcs = [
        "BackwardsUniConverterTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/sustainment/operations:coroutine",
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/startstop",
        "//java/io/jackbradshaw/sustainment/uniconverter",
        "//java/io/jackbradshaw/sustainment/uniconverter/coroutine:backwards_converter",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "ForwardsUniConverterTest",
    srcs = [
        "ForwardsUniConverterTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/sustainment/operations:coroutine",
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/startstop",
        "//java/io/jackbradshaw/sustainment/startstop:simplex",
        "//java/io/jackbradshaw/sustainment/uniconverter",
        "//java/io/jackbradshaw/sustainment/uniconverter/coroutine:forwards_converter",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "PassThroughUniConverterTest",
    srcs = [
        "PassThroughUniConverterTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/sustainment/operations:coroutine",
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/startstop",
        "//java/io/jackbradshaw/sustainment/startstop:simplex",
        "//java/io/jackbradshaw/sustainment/uniconverter",
        "//java/io/jackbradshaw/sustainment/uniconverter/coroutine:pass_through_converter",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
