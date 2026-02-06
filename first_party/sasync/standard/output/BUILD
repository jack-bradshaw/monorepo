load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:defs.bzl", "public")

PUBLICITY = public()

kt_jvm_library_with_dagger(
    name = "output",
    srcs = ["StandardOutput.kt"],
    visibility = PUBLICITY,
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["StandardOutputModule.kt"],
    visibility = PUBLICITY,
    deps = [
        ":output",
        "//first_party/sasync/outbound/transport",
        "//first_party/sasync/standard:scope",
    ],
)
