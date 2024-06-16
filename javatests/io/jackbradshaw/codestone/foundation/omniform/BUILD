load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "CoordinatorImplTest",
    srcs = [
        "CoordinatorImplTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "//java/io/jackbradshaw/codestone/sustainment/startstop:simplex",
        "//java/io/jackbradshaw/codestone/foundation/omniform",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
                "//java/io/jackbradshaw/codestone/sustainment/omnisustainer/factoring:omnisustainers",
        "//java/io/jackbradshaw/codestone/sustainment/operations:ktcoroutine",
        "//java/io/jackbradshaw/codestone/sustainment/operations:startstop",
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer/factoring:ktcoroutine",
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer/factoring:listenablefuture",
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer/factoring:startstop",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
