load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "OmniConverterSimplexTest",
    srcs = [
        "OmniConverterSimplexTest.kt",
    ],
    runtime_deps = [
        "//java/io/jackbradshaw/sustainment/uniconverter/listenablefuture",
    ],
    deps = [
        "//java/io/jackbradshaw/sustainment/omniconverter",
        "//java/io/jackbradshaw/sustainment/omniconverter:simplex",
        "//java/io/jackbradshaw/sustainment/omnisustainer/factoring:listenablefuture",
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/primitives",
        "//java/io/jackbradshaw/sustainment/startstop",
        "//java/io/jackbradshaw/sustainment/startstop:simplex",
        "//java/io/jackbradshaw/sustainment/uniconverter",
        "//java/io/jackbradshaw/sustainment/uniconverter/listenablefuture",
        "//java/io/jackbradshaw/queen/ui/primitives",
        "@maven//:com_google_guava_guava",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)
