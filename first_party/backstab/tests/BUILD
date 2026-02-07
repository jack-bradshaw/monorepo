load("@rules_java//java:defs.bzl", "java_binary", "java_import")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "testing_infrastructure",
    testonly = True,
    srcs = ["TestingComponents.kt"],
    plugins = [
        "//first_party/backstab/entrypoint:plugin",
    ],
    visibility = ["//visibility:private"],
    deps = [
        "//first_party/backstab/annotations/aggregate",
        "//first_party/backstab/annotations/backstab",
        "@com_jackbradshaw_maven//:com_google_code_findbugs_jsr305",
    ],
)

kt_jvm_test(
    name = "BackstabTest",
    srcs = ["BackstabTest.kt"],
    test_class = "com.jackbradshaw.backstab.tests.BackstabTest",
    deps = [
        ":testing_infrastructure",
        "//first_party/backstab/annotations/aggregate",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect",
    ],
)
