load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "KtCoroutineOperationTest",
    srcs = [
        "KtCoroutineOperationTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/operations:ktcoroutine",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "ListenableFutureOperationTest",
    srcs = [
        "ListenableFutureOperationTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/operations:listenablefuture",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "StartStopOperationTest",
    srcs = [
        "StartStopOperationTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/operations:startstop",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
    ],
)
