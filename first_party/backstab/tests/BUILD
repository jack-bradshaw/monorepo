load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")
load("@rules_java//java:defs.bzl", "java_binary", "java_import")

kt_jvm_library_with_dagger(
    name = "test_components",
    testonly = True,
    srcs = ["TestComponents.kt"],
    plugins = [
        "//first_party/backstab/entrypoint:plugin",
    ],
    deps = [
        "//first_party/backstab/annotations/backstab",
        "//first_party/backstab/annotations/meta",
    ],
    visibility = ["//visibility:private"],
)

kt_jvm_library_with_dagger(
    name = "meta_components",
    testonly = True,
    srcs = ["MetaComponents.kt"],
    deps = [
        ":test_components",
        "//first_party/backstab/annotations/meta",
        "//first_party/backstab/annotations/backstab",
    ],
    visibility = ["//visibility:private"],
)

kt_jvm_test(
    name = "BackstabTest",
    srcs = ["BackstabTest.kt"],
    test_class = "com.jackbradshaw.backstab.tests.BackstabTest",
    deps = [
        ":meta_components",
        ":test_components",
        "//first_party/backstab/annotations/meta",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect", 
    ],
)
