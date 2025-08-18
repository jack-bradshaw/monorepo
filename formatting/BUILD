load("@buildifier_prebuilt//:rules.bzl", "buildifier")

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
