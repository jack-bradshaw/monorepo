load("//first_party/dagger:defs.bzl", 'kt_jvm_library_with_dagger', 'kt_jvm_test_with_dagger')
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = [
        "StandardScope.kt",
    ],
    visibility = [
        "//visibility:public",
    ],
    deps = [
    ],
)

kt_jvm_library_with_dagger(
    name = "standard",
    srcs = [
        "Standard.kt",
    ],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":scope",
        "//first_party/sasync/inbound",
        "//first_party/sasync/outbound",
        "//first_party/sasync/standard/config:default",
        "//first_party/sasync/standard/config:kt_jvm_proto",
        "//first_party/sasync/standard/config:module",
        "//first_party/sasync/standard/error:module",
        "//first_party/sasync/standard/input:module",
        "//first_party/sasync/standard/output:module",
    ],
)

kt_jvm_test_with_dagger(
    name = "test",
    testonly = True,
    srcs = ["StandardTest.kt"],
    test_class = "com.jackbradshaw.sasync.standard.StandardTest",
    visibility = [
        "//visibility:private",
    ],
    deps = [
        ":standard",
        "//first_party/coroutines/testing",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
