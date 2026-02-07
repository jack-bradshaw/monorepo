load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/otter/demo:publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "module",
    testonly = True,
    srcs = ["SupportModule.kt"],
    visibility = PUBLICITY,
    deps = ["//first_party/otter/demo:scope"],
)
