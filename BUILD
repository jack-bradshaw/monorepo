load("@com_github_bazelbuild_buildtools//buildifier:def.bzl", "buildifier")
load("@io_bazel_rules_kotlin//kotlin:core.bzl", "define_kt_toolchain")

buildifier(
    name = "buildifier",
)

define_kt_toolchain(
    name = "kotlin_toolchain",
    api_version = "1.7",
    jvm_target = "1.8",
    language_version = "1.7",
)
