load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:defs.bzl", "public")

PUBLICITY = public()

kt_jvm_library_with_dagger(
    name = "error",
    srcs = ["StandardError.kt"],
    visibility = PUBLICITY,
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["StandardErrorModule.kt"],
    visibility = PUBLICITY,
    deps = [
        ":error",
        "//first_party/sasync/outbound/transport",
        "//first_party/sasync/standard:scope",
    ],
)
