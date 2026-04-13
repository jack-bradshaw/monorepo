load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "loader",
    srcs = ["ApplicationLoader.kt"],
    visibility = ["//visibility:public"],
    deps = ["//first_party/oksp/application"],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["ApplicationLoaderImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":loader",
        "//first_party/oksp/application",
    ],
)

kt_jvm_library(
    name = "test_lib",
    srcs = ["ApplicationLoaderTest.kt"],
    deps = [
        ":loader",
        "//first_party/oksp/application",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
    testonly = True,
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["ApplicationLoaderImplTest.kt"],
    test_class = "com.jackbradshaw.oksp.application.loader.ApplicationLoaderImplTest",
    deps = [
        ":test_lib",
        ":impl",
        ":loader",
        "//first_party/oksp/application:component",
        "//first_party/oksp/application",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
