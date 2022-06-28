load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_test(
    name = "OnceTest",
    srcs = [
        "OnceTest.kt",
    ],
    jvm_flags = ["-Djava.security.manager=allow"],  # TODO(https://github.com/bazelbuild/bazel/issues/14502): Remove
    deps = [
        "//java/io/matthewbradshaw/klu/concurrency",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
