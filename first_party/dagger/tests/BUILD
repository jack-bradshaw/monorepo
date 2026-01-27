load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_library_with_dagger(
    name = "test_component",
    testonly = True,
    visibility = ["//visibility:private"],
    srcs = ["TestComponent.kt"],
)

kt_jvm_test(
    name = "DaggerKspTest",
    srcs = ["DaggerKspTest.kt"],
    test_class = "first_party.dagger.tests.DaggerKspTest",
    visibility = ["//visibility:private"],
    deps = [
        ":test_component",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)
