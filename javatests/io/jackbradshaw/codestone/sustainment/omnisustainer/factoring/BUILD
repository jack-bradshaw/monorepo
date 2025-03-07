load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "PlatformsSimplexEmptyClasspathTest",
    testonly = True,
    srcs = [
        "PlatformsSimplexEmptyClasspathTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer/factoring:platforms",
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer/factoring:platforms_simplex",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "PlatformsSimplexNonEmptyClasspathTest",
    testonly = True,
    srcs = [
        "PlatformsSimplexNonEmptyClasspathTest.kt",
    ],
    runtime_deps = [
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer/factoring:ktcoroutine",
    ],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer/factoring:ktcoroutine",
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer/factoring:platforms",
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer/factoring:platforms_simplex",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
    ],
)
