load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "BackwardsUniConverterTest",
    srcs = [
        "BackwardsUniConverterTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/operations:listenablefuture",
        "//java/io/jackbradshaw/codestone/sustainment/operations:startstop",
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter/listenablefuture",
        "@maven//:com_google_guava_guava",
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
        "//java/io/jackbradshaw/codestone/sustainment/operations:listenablefuture",
        "//java/io/jackbradshaw/codestone/sustainment/operations:startstop",
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "//java/io/jackbradshaw/codestone/sustainment/startstop:simplex",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter/listenablefuture",
        "@maven//:com_google_guava_guava",
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
        "//java/io/jackbradshaw/codestone/sustainment/operations:listenablefuture",
        "//java/io/jackbradshaw/codestone/sustainment/operations:startstop",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter/listenablefuture",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
