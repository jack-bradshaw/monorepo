load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "OmniConverterSimplexTest",
    srcs = [
        "OmniConverterSimplexTest.kt",
    ],
    runtime_deps = [
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter/listenablefuture",
    ],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/omniconverter",
        "//java/io/jackbradshaw/codestone/sustainment/omniconverter:simplex",
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer/factoring:listenablefuture",
        "//java/io/jackbradshaw/codestone/sustainment/operations:startstop",
        "//java/io/jackbradshaw/codestone/sustainment/primitives",
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "//java/io/jackbradshaw/codestone/sustainment/startstop:simplex",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter/listenablefuture",
        "//java/io/jackbradshaw/codestone/ui/primitives",
        "@maven//:com_google_guava_guava",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)
