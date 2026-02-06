load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:defs.bzl", "public")

PUBLICITY = public()

kt_jvm_library_with_dagger(
    name = "input",
    srcs = ["StandardInput.kt"],
    visibility = PUBLICITY,
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["StandardInputModule.kt"],
    visibility = PUBLICITY,
    deps = [
        ":input",
        "//first_party/sasync/inbound/transport",
        "//first_party/sasync/standard:scope",
    ],
)
