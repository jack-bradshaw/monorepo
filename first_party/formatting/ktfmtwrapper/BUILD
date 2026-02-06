load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")
load("//first_party/publicity:defs.bzl", "internal")

PUBLICITY = internal()

java_library(
    name = "lib",
    srcs = ["KtFmtWrapper.java"],
    deps = ["@ktfmt//jar"],
)

java_binary(
    name = "bin",
    main_class = "com.jackbradshaw.formatting.ktfmtwrapper.KtFmtWrapper",
    visibility = PUBLICITY,
    runtime_deps = [":lib"],
)

kt_jvm_test(
    name = "test",
    srcs = ["KtFmtWrapperTest.kt"],
    test_class = "com.jackbradshaw.formatting.ktfmtwrapper.KtFmtWrapperTest",
    deps = [
        ":lib",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
