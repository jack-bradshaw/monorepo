load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_test(
    name = "ManifestInstallerTest",
    srcs = [
        "ManifestInstallerTest.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/omnixr:component",
        "//java/io/jackbradshaw/omnixr/manifest/encoder",
        "//java/io/jackbradshaw/omnixr/manifest/installer",
        "//java/io/jackbradshaw/omnixr/model:proto_extensions",
        "//java/io/jackbradshaw/omnixr/standard",
        "//javatests/io/jackbradshaw/omnixr/manifest/goldens",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
