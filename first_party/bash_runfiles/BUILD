load(":publicity.bzl", "PUBLICITY")

# Exported for reference by the macro-generated targets.
exports_files(
    [
        "shell.bzl",
        "bats_test.bzl",
        "filegroup.bzl",
    ],
    visibility = PUBLICITY,
)

# These files are exported so they can be referenced by the macro-generated targets; however,
# they are not supported for use in other contexts, and do not constitutie the public API of this
# package. They are effectively internal, but must be publicly available due to Bazel visibility
# constraints. Despite Hyrum's Law, these files are subject to change without warning, and
# downstream breakages as a result of their use will not prevent upstream changes from being
# submitted.
exports_files(
    [
        "inject_runfiles.bzl",
        "verify_sources.bzl",
    ],
    visibility = PUBLICITY,
)
