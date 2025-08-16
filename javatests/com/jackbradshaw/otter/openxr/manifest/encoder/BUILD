load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

kt_jvm_test(
    name = "ManifestEncoderTest",
    srcs = [
        "ManifestEncoderTest.kt",
    ],
    deps = [
        ":dagger",
        "//java/com/jackbradshaw/otter:component",
        "//java/com/jackbradshaw/otter/config:proto_extensions",
        "//java/com/jackbradshaw/otter/openxr/config:proto_extensions",
        "//java/com/jackbradshaw/otter/openxr/manifest/encoder",
        "//java/com/jackbradshaw/otter/openxr/standard",
        "@com_jackbradshaw_maven//:com_google_protobuf_protobuf_kotlin_lite",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
