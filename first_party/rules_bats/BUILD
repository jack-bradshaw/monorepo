package(default_visibility = ["//visibility:public"])

# These files must be exported so they can be referenced by generated targets. This is necessary
# because the generator macros are defined in this package, but the generated targets are scoped to
# the calling package, and files are not visible outside their package by default.
exports_files([
    "defs.bzl",
    "runner.sh",
])
