load("@buildifier_prebuilt//:rules.bzl", "buildifier")
load("@rules_kotlin//kotlin:core.bzl", "define_kt_toolchain")

buildifier(
    name = "buildifier_fix",
    exclude_patterns = [
        "./.git/*",
        "./third_party/*",
    ],
    lint_mode = "fix",
    mode = "fix",
)

buildifier(
    name = "buildifier_check",
    exclude_patterns = [
        "./.git/*",
        "./third_party/*",
    ],
    lint_mode = "warn",
    mode = "check",
)

define_kt_toolchain(
    name = "kotlin_toolchain",
    api_version = "1.7",
    jvm_target = "1.8",
    language_version = "1.7",
)
