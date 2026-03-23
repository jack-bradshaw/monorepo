load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

kt_jvm_test(
    name = "journal_metadata_test",
    size = "small",
    srcs = ["JournalMetadataTest.kt"],
    data = [
        "//first_party/site:journal_content_md",
        "//first_party/site:journal_metadata",
    ],
    test_class = "com.jackbradshaw.site.tests.metadata.JournalMetadataTest",
    deps = [
        "@bazel_tools//tools/java/runfiles",
        "@com_jackbradshaw_maven//:com_google_code_gson_gson",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
