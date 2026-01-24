package(default_visibility = ["//visibility:public"])

# These files must be exported so they can be referenced by the targets the macro generates. This is
# necessary because the macro is defined in this package but the generated targets are scoped to
# the calling package.
exports_files([
    "defs.bzl",
    "runner.sh",
])
