load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/sasync:publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "input",
    srcs = ["StandardInput.kt"],
    visibility = PUBLICITY,
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["StandardInputModule.kt"],
    visibility = ["//first_party/sasync/standard:__subpackages__"],
    deps = [
        ":input",
        "//first_party/sasync/inbound/transport",
        "//first_party/sasync/standard:scope",
    ],
)
