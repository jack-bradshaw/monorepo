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

kt_jvm_library_with_dagger(
    name = "impl_module",
    srcs = ["ApplicationLoaderImplModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":impl",
        ":loader",
    ],
)

kt_jvm_library(
    name = "test_lib",
    testonly = True,
    srcs = ["ApplicationLoaderTest.kt"],
    deps = [
        ":loader",
        "//first_party/oksp/application",
        "//first_party/oksp/application:component",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["ApplicationLoaderImplTest.kt"],
    test_class = "com.jackbradshaw.oksp.application.loader.ApplicationLoaderImplTest",
    deps = [
        ":impl",
        ":loader",
        ":test_lib",
        "//first_party/oksp/application",
        "//first_party/oksp/application:component",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
